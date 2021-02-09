package uy.com.pepeganga.consumingwsstore.services;

public interface IScheduledSyncService {
    void syncDataBase();
    Boolean syncStockRisk();
}
