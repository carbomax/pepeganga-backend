package uy.com.pepeganga.consumingwsstore.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uy.com.pepeganga.business.common.entities.*;
import uy.com.pepeganga.business.common.utils.date.DateTimeUtilsBss;
import uy.com.pepeganga.consumingwsstore.entities.*;
import uy.com.pepeganga.consumingwsstore.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ScheduledSyncService implements IScheduledSyncService{

    private static final Logger logger = LoggerFactory.getLogger(ScheduledSyncService.class);

    //Repositories of temporal Tables
    @Autowired
    ITempBrandRepository tempBrandRepo;
    @Autowired
    ITempCategoryRepository tempCategoryRepo;
    @Autowired
    ITempFamilyRepository tempFamilyRepo;
    @Autowired
    ITempItemRepository tempItemRepo;
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

    UpdatesOfSystem data = new UpdatesOfSystem();

    @Override
    public void syncDataBase(){

        try {
            //Create sincronization logs
            UpdatesOfSystem updatesSystem = new UpdatesOfSystem();
            updatesSystem.setStartDate(DateTimeUtilsBss.getDateTimeAtCurrentTime().toDate());
            data = updateSysRepo.save(updatesSystem);
/*
            //Empty temporals table
            if(!deleteTemporalData())
                return;

            //Insert synchronization data
            String[] dataTypes = new String[]{
                    "brand","family","category","item" };

            for(int i = 0; i < dataTypes.length; i++) {
                if(!insertTemporalData(dataTypes[i])) {
                    return;
                }
            }
*/
            //Update the system tables from temporal data
            if(!synchronizeDatas()){
                return;
            }

            //If this is execute then the synchronization was Ok, for that reason Empty temporals table
            if(!deleteTemporalData())
                return;

            //Update Stock table
            updateStock();

        }catch (Exception e) {
            logger.error(String.format("Error synchronizing Tables {General method}, Error: %s"), e.getStackTrace());
            updateTableLogs(String.format("Error synchronizing Tables {General method}, Error: %s", e.getMessage()), true);
            return;
        }

    }

    private boolean deleteTemporalData() {
        try {
            if (tempItemRepo.count() != 0) {
                tempItemRepo.deleteAll();
            }

            if (tempCategoryRepo.count() != 0) {
                tempCategoryRepo.deleteAll();
            }

            if (tempBrandRepo.count() != 0) {
                tempBrandRepo.deleteAll();
            }
           /* if (tempItemRepo.getCountCategoriesRelation() != 0) {
                tempItemRepo.deleteAllCategoriesRelation();
            }*/

            if (tempFamilyRepo.count() != 0) {
                tempFamilyRepo.deleteAll();
            }
            return true;
        }catch (Exception e) {
            logger.error(" Error deleting temporal tables {}", e.getStackTrace());
            updateTableLogs("Error deleting temporal tables {}" + e.getMessage(), true);
            return false;
        }
    }

    private boolean insertTemporalData(String type) {
        try {
            if (type.equals("brand"))
                brandService.storeBrand();
            if (type.equals("family"))
                familyService.storeFamilies();
            if (type.equals("category"))
                categoryService.storeCategories();
            if (type.equals("item"))
                itemService.storeItems();

            return true;
        }catch (Exception e){
            logger.error(String.format("Error inserting temporal %s tables {}", type), e.getStackTrace());
            updateTableLogs(String.format("Error inseting temporal %s tables {}", type) + e.getMessage(), true);
            deleteTemporalData();
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
        data.setMessage(msg);
        data.setEndDate(DateTimeUtilsBss.getDateTimeAtCurrentTime().toDate());
        updateSysRepo.save(data);
    }

    private boolean synchronizeDatas() {
        AtomicBoolean exist = new AtomicBoolean(false);

        /** ************ Operation with Item Table ********** **/
        //Spliting Item Table
        List<TempItem> tempItems = new ArrayList<>();
        tempItems.addAll(tempItemRepo.findAll());
        List<Item> items = itemRepo.findAll();
        List<Item> itemsDeleted = new ArrayList<>(){};
        List<Item> newItemList = new ArrayList<>();

        //Getting items to update and delete
        items.forEach(i -> {
            Integer count = 0;
            boolean exit = false;
            while (count < tempItems.size() && !exit) {
                if(i.getSku().equals(tempItems.get(count).getSku())) {
                    //element to update
                    newItemList.add(updateItem(tempItems.get(count), i));
                    exit = true;
                }
                count++;
            }
            if(!exit){
                //element to delete
                itemsDeleted.add(i);
            }
            else {
                int iter = count - 1;
                tempItems.remove(iter);
            }
        });

        //new element to add
        if(tempItems.size() != 0){
            tempItems.forEach(i -> {
                newItemList.add(addItem(i));
            });
        }

        /** ************ Operation with Category Table ********** **/
        //Spliting Category Table

        List<TempCategory> tempCategory = new ArrayList<>();
        tempCategory.addAll(tempCategoryRepo.findAll());
        List<Category> category = categoryRepo.findAll();
        List<Category> categoryDeleted = new ArrayList<>(){};
        List<Category> newCategoryList = new ArrayList<>();

        //Getting categories to save
        tempCategory.forEach(cat -> {
            newCategoryList.add(addCategory(cat));
        });

        //Getting Categories to delete
        category.forEach(i -> {
            Integer count = 0;
            boolean exit = false;
            while (count < tempCategory.size() && !exit) {
                if(i.getId() == tempCategory.get(count).getId()) {
                    exit = true;
                }
                count++;
            }
            if(!exit){
                categoryDeleted.add(i);
            }
            else {
                int iter = count - 1;
                tempCategory.remove(iter);
            }
        });

        /** ************ Operation with Family Table ********** **/
        //Spliting Family Table

        List<TempFamily> tempFamily = new ArrayList<>();
        tempFamily.addAll(tempFamilyRepo.findAll());
        List<Family> family = familyRepo.findAll();
        List<Family> familyDeleted = new ArrayList<>(){};
        List<Family> newFamilyList = new ArrayList<>();

        //Getting families to save
        tempFamily.forEach(fam -> {
            newFamilyList.add(addFamily(fam));
        });

        //Getting Families to delete
        family.forEach(i -> {
            Integer count = 0;
            boolean exit = false;
            while (count < tempFamily.size() && !exit) {
                if(i.getId() == tempFamily.get(count).getId()) {
                    exit = true;
                }
                count++;
            }
            if(!exit){
                familyDeleted.add(i);
            }
            else {
                int iter = count - 1;
                tempFamily.remove(iter);
            }
        });


        /** ************ Operation with Brand Table ********** **/
        //Spliting Brand Table
        List<TempBrand> tempBrand = new ArrayList<>();
        tempBrand.addAll(tempBrandRepo.findAll());
        List<Brand> brand = brandRepo.findAll();
        List<Brand> brandDeleted = new ArrayList<>(){};
        List<Brand> newBrandList = new ArrayList<>();

        //Getting brands to save
        tempBrand.forEach(b -> {
            newBrandList.add(addBrand(b));
        });

        //Getting Brands to delete
        brand.forEach(i -> {
            Integer count = 0;
            boolean exit = false;
            while (count < tempBrand.size() && !exit) {
                if(i.getId() == tempBrand.get(count).getId()) {
                    exit = true;
                }
                count++;
            }
            if(!exit){
                brandDeleted.add(i);
            }
            else {
                int iter = count - 1;
                tempBrand.remove(iter);
            }
        });


        /** ********* Deleting Datas ******* **/
        //Delete the item that not exsist
        if(!saveOrDeleteItemsTable(itemsDeleted, "deleting")){
            return false;
        }
        //Delete the category that not exsist
        if(!saveOrDeleteCategoriesTable(categoryDeleted, "deleting")){
            return false;
        }
        //Delete the families that not exsist
        if(!saveOrDeleteFamiliesTable(familyDeleted, "deleting")){
            return false;
        }
        //Delete the brands that not exsist
        if(!saveOrDeleteBrandsTable(brandDeleted, "deleting")){
            return false;
        }


        /** ********* Saving and Updating Datas ******* **/
        //Brands
        if(!saveOrDeleteBrandsTable(newBrandList, "saving")){
            return false;
        }
        //Families
        if(!saveOrDeleteFamiliesTable(newFamilyList, "saving")){
            return false;
        }
        //Categories
        if(!saveOrDeleteCategoriesTable(newCategoryList, "saving")){
            return false;
        }
        //Items
        if(!saveOrDeleteItemsTable(newItemList, "saving")){
            return false;
        }
        /** *********  All OK  ************** **/
        updateTableLogs("Synchronization completed...", false);
        return true;
    }

    private boolean updateStock(){
        List<StockProcessor> stockList = new ArrayList<>();
        stockList = stockProcRepo.findAll();
        List<Item> itemsU = new ArrayList<>();
        itemsU = itemRepo.findAll();

        List<CheckingStockProcessor> checkingList = new ArrayList<>();
        List<StockProcessor> stockToAddOrUpdate = new ArrayList<>();

        if(!stockList.isEmpty()){
            List<Item> finalItemsU = itemsU;
            stockList.forEach(stock -> {
                boolean exit = false;
                AtomicInteger count = new AtomicInteger();
                CheckingStockProcessor checking = new CheckingStockProcessor();
                StockProcessor stockUpdated = new StockProcessor();

                while (count.get() < finalItemsU.size() && !exit){
                    //Existe el SKU en la tabla
                    if(finalItemsU.get(count.get()).getSku().equals(stock.getSku())){
                        exit = true;
                        stockUpdated.setId(stock.getId());
                        stockUpdated.setSku(stock.getSku());
                        stockUpdated.setExpectedStock(stock.getExpectedStock());
                        stockUpdated.setRealStock((int) finalItemsU.get(count.get()).getStockActual());

                        //el articulo esta pausado por stock
                        if((stock.getRealStock() - stock.getExpectedStock()) <= 10){
                            //Verifico si continua sin stock al actualizar
                            if((int) finalItemsU.get(count.get()).getStockActual() - stock.getExpectedStock() > 10){
                                checking.setSku(stock.getSku());
                                checking.setExpectedStock(stock.getExpectedStock());
                                int value = (int) finalItemsU.get(count.get()).getStockActual();
                                checking.setRealStock(value);
                                checkingList.add(checking);
                            }
                        }
                        //Articulo no pausado por stock -- Verifico si se pausa al actualizar
                        else{
                            if((int) finalItemsU.get(count.get()).getStockActual() - stock.getExpectedStock() <= 10){
                                checking.setSku(stock.getSku());
                                checking.setExpectedStock(stock.getExpectedStock());
                                int value = (int) finalItemsU.get(count.get()).getStockActual();
                                checking.setRealStock(value);
                                checkingList.add(checking);
                            }
                        }
                    }
                    count.getAndIncrement();
                }
                if(!exit) {
                    //No existe el articulo con SKU en la tabla -- adiciono a StockProcessor Table
                    stockUpdated.setSku(finalItemsU.get(count.get()).getSku());
                    stockUpdated.setExpectedStock(0);
                    stockUpdated.setRealStock((int) finalItemsU.get(count.get()).getStockActual());

                    //Verifico si cumple condicion de stock
                    if ((int) finalItemsU.get(count.get()).getStockActual() - 0 <= 10) {
                        checking.setSku(stockUpdated.getSku());
                        checking.setExpectedStock(0);
                        checking.setRealStock((int) finalItemsU.get(count.get()).getStockActual());
                        checkingList.add(checking);
                    }
                    stockToAddOrUpdate.add(stockUpdated);
                }
            });
        }
        //El stock está vacio -- Sistema nuevo
        else{
            itemsU.forEach(i -> {
                StockProcessor stockNew = new StockProcessor();
                CheckingStockProcessor checkingNew = new CheckingStockProcessor();
                //adiciono a StockProcessor Table
                stockNew.setSku(i.getSku());
                stockNew.setExpectedStock(0);
                stockNew.setRealStock((int) i.getStockActual());

                //Verifico si cumple condicion de stock
                if ((int) i.getStockActual() - 0 <= 10) {
                    checkingNew.setSku(i.getSku());
                    checkingNew.setExpectedStock(0);
                    checkingNew.setRealStock((int) i.getStockActual());
                    checkingList.add(checkingNew);
                }
                stockToAddOrUpdate.add(stockNew);
            });
        }

        //Actualizar ambas tablas en la base datos
        checkStockRepo.saveAll(checkingList);
        stockProcRepo.saveAll(stockToAddOrUpdate);
        return true;
    }

    private Item addItem(TempItem ti) {
        Item item = new Item();
        item.setSku(ti.getSku());
        item.setArtCantUnidades(ti.getArtCantUnidades());
        item.setArtCodPro(ti.getArtCodPro());
        item.setArtDescripCatalogo(ti.getArtDescripCatalogo());
        item.setArtDescripML(ti.getArtDescripML());
        item.setArtEspecificaciones(ti.getArtEspecificaciones());
        item.setArtMedida(ti.getArtMedida());
        item.setArtMostrarEnWeb(ti.getArtMostrarEnWeb());
        item.setArtNombreML(ti.getArtNombreML());
        item.setArtPreUniDolares(ti.getArtPreUniDolares());
        item.setArtPreUniPesos(ti.getArtPreUniPesos());
        item.setArtVendibleMercadoLibre(ti.getArtVendibleMercadoLibre());
        item.setCantidadPorCaja(ti.getCantidadPorCaja());
        item.setCapacidad(ti.getCapacidad());
        item.setFuturo(ti.getFuturo());
        item.setMedidaEmpaque(ti.getMedidaEmpaque());
        item.setImages(ti.getImages());
        item.setNuevo(ti.getNuevo());
        item.setOferta(ti.getOferta());
        item.setPrecioDolares(ti.getPrecioDolares());
        item.setPrecioPesos(ti.getPrecioPesos());
        item.setStockActual(ti.getStockActual());
        item.setTalle(ti.getTalle());
        List<Category> catList = new ArrayList<>();
        ti.getCategories().forEach(cat ->
        {
            Category category = new Category();
            category.setId(cat.getId());
            category.setDescription(cat.getDescription());
            catList.add(category);
        });
        if(catList.size() != 0)
            item.setCategories(catList);

        if(ti.getFamily() != null){
            Family family = new Family();
            family.setId(ti.getFamily().getId());
            family.setDescription(ti.getFamily().getDescription());
            item.setFamily(family);
        }

        if(ti.getBrand() != null) {
            Brand brand = new Brand();
            brand.setId(ti.getBrand().getId());
            brand.setDescription(ti.getBrand().getDescription());
            brand.setMarcaInUse(ti.getBrand().getMarcaInUse());
            item.setBrand(brand);
        }

        return item;
    }
    private Item updateItem(TempItem ti, Item item) {
        item.setArtCantUnidades(ti.getArtCantUnidades());
        item.setArtCodPro(ti.getArtCodPro());
        item.setArtDescripCatalogo(ti.getArtDescripCatalogo());
        item.setArtDescripML(ti.getArtDescripML());
        item.setArtEspecificaciones(ti.getArtEspecificaciones());
        item.setArtMedida(ti.getArtMedida());
        item.setArtMostrarEnWeb(ti.getArtMostrarEnWeb());
        item.setArtNombreML(ti.getArtNombreML());
        item.setArtPreUniDolares(ti.getArtPreUniDolares());
        item.setArtPreUniPesos(ti.getArtPreUniPesos());
        item.setArtVendibleMercadoLibre(ti.getArtVendibleMercadoLibre());
        item.setCantidadPorCaja(ti.getCantidadPorCaja());
        item.setCapacidad(ti.getCapacidad());
        item.setFuturo(ti.getFuturo());
        item.setMedidaEmpaque(ti.getMedidaEmpaque());
        item.setImages(ti.getImages());
        item.setNuevo(ti.getNuevo());
        item.setOferta(ti.getOferta());
        item.setPrecioDolares(ti.getPrecioDolares());
        item.setPrecioPesos(ti.getPrecioPesos());
        item.setStockActual(ti.getStockActual());
        item.setTalle(ti.getTalle());
        return item;
    }
    private Category addCategory(TempCategory cat) {
        Category category = new Category();
        category.setId(cat.getId());
        category.setDescription(cat.getDescription());
        return category;
    }
    private Brand addBrand(TempBrand b) {
        Brand brand = new Brand();
        brand.setId(b.getId());
        brand.setDescription(b.getDescription());
        brand.setMarcaInUse(b.getMarcaInUse());
        return brand;
    }
    private Family addFamily(TempFamily fami) {
        Family family = new Family();
        family.setId(fami.getId());
        family.setDescription(fami.getDescription());
        return family;
    }

    private boolean saveOrDeleteItemsTable(List<Item> list, String action){
        try {
            if(action.equals("deleting")) {
                if(list.size() != 0)
                    itemRepo.deleteAll(list);
            }else if(action.equals("saving")){
                itemRepo.saveAll(list);
            }
            return true;
        }catch (Exception e){
            logger.error(String.format("Error %s Item table {}", action), e.getStackTrace());
            updateTableLogs(String.format("Error %s Item table {}", action) + e.getMessage(), true);
            return false;
        }
    }
    private boolean saveOrDeleteBrandsTable(List<Brand> list, String action){
        try {
            if(action.equals("deleting")) {
                if(list.size() != 0)
                    brandRepo.deleteAll(list);
            }else if(action.equals("saving")){
                brandRepo.saveAll(list);
            }
            return true;
        }catch (Exception e){
            logger.error(String.format("Error %s Brand table {}", action), e.getStackTrace());
            updateTableLogs(String.format("Error %s Brand table {}", action) + e.getMessage(), true);
            return false;
        }
    }
    private boolean saveOrDeleteFamiliesTable(List<Family> list, String action){
        try {
            if(action.equals("deleting")) {
                if(list.size() != 0)
                    familyRepo.deleteAll(list);
            }else if(action.equals("saving")){
                familyRepo.saveAll(list);
            }
            return true;
        }catch (Exception e){
            logger.error(String.format("Error %s Family table {}", action), e.getStackTrace());
            updateTableLogs(String.format("Error %s Family table {}", action) + e.getMessage(), true);
            return false;
        }
    }
    private boolean saveOrDeleteCategoriesTable(List<Category> list, String action){

        try {
            if(action.equals("deleting")) {
                if(list.size() != 0)
                    categoryRepo.deleteAll(list);
            }else if(action.equals("saving")){
                categoryRepo.saveAll(list);
            }
            return true;
        }catch (Exception e){
            logger.error(String.format("Error %s Category table {}", action), e.getStackTrace());
            updateTableLogs(String.format("Error %s Category table {}", action) + e.getMessage(), true);
            return false;
        }
    }
}