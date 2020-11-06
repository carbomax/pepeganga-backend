package uy.pepeganga.meli.service.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import meli.ApiException;
import meli.model.*;
import meli.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uy.com.pepeganga.business.common.entities.*;
import uy.com.pepeganga.business.common.utils.enums.ChangeStatusPublicationType;
import uy.com.pepeganga.business.common.utils.methods.BurbbleSort;
import uy.pepeganga.meli.service.models.ApiMeliModelException;
import uy.pepeganga.meli.service.models.DetailsModelResponse;
import uy.pepeganga.meli.service.models.DetailsPublicationsMeliGrid;
import uy.pepeganga.meli.service.models.ItemModel;
import uy.pepeganga.meli.service.models.publications.*;
import uy.pepeganga.meli.service.repository.DetailsPublicationMeliRepository;
import uy.pepeganga.meli.service.repository.MercadoLibrePublishRepository;
import uy.pepeganga.meli.service.repository.SellerAccountRepository;
import uy.pepeganga.meli.service.repository.ProfileRepository;

import java.util.*;

@Service
public class MeliService  implements IMeliService{

    private static final Logger logger = LoggerFactory.getLogger(MeliService.class);

    @Autowired
    SellerAccountRepository sellerAccountRepository;

    @Autowired
    DetailsPublicationMeliRepository detailsPublicationRepository;

    @Autowired
    MercadoLibrePublishRepository mlPublishRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    IApiService apiService;

    @Autowired
    ObjectMapper mapper;

    private static final String ERROR = "error";
    private static final String MELI_ERROR = "error_meli";

    @Override
    public SellerAccount createAccountByProfile(Integer profileId, SellerAccount sellerAccount) {
        Optional<Profile> profileFound = profileRepository.findById(profileId);
        if(profileFound.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Profile with id: %s not found", profileId));
        }
        sellerAccount.setProfile(profileFound.get());
        return sellerAccountRepository.save(sellerAccount);
    }

    @Override
    public SellerAccount updateMeliAccount(Integer accountId, SellerAccount sellerAccount) {
        SellerAccount accountToUpdated = findAccountById(accountId);
        accountToUpdated.setNickname(sellerAccount.getNickname());
        accountToUpdated.setBusinessDescription(sellerAccount.getBusinessDescription());
        return sellerAccountRepository.save(accountToUpdated);
    }

    @Override
    public void deleteMeliAccount(Integer accountId) {
        findAccountById(accountId);
        sellerAccountRepository.deleteById(accountId);

    }

    @Override
    public List<SellerAccount> meliAccountsByProfileId(Integer profileId) {
        Optional<Profile> profileFound = profileRepository.findById(profileId);
        if(profileFound.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Profile with id: %s not found", profileId));
        }
        return profileFound.get().getSellerAccounts();
    }

    @Override
    public SellerAccount findAccountById(Integer accountId) {
        Optional<SellerAccount> accountFounded = sellerAccountRepository.findById(accountId);
        if(accountFounded.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Account with id: %s not found", accountId));
        }
        return accountFounded.get();
    }

    @Override
    public Map<String, Object> createPublication(Item publicationRequest, Integer accountId) {
        Optional<SellerAccount> accountFounded = sellerAccountRepository.findById(accountId);
        Map<String, Object> response = new HashMap<>();
        if (accountFounded.isEmpty()) {
            response.put(ERROR, new ApiMeliModelException(HttpStatus.NOT_FOUND.value(), String.format("Account with id: %s not found", accountId)));
            return response;
        } else {
/*
            // Example to post an item in Argentina
            List<ItemPictures> pictures = new ArrayList<>();
            String source =	"https://http2.mlstatic.com/storage/developers-site-cms-admin/openapi/319968615067-mp3.jpg";
            pictures.add(new ItemPictures().source(source));

            List<AttributesValues> attrValues = new ArrayList<>();
            attrValues.add(new AttributesValues().name("8 GB"));
            List<Attributes> attributes = new ArrayList<>();
            attributes.add(new Attributes()
                    .id("DATA_STORAGE_CAPACITY")
                    .name("Capacidad de almacenamiento de datos")
                    .valueName("8 GB")
                    .values(attrValues)
                    .attributeGroupName("Otros")
                    .attributeGroupId("OTHERS"));

            Shipping shipping = new Shipping("me2");
            shipping.localPickUp(false);
            shipping.freeShipping(false);
            shipping.methods(new ArrayList<>());

            List<SaleTerms> saleTerms = new ArrayList<>();
            saleTerms.add(new SaleTerms("WARRANTY_TYPE")
                    .name("Tipo de garantía")
                    .valueName("Garantia del vendedor")
                    .valueId("2230280"));

            Item item = new Item();
            item.title("Item de test2 - No Changes java");
            item.categoryId("MLU1915");
            item.price(350);
            item.currencyId("UYU");
            item.availableQuantity("12");
            item.buyingMode("buy_it_now");
            item.listingTypeId("bronze");
            item.condition("new");
            item.description("Item de Teste. Mercado Libre con cambios en la SDK");
            item.videoId("RXWn6kftTHY");
            item.pictures(pictures);
            item.attributes(attributes);
            item.shipping(shipping);
            item.saleTerms(saleTerms);*/
            try {
                response.put("response", apiService.createPublication(publicationRequest, accountFounded.get().getAccessToken()));
                return response;
            } catch (ApiException e) {
                logger.error(" Error getting token Meli Response: {}", e.getResponseBody());
                response.put(MELI_ERROR, new ApiMeliModelException(e.getCode(), e.getResponseBody()));
                return response;
            }

        }


    }

    @Override
    public List<Map<String, Object>> createPublicationList(List<Item> items, Integer accountId ) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        List<Map<String, Object>> responseList = new ArrayList<>();
        try {
            for (Item item : items) {
                String sku = "";
                Map<String, Object> response = createPublication(item, accountId);
                sku = item.getAttributes().stream().filter(m -> m.getId() == "SELLER_SKU").findFirst().isPresent() ?
                        item.getAttributes().stream().filter(m -> m.getId() == "SELLER_SKU").findFirst().get().getValueName() :
                        "WHITOUT-SKU";
                if(response.containsKey("response")){
                    resultMap.put("published", true);
                    resultMap.put("sku", sku);
                    resultMap.put("product", response.get("response"));
                    responseList.add(resultMap);
                }
                else{
                    resultMap.put("published", false);
                    resultMap.put("sku", sku);
                    resultMap.put("product", response.get("MELI_ERROR"));
                    responseList.add(resultMap);
                }
            }
            return responseList;
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public boolean createOrUpdateDetailPublicationsMeli(List<ItemModel> items, Integer accountId, Short idMargin){
        List<DetailsPublicationsMeli> detailsMeli = new ArrayList<>();
        List<MercadoLibrePublications> meliPublicationsList = new ArrayList<>();
        for (ItemModel iter: items) {
            DetailsPublicationsMeli detail = new DetailsPublicationsMeli();
            String valueName = "";
            for (Attributes attr1: iter.getItem().getAttributes()) {
                if(attr1.getId().equals("SELLER_SKU")){
                    valueName = attr1.getValueName();
                }
            }
            if(!valueName.isBlank()) {
                DetailsPublicationsMeli detailP = detailsPublicationRepository.findBySKUAndAccountId(valueName, accountId);
                if(detailP != null)
                    detail = detailP;
            }


            detail.setTitle(iter.getItem().getTitle());
            detail.setAccountMeli(accountId);
            detail.setCategoryMeli(iter.getItem().getCategoryId());
            detail.setMargin(idMargin);
            detail.setPricePublication(iter.getItem().getPrice());

            Optional<MercadoLibrePublications> meli = mlPublishRepository.findById(iter.getIdProduct());
            if(meli.isPresent()) {
                detail.setMlPublication(meli.get());
                var price = (double) iter.getItem().getPrice();
                meli.get().setPrice(price);
                meli.get().setStates((short)1);
                meliPublicationsList.add(meli.get());
            }
            else
                continue;

            if(iter.getItem().getSaleTerms() != null) {
                Optional<SaleTerms> warrantyType = iter.getItem().getSaleTerms().stream().filter(p -> p.getId().equals("WARRANTY_TYPE")).findFirst();
                detail.setWarrantyType(warrantyType.get().getValueName());

                Optional<SaleTerms> warrantyTime = iter.getItem().getSaleTerms().stream().filter(p -> p.getId().equals("WARRANTY_TIME")).findFirst();
                detail.setWarrantyTime(warrantyTime.get().getValueName());
            }
            detail.setStatus("in process");
            detailsMeli.add(detail);
        }
        detailsPublicationRepository.saveAll(detailsMeli);
        mlPublishRepository.saveAll(meliPublicationsList);
        return true;
    }

    @Override
    public boolean createPublicationsFlow(List<ItemModel> items, Integer accountId, Short idMargin) throws NoSuchFieldException {
        List<DetailsPublicationsMeli> detailsToUpdate = new ArrayList<>();

        if(createOrUpdateDetailPublicationsMeli(items, accountId, idMargin)){
            for (ItemModel item: items) {
                Map<String, Object> response = createPublication(item.getItem(), accountId);
                if(response.containsKey("response")){
                    Object obj = response.get("response");
                    DetailsModelResponse detailM = mapper.convertValue(obj, DetailsModelResponse.class);
                    String valueName="";
                    for (Attributes attr1: item.getItem().getAttributes()) {
                        if(attr1.getId().equals("SELLER_SKU")){
                            valueName = attr1.getValueName();
                        }
                    }
                    if(!valueName.isBlank()){
                        String sku = valueName;
                        DetailsPublicationsMeli detailP = detailsPublicationRepository.findBySKUAndAccountId(sku, accountId);
                        detailP.setStatus(detailM.getStatus());
                        detailP.setIdPublicationMeli(detailM.getIdPublication());
                        detailP.setLastUpgrade(detailM.getLastUpdated());
                        detailP.setPermalink(detailM.getPermalink());
                        detailsToUpdate.add(detailP);
                    }
                }
                else{
                    String valueName="";
                    for (Attributes attr1: item.getItem().getAttributes()) {
                        if(attr1.getId().equals("SELLER_SKU")){
                            valueName = attr1.getValueName();
                        }
                    }
                    if(!valueName.isBlank()){
                        String sku = valueName;
                        DetailsPublicationsMeli detailP = detailsPublicationRepository.findBySKUAndAccountId(sku, accountId);
                        detailP.setStatus("fail");
                        detailsToUpdate.add(detailP);
                    }
                }
            }
            detailsPublicationRepository.saveAll(detailsToUpdate);
        }
    return true;
    }

//implementar esto
    @Override
    public DetailsPublicationsMeliGrid republishProduct(DetailsPublicationsMeliGrid product) throws ApiException {
        Optional<SellerAccount> accountFounded = sellerAccountRepository.findById(product.getAccountMeli());
        Map<String, Object> response = new HashMap<>();
        DetailsPublicationsMeliGrid productResponse = new DetailsPublicationsMeliGrid();
        if (accountFounded.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND.value(), String.format("No se encontro la cuenta: %s", product.getAccountName()));
        }
        else {
            try {
                DescriptionRequest descriptionRequest = new DescriptionRequest();
                descriptionRequest.setDescription(product.getDescription());
                response.put("response", apiService.updateDescription(descriptionRequest, accountFounded.get().getAccessToken(), product.getIdPublicationMeli()));

                //Para las imagenes
                Source source = new Source();
                List<Source> sources = new ArrayList<>();

                //Ordeno el arreglo segun orden de ubicacion de las imagenes
                List<Image> newImageList= BurbbleSort.burbbleLowerToHigher(product.getImages());
                for (Image image: newImageList) {
                    source.setSource(image.getPhotos());
                    sources.add(source);
                }

                //Producto Con ventas
                if (product.getSaleStatus() == 1) {
                    PropertiesWithSalesRequest withSaleRequest = new PropertiesWithSalesRequest();
                    withSaleRequest.setPrice(product.getPricePublication());
                    withSaleRequest.setPictures(sources);
                    response.put("response", apiService.updatePropertiesWithSales(withSaleRequest, accountFounded.get().getAccessToken(), product.getIdPublicationMeli()));

                 //Producto Sin ventas
                } else if (product.getSaleStatus() == 0) {
                    PropertiesWithoutSalesRequest withoutSaleRequest = new PropertiesWithoutSalesRequest();
                    withoutSaleRequest.setPrice(product.getPricePublication());
                    withoutSaleRequest.setPictures(sources);
                    withoutSaleRequest.setTitle(product.getTitle());
                    response.put("response", apiService.updatePropertiesWithoutSales(withoutSaleRequest, accountFounded.get().getAccessToken(), product.getIdPublicationMeli()));
                }

                //Construyo objeto para retornar
                if(response.containsKey("response")){
                    Object obj = response.get("response");
                    DetailsModelResponse detailM = mapper.convertValue(obj, DetailsModelResponse.class);
                    String sku = product.getSku();
                    DetailsPublicationsMeli detailP = detailsPublicationRepository.findBySKUAndAccountId(sku, product.getAccountMeli());
                    detailP.setLastUpgrade(detailM.getLastUpdated());
                    detailsPublicationRepository.save(detailP);
                    productResponse = product;
                    productResponse.setLastUpgrade(detailM.getLastUpdated());
                    return productResponse;
                }
                else{
                    throw new ApiException(HttpStatus.CONFLICT.value(), "Fallo actualizando producto en Mercado Libre");
                }
            }
            catch (ApiException e){
                //comprobar los codigos de Token Vencido
                logger.error(" Error obteniendo el token de seguridad: {}", e.getResponseBody());
                throw new ApiException(HttpStatus.CONFLICT.value(), String.format("Error obteniendo el token de seguridad: %s", e.getResponseBody()));
            }
        }
    }

    public Map<String, Object> updatePropertiesWithoutSales(PropertiesWithoutSalesRequest product, String token, String idPublicationMeli){
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("response", apiService.updatePropertiesWithoutSales(product, token, idPublicationMeli));
            return response;
        } catch (ApiException e) {
            //comprobar los codigos de Token Vencido
            logger.error(" Error obteniendo el token de seguridad: {}", e.getResponseBody());
            response.put(MELI_ERROR, new ApiMeliModelException(e.getCode(), e.getResponseBody()));
            return response;
        }
    }

    public Map<String, Object> updatePropertiesWithSales(PropertiesWithSalesRequest product, String token, String idPublicationMeli){
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("response", apiService.updatePropertiesWithSales(product, token, idPublicationMeli));
            return response;
        } catch (ApiException e) {
            //comprobar los codigos de Token Vencido
            logger.error(" Error obteniendo el token de seguridad: {}", e.getResponseBody());
            response.put(MELI_ERROR, new ApiMeliModelException(e.getCode(), e.getResponseBody()));
            return response;
        }
    }

    public Map<String, Object> updateDescription(DescriptionRequest product, String token, String idPublicationMeli){
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("response", apiService.updateDescription(product, token, idPublicationMeli));
            return response;
        } catch (ApiException e) {
            //comprobar los codigos de Token Vencido
            logger.error(" Error obteniendo el token de seguridad: {}", e.getResponseBody());
            response.put(MELI_ERROR, new ApiMeliModelException(e.getCode(), e.getResponseBody()));
            return response;
        }
    }

    @Override
    public Map<String, Object> changeStatusPublication(Integer accountId, int status, String idPublication) {

        Map<String, Object> response = new HashMap<>();
        ChangeStatusPublicationRequest request = new ChangeStatusPublicationRequest();

        try {
            request.setStatus(ChangeStatusPublicationType.ofCode(status).getStatus());
        } catch (IllegalArgumentException e){
            logger.error("The status: {} you provide is not correct", status);
             response.put(ERROR, new ApiMeliModelException(HttpStatus.BAD_REQUEST.value(), String.format("The status: %d you provide is not correct", status)));
            return response;
        }


        DetailsPublicationsMeli details = detailsPublicationRepository.findByIdPublicationMeli(idPublication);
        if(Objects.isNull(details)){
            logger.error("Detail Publication with id: {} not found", idPublication);
            response.put(ERROR, new ApiMeliModelException(HttpStatus.NOT_FOUND.value(), String.format("Account with id: %s not found", accountId)));
        } else {
            Optional<SellerAccount> accountFounded = sellerAccountRepository.findById(accountId);
            if (accountFounded.isEmpty()) {
                logger.error("Account with id: {} not found", accountId);
                response.put(ERROR, new ApiMeliModelException(HttpStatus.NOT_FOUND.value(), String.format("Account with id: %s not found", accountId)));
            } else {
                try {
                    Object result = apiService.changeStatusPublications(request, accountFounded.get().getAccessToken(), idPublication);
                    if (!Objects.isNull(result)) {
                        details.setStatus(ChangeStatusPublicationType.ofCode(status).getStatus());
                        response.put("response", detailsPublicationRepository.save(details).getStatus().trim());
                    } else {
                        logger.error("Publication not changed: status to change: {}, publicationId: {}", status, idPublication);
                        response.put(ERROR,  ChangeStatusPublicationType.ofCode(-1).getStatus());
                    }
                } catch (ApiException e) {
                    if(e.getCode() == 401){
                        try {
                            SellerAccount newTokenAccount = apiService.getTokenByRefreshToken(accountFounded.get());
                            Object result =  apiService.changeStatusPublications(request, newTokenAccount.getAccessToken(), idPublication);
                            if (!Objects.isNull(result)) {
                                details.setStatus(ChangeStatusPublicationType.ofCode(status).getStatus());
                                response.put("response", detailsPublicationRepository.save(details).getStatus().trim());
                            } else {
                                logger.error("Publication not changed: status to change: {}, publicationId: {}", status, idPublication);
                                response.put(ERROR,  ChangeStatusPublicationType.ofCode(-1));
                            }
                        } catch (ApiException ex) {
                            logger.error(ex.getMessage(), ex);
                            response.put(MELI_ERROR,  new ApiMeliModelException(e.getCode(), e.getResponseBody()));
                        }
                    } else response.put(MELI_ERROR, new ApiMeliModelException(e.getCode(), e.getResponseBody()));
                }

            }
        }

        return response;
    }


}
