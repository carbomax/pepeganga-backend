package uy.com.pepeganga.consumingwsstore.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import uy.com.pepeganga.business.common.entities.*;
import uy.com.pepeganga.business.common.utils.date.DateTimeUtilsBss;
import uy.com.pepeganga.business.common.utils.methods.ConfigurationsSystem;
import uy.com.pepeganga.consumingwsstore.client.MeliFeignClient;
import uy.com.pepeganga.consumingwsstore.entities.*;
import uy.com.pepeganga.consumingwsstore.models.Pair;
import uy.com.pepeganga.consumingwsstore.models.RiskTime;
import uy.com.pepeganga.consumingwsstore.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@EnableAsync
@Service
public class ScheduledSyncService implements IScheduledSyncService{
    ConfigurationsSystem configService;

    private static final Logger logger = LoggerFactory.getLogger(ScheduledSyncService.class);
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MeliFeignClient feign;
    @Autowired
    RiskTime property;

    @Autowired
    ProductsRepository productRepo;

    @Autowired
    IUpdatesSystemRepository updateSysRepo;

    //Repositories of Tables
    @Autowired
    IItemRepository itemRepo;
    @Autowired
    IFamilyRepository familyRepo;
    @Autowired
    ICategoryRepository categoryRepo;
    @Autowired
    IBrandRepository brandRepo;
    @Autowired
    CheckingStockProcessorRepository checkStockRepo;
    @Autowired
    StockProcessorRepository stockProcRepo;

    //Services
    @Autowired
    BrandRequestService brandService;
    @Autowired
    CategoryRequestService categoryService;
    @Autowired
    FamilyRequestService familyService;
    @Autowired
    ItemRequestService itemService;

    public ScheduledSyncService() {
        this.configService = new ConfigurationsSystem();
    }

    UpdatesOfSystem data = new UpdatesOfSystem();
    UpdatesOfSystem riskData;

    @Override
    public void syncDataBase(){

        try {
            //Create synchronization logs
            logger.info("Starting synchronization");
            UpdatesOfSystem updatesSystem = new UpdatesOfSystem();
            updatesSystem.setStartDate(DateTimeUtilsBss.getDateTimeAtCurrentTime().toDate());
            updatesSystem.setMessage("");
            data = updateSysRepo.save(updatesSystem);

            //Insert synchronization data
            String[] dataTypes = new String[]{
                    "brand","family","category","item" };

            for(int i = 0; i < dataTypes.length; i++) {
                if(!insertData(dataTypes[i])) {
                    return;
                }
            }

            //Update Stock table
            if(!updateStockProvided()){
                logger.error("Error updating stock to publications in Mercado Libre");
                return;
            }
            return;
        }catch (Exception e) {
            logger.error(String.format("Error synchronizing Tables, General method, Error: {} "), e.getMessage());
            updateTableLogs(String.format("Error synchronizing Tables, General method, Error: %s", e.getMessage()), true);
            return;
        }

    }

    @Override
    public Boolean syncStockRisk() {
        try {
            //Create synchronization logs
            logger.info("Starting synchronization of Stock Risk");
            UpdatesOfSystem updatesSystem = new UpdatesOfSystem();
            updatesSystem.setStartDate(DateTimeUtilsBss.getDateTimeAtCurrentTime().toDate());
            updatesSystem.setMessage("");
            riskData = updateSysRepo.save(updatesSystem);

            sendCheckingByStockRisk();
            return true;
        }catch (Exception e) {
            logger.error(String.format("Error synchronizing Stock Risk, General method, Msg: %s, Error: ", e.getMessage()), e);
            updateTableLogsOfRisk(String.format("Error synchronizing Stock Risk, General method, Error: %s", e.getMessage()), true);
            return false;
        }
    }

    private boolean insertData(String type) {
        logger.info(String.format("Starting to insert or update %s data in table", type));
        try {
            if (type.equals("brand"))
                if(!brandService.storeBrand(data))
                    return false;
            if (type.equals("family"))
                if(!familyService.storeFamilies(data))
                    return false;
            if (type.equals("category"))
                if(!categoryService.storeCategories(data))
                    return false;
            if (type.equals("item"))
                if(!itemService.storeItems(data))
                    return false;
            logger.info(String.format("Insert or Update %s data in table Completed....", type));
            return true;
        }catch (Exception e){
            logger.error(String.format("Error inserting or updating %s tables, Msg: {}, Error: {}", type), e.getMessage(), e);
            updateTableLogs(String.format("Error inserting or updating %s tables {}", type) + e.getMessage(), true);
            //deleteTemporalData();
            return false;
        }
    }

    private void updateTableLogs(String msg, Boolean isError) {
        if(isError) {
            data.setFinishedSync(false);
        }
        else {
            data.setFinishedSync(true);
        }
        String data1 = data.getMessage();
        data.setMessage(data1 + msg);
        data.setEndDate(DateTimeUtilsBss.getDateTimeAtCurrentTime().toDate());
        updateSysRepo.save(data);
    }

    private void updateTableLogsOfRisk(String msg, Boolean isError) {
        if(isError) {
            riskData.setFinishedSync(false);
        }
        else {
            riskData.setFinishedSync(true);
        }
        String data1 = riskData.getMessage();
        riskData.setMessage(data1 + msg);
        riskData.setEndDate(DateTimeUtilsBss.getDateTimeAtCurrentTime().toDate());
        updateSysRepo.save(riskData);
    }

    private boolean updateStockProvided(){
        logger.info("Starting to update stock provider...");
        List<StockProcessor> stockList = new ArrayList<>();
        stockList.addAll(stockProcRepo.findAll());
        List<Item> itemsU = new ArrayList<>();
        itemsU = itemRepo.findAll();
        List<String> itemsToUpdate = new ArrayList<>();
        Integer globalStockRisk = getStockRisk();

        List<Pair> pairs = new ArrayList<>();
        boolean initialStockEmpty = false;
        boolean finishedWithError = false;

        List<CheckingStockProcessor> checkingList = new ArrayList<>();
        List<StockProcessor> stockToAddOrUpdate = new ArrayList<>();

        if(!stockList.isEmpty()){
            itemsU.forEach(newItem -> {
                pairs.add(new Pair(newItem.getSku(), Math.toIntExact(newItem.getStockActual())));
                boolean exit = false;
                AtomicInteger count = new AtomicInteger();
                CheckingStockProcessor checking = new CheckingStockProcessor();
                StockProcessor stockUpdated = new StockProcessor();

                while (count.get() < stockList.size() && !exit){
                    //Si existe el SKU en la tabla?
                    if(stockList.get(count.get()).getSku().equals(newItem.getSku())) {
                        exit = true;
                        //El producto llego en la actualizacion
                        if (newItem.isUpdated() != null && newItem.isUpdated()) {
                            stockUpdated.setId(stockList.get(count.get()).getId());
                            stockUpdated.setSku(stockList.get(count.get()).getSku());
                            stockUpdated.setExpectedStock(stockList.get(count.get()).getExpectedStock());
                            stockUpdated.setRealStock(Math.toIntExact(newItem.getStockActual()));

                            //Si el articulo ya se encuentra pausado por stock lo envio de vuelta al checking, sino Verifico si se pausa al actualizar
                            if (((stockList.get(count.get()).getRealStock() - stockList.get(count.get()).getExpectedStock()) <= globalStockRisk) || ((int) newItem.getStockActual() - stockList.get(count.get()).getExpectedStock() <= globalStockRisk)) {
                                checking.setSku(stockList.get(count.get()).getSku());
                                checking.setExpectedStock(stockList.get(count.get()).getExpectedStock());
                                checking.setRealStock((int) newItem.getStockActual());
                                checking.setAction(0);
                                checkingList.add(checking);
                                logger.info("Enviando al checking con item con sku: {}", checking.getSku());

                            }
                        }
                        //El producto no llego en la actualizacion
                        else {
                            //Pausamos este Item por no llegar en la actualización
                            CheckingStockProcessor check = new CheckingStockProcessor();
                            check.setSku(newItem.getSku());
                            check.setExpectedStock(0);
                            check.setRealStock(0);
                            check.setAction(0);
                            checkingList.add(check);

                            //Actualizamos el stock del Producto a cero en la tabla Item
                            itemsToUpdate.add(newItem.getSku());
                            pairs.add(new Pair(newItem.getSku(), 0));

                            //actualizamos a cero el item en la tabla Stock Processor
                            stockUpdated.setId(stockList.get(count.get()).getId());
                            stockUpdated.setSku(stockList.get(count.get()).getSku());
                            stockUpdated.setRealStock(0);
                            stockUpdated.setExpectedStock(0);
                            stockToAddOrUpdate.add(stockUpdated);
                            logger.info("Enviando al checking para pausar especial el item con sku: {} por no venir en la actualización", stockUpdated.getSku());
                        }
                    }
                    count.getAndIncrement();
                }
                if(!exit) {
                    //No existe el articulo con SKU en la tabla StockProcessor -- lo adiciono a StockProcessor Table
                    logger.info("Adicionando nuevo sku a la tabla Stock Processor: {}", newItem.getSku());
                    stockUpdated.setSku(newItem.getSku());
                    stockUpdated.setExpectedStock(0);
                    stockUpdated.setRealStock((int) newItem.getStockActual());

                    //Verifico si cumple condicion de stock
                    if ((int) newItem.getStockActual() <= globalStockRisk) {
                        checking.setSku(newItem.getSku());
                        checking.setExpectedStock(0);
                        checking.setRealStock((int) newItem.getStockActual());
                        checking.setAction(0);
                        checkingList.add(checking);
                        logger.info("Enviando al checking con item con sku: {}", checking.getSku());
                    }
                }

                //si existe articulo: Actualizo el stock del articulo en la tabla Stock Processor, sino existe: lo adiciono
                stockToAddOrUpdate.add(stockUpdated);
            });
        }
        //El stock está vacio -- Sistema nuevo
        else{
            logger.info("Sistema nuevo, adicionando productos a la tabla stock");
            itemsU.forEach(i -> {
                StockProcessor stockNew = new StockProcessor();
                CheckingStockProcessor checkingNew = new CheckingStockProcessor();
                //adiciono a StockProcessor Table
                logger.info("Adicionando nuevo sku a la tabla Stock Processor: {}", i.getSku());
                stockNew.setSku(i.getSku());
                stockNew.setExpectedStock(0);
                stockNew.setRealStock((int) i.getStockActual());

                //Verifico si cumple condicion de stock
                if ((int) i.getStockActual() <= globalStockRisk) {
                    checkingNew.setSku(i.getSku());
                    checkingNew.setExpectedStock(0);
                    checkingNew.setRealStock((int) i.getStockActual());
                    checkingNew.setAction(0);
                    checkingList.add(checkingNew);
                    logger.info("Enviando al checking con item con sku: {}", checkingNew.getSku());
                }
                stockToAddOrUpdate.add(stockNew);
            });
            initialStockEmpty = true;
        }

        //Liberando espacio en memoria
        itemsU.clear();

        logger.info("Updating checking Stock and stock product table");
        //Actualizar ambas tablas en la base datos
        if(!stockToAddOrUpdate.isEmpty())
            stockProcRepo.saveAll(stockToAddOrUpdate);

        if(!checkingList.isEmpty()) {
            checkingList.forEach(check -> {
                CheckingStockProcessor data = checkStockRepo.findBySku(check.getSku());
                if(data != null && data.getSku() != null && data.getSku() != "")
                    check.setId(data.getId());
            });
            checkStockRepo.saveAll(checkingList);
        }
        logger.info("Checking Stock and stock product table completed...");

        //Actualizar campo Updated para todos los items en la tabla Item
        logger.info("Updating fields 'Updated' to all items in Item table...");
        itemRepo.updateFieldUpdatedToAll(false);

        //Actualizar stock en la Tabla Item para productos que no llegaron
        List<Item> updateItem = new ArrayList<>();
        itemsToUpdate.forEach(i -> {
            Optional<Item> itemOptional = itemRepo.findById(i);
            if(itemOptional.isPresent()) {
                itemOptional.get().setStockActual(0);
                updateItem.add(itemOptional.get());
            }
        });
        if(!updateItem.isEmpty())
            itemRepo.saveAll(updateItem);
        logger.info("IMPORTANT: THE SYNCHRONIZATION PROCESS WAS FINISHED CORRECTLY ....");

        logger.info("STARTING TO UPDATE PUBLICATIONS IN MERCADO LIBRE AND PRODUCTS STOCK IN TABLES OF THE SYSTEM");

        //Actualiza stock en el sistema y en Mercado Libre
        if(!initialStockEmpty) {
            if(!updateStockOfProductsMeli(pairs)){
               finishedWithError = true;
            }
            //Lamada al metodo asincrono
            updateStockOfPublicationsMeli(pairs);

        }

        if(finishedWithError){ return false;}
        return true;
    }

    public void sendCheckingByStockRisk() {
        try {
            List<CheckingStockProcessor> checkList = new ArrayList<>();
            List<StockProcessor> stockList = new ArrayList<>();
            stockList.addAll(stockProcRepo.findAll());
            Integer globalStockRisk = getStockRisk();

            if (!stockList.isEmpty()) {
                logger.info("Starting the synchronization of stock risk...");
                AtomicInteger count = new AtomicInteger();
                while (count.get() < stockList.size()) {
                    CheckingStockProcessor checking = new CheckingStockProcessor();
                    // Verifico si se pausa al actualizar la variable StockRisk
                    if ((stockList.get(count.get()).getRealStock() - stockList.get(count.get()).getExpectedStock()) <= globalStockRisk) {
                        checking.setSku(stockList.get(count.get()).getSku());
                        checking.setExpectedStock(stockList.get(count.get()).getExpectedStock());
                        checking.setRealStock(stockList.get(count.get()).getRealStock());
                        checking.setAction(0);
                        checkList.add(checking);
                        logger.info("Enviando al checking con item con sku: {}", checking.getSku());
                    }
                    count.getAndIncrement();
                }

                logger.info("Updating checking Stock table");
                //Actualizar la tabla checking en la base datos
                if (!checkList.isEmpty()) {
                    checkList.forEach(check -> {
                        CheckingStockProcessor check_data = checkStockRepo.findBySku(check.getSku());
                        if (check_data != null && check_data.getSku() != null && check_data.getSku() != "")
                            check.setId(check_data.getId());
                    });
                    checkStockRepo.saveAll(checkList);
                }
                logger.info("Checking Stock table completed...");
                logger.info("IMPORTANT: THE SYNCHRONIZATION OF STOCK RISK PROCESS WAS FINISHED CORRECTLY ....");
                updateTableLogsOfRisk("synchronization success", false);
            }
        }catch (Exception e){
            logger.error(String.format("Error sending products to checking by Stock Risk. Method: sendCheckingByStockRisk(), Msg: %s, Error: ", e.getMessage()), e);
            logger.error("No se completó la sincronizacion al actualizar variable ´Riesgo de Stock´");
            updateTableLogsOfRisk(String.format("Error sending products to checking by Stock Risk. Method: sendCheckingByStockRisk(), Msg: %s", e.getMessage()), true);
        }
    }

    private boolean updateStockOfProductsMeli(List<Pair> pairsI){
        try {
            pairsI.forEach( i -> {
                productRepo.updateStockBySKU(i.getStock(), i.getSku());
            });
            return true;
        }catch (Exception e){
            logger.error(String.format("Error updating stock in mercadolibrepublications table {} Error: "), e.getMessage());
            updateTableLogs(String.format("Error updating stock in mercadolibrepublications table {} Error: ", e.getMessage()), true);
            return false;
        }

    }

    @Async
    public void updateStockOfPublicationsMeli(List<Pair> pairs){
        try {
            Long id = data.getId();
            feign.updateStock(pairs, id);
        }catch (Exception e) {
            logger.warn(String.format("Timeout error waiting for response from meli service to update stock of publications {} Error: "), e.getMessage());
        }
    }

    private Integer getStockRisk() {
        Integer stock_risk = Integer.parseInt(this.configService.getSynchronizationConfig().get("stock_risk").toString());
        return stock_risk == null ? 0 : stock_risk;
    }

    private boolean saveOrDeleteItemsTable(List<Item> list, String action){
        logger.info("Starting to synchronization from TempItems to Item table in database");
        try {
            if(action.equals("deleting")) {
                if(list.size() != 0)
                    itemRepo.deleteAll(list);
            }else if(action.equals("saving")){
                itemRepo.saveAll(list);
            }
            logger.info("The synchronization from TempItems to Item table in database completed...");
            return true;
        }catch (Exception e){
            logger.error("The synchronization from TempItems to Item table in database throw a error");
            logger.error(String.format("Error %s Item table {}", action), e.getMessage());
            updateTableLogs(String.format("Error %s Item table {}", action) + e.getMessage(), true);
            return false;
        }
    }
    private boolean saveOrDeleteBrandsTable(List<Brand> list, String action){
        logger.info("Starting to synchronization from TempBrand to Brand table in database");
        try {
            if(action.equals("deleting")) {
                if(list.size() != 0)
                    brandRepo.deleteAll(list);
            }else if(action.equals("saving")){
                brandRepo.saveAll(list);
            }
            logger.info("The synchronization from TempBrand to Brand table in database completed...");
            return true;
        }catch (Exception e){
            logger.error("The synchronization from TempBrand to Brand table in database throw a error");
            logger.error(String.format("Error %s Brand table {}", action), e.getMessage());
            updateTableLogs(String.format("Error %s Brand table {}", action) + e.getMessage(), true);
            return false;
        }
    }
    private boolean saveOrDeleteFamiliesTable(List<Family> list, String action){
        logger.info("Starting to synchronization from TempFamilies to Families table in database");
        try {
            if(action.equals("deleting")) {
                if(list.size() != 0)
                    familyRepo.deleteAll(list);
            }else if(action.equals("saving")){
                familyRepo.saveAll(list);
            }
            logger.info("The synchronization from TempFamilies to Families table in database completed...");
            return true;
        }catch (Exception e){
            logger.error("The synchronization from TempFamilies to Families table in database throw a error");
            logger.error(String.format("Error %s Family table {}", action), e.getMessage());
            updateTableLogs(String.format("Error %s Family table {}", action) + e.getMessage(), true);
            return false;
        }
    }
    private boolean saveOrDeleteCategoriesTable(List<Category> list, String action){
        logger.info("Starting to synchronization from TempCategories to Categories table in database");
        try {
            if(action.equals("deleting")) {
                if(list.size() != 0)
                    categoryRepo.deleteAll(list);
            }else if(action.equals("saving")){
                categoryRepo.saveAll(list);
            }
            logger.info("The synchronization from TempCategories to Categories table in database completed...");
            return true;
        }catch (Exception e){
            logger.error("The synchronization from TempCategories to Categories table in database throw a error");
            logger.error(String.format("Error %s Category table {}", action), e.getMessage());
            updateTableLogs(String.format("Error %s Category table {}", action) + e.getMessage(), true);
            return false;
        }
    }
}
