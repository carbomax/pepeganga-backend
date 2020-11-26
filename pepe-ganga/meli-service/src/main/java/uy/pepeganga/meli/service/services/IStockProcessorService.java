package uy.pepeganga.meli.service.services;

import uy.com.pepeganga.business.common.entities.CheckingStockProcessor;

public interface IStockProcessorService {

    void schedulingStockProcessor();

    void checkingPublication(CheckingStockProcessor checkingStockProcessor);
}
