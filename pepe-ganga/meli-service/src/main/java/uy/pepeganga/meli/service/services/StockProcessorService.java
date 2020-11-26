package uy.pepeganga.meli.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.com.pepeganga.business.common.entities.CheckingStockProcessor;
import uy.com.pepeganga.business.common.entities.DetailsPublicationsMeli;
import uy.com.pepeganga.business.common.entities.StockProcessor;
import uy.com.pepeganga.business.common.utils.enums.ChangeStatusPublicationType;
import uy.pepeganga.meli.service.repository.CheckingStockProcessorRepository;
import uy.pepeganga.meli.service.repository.DetailsPublicationMeliRepository;
import uy.pepeganga.meli.service.repository.StockProcessorRepository;
import uy.pepeganga.meli.service.utils.MapResponseConstants;

import java.util.List;
import java.util.Map;

@Service
public class StockProcessorService implements IStockProcessorService {

    @Autowired
    StockProcessorRepository stockProcessorRepository;

    @Autowired
    CheckingStockProcessorRepository checkingStockProcessorRepository;

    @Autowired
    DetailsPublicationMeliRepository detailsPublicationMeliRepository;

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
            if((stockProcessorFounded.getRealStock() - stockProcessorFounded.getExpectedStock()) <= 10){
                // Pausar con estado especial todas las publicaciones y  bloquear los item no publicados
                List<DetailsPublicationsMeli> detailsPublications = detailsPublicationMeliRepository.findAllBySku(stockProcessorFounded.getSku());

                detailsPublications.forEach(detailsPublicationsMeli -> {

                    Map<String, Object> response = meliService.changeStatusPublication(detailsPublicationsMeli.getAccountMeli(),
                            ChangeStatusPublicationType.PAUSED.getCode(), detailsPublicationsMeli.getIdPublicationMeli());
                    if(response.containsKey(MapResponseConstants.RESPONSE)){
                        detailsPublicationsMeli.setSpecialPaused(1);
                        detailsPublicationMeliRepository.save(detailsPublicationsMeli);
                    }
                });
            } else {
                // Reactivar las publicaciones con pausado especial y  desbloquear los item no publicados
            }
        }
    }
}
