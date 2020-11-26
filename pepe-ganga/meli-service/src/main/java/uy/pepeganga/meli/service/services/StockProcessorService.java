package uy.pepeganga.meli.service.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.com.pepeganga.business.common.entities.CheckingStockProcessor;
import uy.com.pepeganga.business.common.entities.DetailsPublicationsMeli;
import uy.com.pepeganga.business.common.entities.MercadoLibrePublications;
import uy.com.pepeganga.business.common.entities.StockProcessor;
import uy.com.pepeganga.business.common.utils.enums.ChangeStatusPublicationType;
import uy.pepeganga.meli.service.repository.CheckingStockProcessorRepository;
import uy.pepeganga.meli.service.repository.DetailsPublicationMeliRepository;
import uy.pepeganga.meli.service.repository.MercadoLibrePublishRepository;
import uy.pepeganga.meli.service.repository.StockProcessorRepository;
import uy.pepeganga.meli.service.utils.MapResponseConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockProcessorService implements IStockProcessorService {

    private static final Logger logger = LoggerFactory.getLogger(StockProcessor.class);

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
        checkingStocks.forEach(this::checkingPublication);
    }

    @Override
    public void checkingPublication(CheckingStockProcessor checkingStockProcessor) {
        StockProcessor stockProcessorFounded = stockProcessorRepository.findBySku(checkingStockProcessor.getSku());


        if(stockProcessorFounded != null){
            // ir a la tabla de StockProcessor y verificar si esta en zona de riesgo
            List<DetailsPublicationsMeli> detailsPublications = detailsPublicationMeliRepository.findAllBySku(stockProcessorFounded.getSku());
            List<MercadoLibrePublications> meliPublications = mercadoLibrePublishRepository.findAllBySku(stockProcessorFounded.getSku());
            if((stockProcessorFounded.getRealStock() - stockProcessorFounded.getExpectedStock()) <= 10){
                // Pausar con estado especial todas las publicaciones y  bloquear los item no publicados


                detailsPublications.forEach(detailsPublicationsMeli -> {

                    // Comprobamos si está activa la publicacion, si lo está mandamos a pausar
                    if(detailsPublicationsMeli.getStatus().equals(ChangeStatusPublicationType.ACTIVE.getStatus())){
                        Map<String, Object> response =  meliService.changeStatusPublication(detailsPublicationsMeli.getAccountMeli(),
                                ChangeStatusPublicationType.PAUSED.getCode(), detailsPublicationsMeli.getIdPublicationMeli());
                        if(response.containsKey(MapResponseConstants.RESPONSE)){
                            logger.info("Publication was paused successfully. Publication Id: {}", detailsPublicationsMeli.getIdMLPublication());
                        } else {
                            logger.warn("Publication was not paused. Publication Id: {}", detailsPublicationsMeli.getIdMLPublication());
                        }
                    }
                    // se marca como pausado especial aunque no se pueda pausar en mercado libre.
                    detailsPublicationsMeli.setSpecialPaused(1);
                    detailsPublicationMeliRepository.save(detailsPublicationsMeli);

                    // Vamos a cada uno de los items a bloquearlos

                });
                meliPublications.forEach(mercadoLibrePublications -> {
                    logger.info("Blocking unpublished product for special paused with sku: {} ", mercadoLibrePublications.getSku());
                    mercadoLibrePublications.setSpecialPaused(1);
                    mercadoLibrePublishRepository.save(mercadoLibrePublications);
                    logger.info("Blocking unpublished product successfully with sku: {} ", mercadoLibrePublications.getSku());
                });

            } else {
                // Reactivar las publicaciones con pausado especial y  desbloquear los item no publicados
                detailsPublications.forEach(detailsPublicationsMeli -> {

                    // Comprobamos si está activa la publicacion, si lo está mandamos a pausar
                    if(detailsPublicationsMeli.getStatus().equals(ChangeStatusPublicationType.PAUSED.getStatus())){
                        Map<String, Object> response =  meliService.changeStatusPublication(detailsPublicationsMeli.getAccountMeli(),
                                ChangeStatusPublicationType.ACTIVE.getCode(), detailsPublicationsMeli.getIdPublicationMeli());
                        if(response.containsKey(MapResponseConstants.RESPONSE)){
                            logger.info("Publication was reactivated successfully. Publication Id: {}", detailsPublicationsMeli.getIdMLPublication());
                            // Si se logro activar entonces se elimina el bloqueo especial y estaria disponible en el frontend
                            detailsPublicationsMeli.setSpecialPaused(0);
                            detailsPublicationMeliRepository.save(detailsPublicationsMeli);



                        } else {
                            logger.warn("Publication was not reactivated. Publication Id: {}", detailsPublicationsMeli.getIdMLPublication());
                        }
                    }


                    // Vamos a cada uno de los items  a desbloquearlos.


                });

            }
        }
    }
}
