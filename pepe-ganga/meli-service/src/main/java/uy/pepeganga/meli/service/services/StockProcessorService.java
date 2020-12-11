package uy.pepeganga.meli.service.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uy.com.pepeganga.business.common.entities.CheckingStockProcessor;
import uy.com.pepeganga.business.common.entities.DetailsPublicationsMeli;
import uy.com.pepeganga.business.common.entities.MercadoLibrePublications;
import uy.com.pepeganga.business.common.entities.StockProcessor;
import uy.com.pepeganga.business.common.utils.enums.ChangeStatusPublicationType;
import uy.com.pepeganga.business.common.utils.enums.MeliStatusPublications;
import uy.com.pepeganga.business.common.utils.enums.States;
import uy.pepeganga.meli.service.repository.CheckingStockProcessorRepository;
import uy.pepeganga.meli.service.repository.DetailsPublicationMeliRepository;
import uy.pepeganga.meli.service.repository.MercadoLibrePublishRepository;
import uy.pepeganga.meli.service.repository.StockProcessorRepository;
import uy.pepeganga.meli.service.utils.MapResponseConstants;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StockProcessorService implements IStockProcessorService {

    private static final Logger logger = LoggerFactory.getLogger(StockProcessor.class);

    @Value("${meli.risk}")
    private String meliRisk;

    private static Integer RISK;

    @Value("${meli.risk}")
    public void setMeliRisk(String meliRisk) {
        StockProcessorService.RISK = Integer.parseInt(meliRisk);
    }

    @Autowired
    StockProcessorRepository stockProcessorRepository;

    @Autowired
    CheckingStockProcessorRepository checkingStockProcessorRepository;

    @Autowired
    DetailsPublicationMeliRepository detailsPublicationMeliRepository;

    @Autowired
    MercadoLibrePublishRepository mercadoLibrePublishRepository;

    @Autowired
    IApiService apiService;

    @Autowired
    IMeliService meliService;

    @Override
    public void schedulingStockProcessor() {

        List<CheckingStockProcessor> checkingStocks = checkingStockProcessorRepository.findAll();
        if (!checkingStocks.isEmpty()) {
            for (CheckingStockProcessor checkingStockProcessor :
                    checkingStocks) {
                checkingPublication(checkingStockProcessor);
            }
        }

    }

    @Override
    public void checkingPublication(CheckingStockProcessor checkingStockProcessor) {
        logger.info("Processing checking stock... sku: {}, expectedStock: {}, realStock: {}, action: {} ",
                checkingStockProcessor.getSku(), checkingStockProcessor.getExpectedStock(), checkingStockProcessor.getRealStock(), checkingStockProcessor.getAction());

            AtomicInteger checkingProcessed = new AtomicInteger(0);

            List<DetailsPublicationsMeli> detailsPublications = detailsPublicationMeliRepository.findAllBySku(checkingStockProcessor.getSku());
            List<MercadoLibrePublications> meliPublications = mercadoLibrePublishRepository.findAllBySku(checkingStockProcessor.getSku());

            if (detailsPublications.isEmpty() && meliPublications.isEmpty()) {
                logger.info("Deleting checking stock processor by empty both lists: detailsPublications and meliPublications. sku: {}", checkingStockProcessor.getSku());
                checkingStockProcessorRepository.deleteById(checkingStockProcessor.getId());
                logger.info("Checking stock processor deleted by empty both lists: detailsPublications and meliPublications. sku: {}", checkingStockProcessor.getSku());

            } else {

                logger.info("Synchronizing publications....");
                meliService.synchronizationPublications(detailsPublications);
                logger.info("Synchronizing publications ended....");

                // Check if this is in the risk zone
                if ((checkingStockProcessor.getRealStock() - checkingStockProcessor.getExpectedStock()) <= StockProcessorService.RISK) {
                    // Pausar con estado especial todas las publicaciones y  bloquear los item no publicados correspondientes.

                    // bloqueamos o mandamos a eliminar todos los items no publicados
                    for (MercadoLibrePublications mercadoLibrePublications :
                            meliPublications) {
                        try {
                            logger.info("Blocking unpublished product for special paused or to delete with sku: {} ", mercadoLibrePublications.getSku());
                            mercadoLibrePublications.setSpecialPaused(1);
                            if (checkingStockProcessor.getAction() == 1) {
                                mercadoLibrePublications.setDeleted(1);
                                mercadoLibrePublications.setStates(States.NOPUBLISHED.getId());
                            }
                            mercadoLibrePublishRepository.save(mercadoLibrePublications);
                            logger.info("Blocking unpublished product successfully with sku: {} ", mercadoLibrePublications.getSku());
                        } catch (Exception e) {
                            logger.error("Blocking unpublished product not successfully with sku: {} ", mercadoLibrePublications.getSku());
                            checkingProcessed.set(checkingProcessed.get() + 1);
                        }

                    }

                    for (DetailsPublicationsMeli detailsPublicationsMeli :
                            detailsPublications) {
                        // se marca como pausado especial aunque no se pueda pausar en mercado libre.
                        detailsPublicationsMeli.setSpecialPaused(1);

                        // Voy a pausar
                        if (checkingStockProcessor.getAction() == 0) {

                            // Comprobamos si está activa la publicacion, si lo está mandamos a pausar
                            if (detailsPublicationsMeli.getStatus().equals(ChangeStatusPublicationType.ACTIVE.getStatus())) {
                                Map<String, Object> response = meliService.changeStatusPublication(detailsPublicationsMeli.getAccountMeli(),
                                        ChangeStatusPublicationType.PAUSED.getCode(), detailsPublicationsMeli.getIdPublicationMeli());
                                if (response.containsKey(MapResponseConstants.RESPONSE)) {
                                    logger.info("Publication was paused successfully. Publication Id: {}", detailsPublicationsMeli.getIdMLPublication());
                                    detailsPublicationsMeli.setStatus((String) response.get(MapResponseConstants.RESPONSE));
                                } else {
                                    logger.warn("Publication was not paused. Publication Id: {}", detailsPublicationsMeli.getIdMLPublication());
                                    checkingProcessed.set(checkingProcessed.get() + 1);
                                }
                            }
                        }

                        // Voy a finalizar la publicacion
                        if (checkingStockProcessor.getAction() == 1) {
                            try {
                                detailsPublicationsMeli.setDeleted(1);
                                // aqui se le pasa el estado actual de la publicacion
                                if(!detailsPublicationsMeli.getStatus().equals(MeliStatusPublications.FAIL.getValue()) &&
                                        !detailsPublicationsMeli.getStatus().equals(ChangeStatusPublicationType.CLOSED.getStatus())){
                                    Map<String, Object> response = meliService.changeStatusPublication(detailsPublicationsMeli.getAccountMeli(),
                                            ChangeStatusPublicationType.CLOSED.getCode(), detailsPublicationsMeli.getIdPublicationMeli());
                                    if (response.containsKey(MapResponseConstants.RESPONSE)) {
                                        logger.info("Publication was finished successfully. Publication Id: {}", detailsPublicationsMeli.getIdMLPublication());
                                        detailsPublicationsMeli.setStatus((String) response.get(MapResponseConstants.RESPONSE));
                                    } else {
                                        logger.warn("Publication was not finished. Publication Id: {}", detailsPublicationsMeli.getIdMLPublication());
                                        checkingProcessed.set(checkingProcessed.get() + 1);
                                    }
                                } else {
                                    logger.warn("Publication was not finished by status. Publication Id: {}, status: {}", detailsPublicationsMeli.getIdMLPublication(), MeliStatusPublications.FAIL.getValue());
                                }
                            } catch (Exception e) {
                                logger.error("DetailsPublicationMeli with id: {}, idPublication: {} not cannot be closed.",
                                        detailsPublicationsMeli.getId(), detailsPublicationsMeli.getIdPublicationMeli());
                                checkingProcessed.set(checkingProcessed.get() + 1);
                            }
                        }
                        detailsPublicationMeliRepository.save(detailsPublicationsMeli);
                    }


                } else {
                    // Reactivar las publicaciones con pausado especial y  desbloquear los item no publicados
                    for (MercadoLibrePublications mercadoLibrePublications :
                            meliPublications) {
                        try {
                            logger.info("Unlocking unpublished product for special paused or to delete with sku: {} ", mercadoLibrePublications.getSku());

                            if (checkingStockProcessor.getAction() == 1) {
                                mercadoLibrePublications.setDeleted(1);
                                mercadoLibrePublications.setStates(States.NOPUBLISHED.getId());
                                mercadoLibrePublications.setSpecialPaused(1);
                                logger.info("Marking unpublished product to delete with sku: {} ", mercadoLibrePublications.getSku());
                            } else {
                                mercadoLibrePublications.setDeleted(0);
                                mercadoLibrePublications.setSpecialPaused(0);
                                logger.info("Marking unpublished product to unlocking with sku: {} ", mercadoLibrePublications.getSku());
                            }

                            mercadoLibrePublishRepository.save(mercadoLibrePublications);
                            logger.info("Unpublished product updated successfully with sku: {} ", mercadoLibrePublications.getSku());
                        } catch (Exception e) {
                            logger.error("Unlocking unpublished product not successfully with sku: {} ", mercadoLibrePublications.getSku());
                            checkingProcessed.set(checkingProcessed.get() + 1);
                        }

                    }

                    logger.info("Synchronizing publications....");
                    meliService.synchronizationPublications(detailsPublications);
                    logger.info("Synchronizing publications ended....");
                    for (DetailsPublicationsMeli detailsPublicationsMeli :
                            detailsPublications) {

                        // Comprobamos si está activa la publicacion, si lo está mandamos a pausar
                        if (detailsPublicationsMeli.getStatus().equals(ChangeStatusPublicationType.PAUSED.getStatus())) {
                            try {
                                Map<String, Object> response = meliService.changeStatusPublication(detailsPublicationsMeli.getAccountMeli(),
                                        ChangeStatusPublicationType.ACTIVE.getCode(), detailsPublicationsMeli.getIdPublicationMeli());
                                if (response.containsKey(MapResponseConstants.RESPONSE)) {
                                    logger.info("Publication was reactivated successfully. Publication Id: {}", detailsPublicationsMeli.getIdMLPublication());
                                    // Si se logro activar entonces se elimina el bloqueo especial y estaria disponible en el frontend
                                    detailsPublicationsMeli.setSpecialPaused(0);
                                    detailsPublicationsMeli.setStatus((String) response.get(MapResponseConstants.RESPONSE));
                                    detailsPublicationMeliRepository.save(detailsPublicationsMeli);

                                } else {
                                    logger.warn("Publication was not reactivated by meli. Publication Id: {}", detailsPublicationsMeli.getIdMLPublication());
                                    checkingProcessed.set(checkingProcessed.get() + 1);
                                }
                            } catch (Exception e) {
                                logger.warn("Publication was not reactivated by meli. Publication Id: {}", detailsPublicationsMeli.getIdMLPublication());
                                checkingProcessed.set(checkingProcessed.get() + 1);
                            }
                        } else {
                            try {
                                detailsPublicationsMeli.setSpecialPaused(0);
                                detailsPublicationMeliRepository.save(detailsPublicationsMeli);
                            }catch (Exception e){
                                logger.warn("Publication was not reactivated by business . Publication Id: {}", detailsPublicationsMeli.getIdMLPublication());
                                checkingProcessed.set(checkingProcessed.get() + 1);
                            }

                        }

                    }
                }
                if (checkingProcessed.get() <= 0) {
                    // Deleting of checking table
                    logger.info("Deleting checkingStockProcessor with sku: {}", checkingStockProcessor.getSku());
                    checkingStockProcessorRepository.deleteById(checkingStockProcessor.getId());
                    logger.info("Deleted checkingStockProcessor with sku: {}", checkingStockProcessor.getSku());
                } else {
                    logger.info("checkingStockProcessor with sku: {}, not deleted by counter: {}", checkingStockProcessor.getSku(), checkingProcessed.get());
                }
            }

    }
}
