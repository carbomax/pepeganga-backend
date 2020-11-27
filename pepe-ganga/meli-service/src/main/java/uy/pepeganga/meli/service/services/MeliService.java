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
import uy.com.pepeganga.business.common.utils.enums.*;
import uy.com.pepeganga.business.common.utils.methods.BurbbleSort;
import uy.pepeganga.meli.service.exceptions.TokenException;
import uy.pepeganga.meli.service.models.ApiMeliModelException;
import uy.pepeganga.meli.service.models.DetailsModelResponse;
import uy.pepeganga.meli.service.models.DetailsPublicationsMeliGrid;
import uy.pepeganga.meli.service.models.ItemModel;
import uy.pepeganga.meli.service.models.publications.*;
import uy.pepeganga.meli.service.repository.*;
import uy.pepeganga.meli.service.utils.MapResponseConstants;
import uy.pepeganga.meli.service.utils.MeliUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class MeliService  implements IMeliService{

    private static final Logger logger = LoggerFactory.getLogger(MeliService.class);
    private static Optional<SellerAccount> accountMeli;

    @Autowired
    SellerAccountRepository sellerAccountRepository;

    @Autowired
    DetailsPublicationMeliRepository detailsPublicationRepository;

    @Autowired
    ImageDetailPublicationRepository imageDPRepository;

    @Autowired
    MercadoLibrePublishRepository mlPublishRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    IApiService apiService;

    @Autowired
    ObjectMapper mapper;

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
        Optional<SellerAccount> accountFounded = getAccountMeli(accountId, false);
        Map<String, Object> response = new HashMap<>();
        try {
            if (accountFounded.isEmpty()) {
                response.put(MapResponseConstants.ERROR, new ApiMeliModelException(HttpStatus.NOT_FOUND.value(), String.format("Account with id: %s not found", accountId)));
                return response;
            }
            else if(!MeliUtils.validateTokenExpiration(accountFounded.get().getExpirationDate())){
                accountFounded = Optional.ofNullable(apiService.getTokenByRefreshToken(accountFounded.get()));
                accountMeli = accountFounded;
            }
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

                response.put("response", apiService.createPublication(publicationRequest, accountFounded.get().getAccessToken()));
                return response;

        }  catch (TokenException e) {
            logger.error(" Error getting token Meli Response: {}", e.getMessage());
            response.put(MapResponseConstants.MELI_ERROR, new ApiMeliModelException(e.getCode(), "Error al obtener token de Mercado Libre. Pude que la API este presentando problema de conexión"));
            return response;
        }catch (ApiException e) {
            logger.error(" Error in the system: {}", e.getResponseBody());
            response.put(MapResponseConstants.MELI_ERROR, new ApiMeliModelException(e.getCode(), "Error en el sistema. Contacte al administrador del sistema"));
            return response;
        }
    }

    // (Verify to delete method if this is no used) OJO
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
            DetailsPublicationsMeli detailP = null;
            if(!iter.getSku().isBlank()) {
                detailP = detailsPublicationRepository.findBySKUAndAccountId(iter.getSku(), accountId);
                if(detailP != null) {
                    detail = detailP;
                    detail.setImages(iter.getImages());
                }
            }

            detail.setTitle(iter.getItem().getTitle());
            detail.setAccountMeli(accountId);
            detail.setCategoryMeli(iter.getItem().getCategoryId());
            detail.setMargin(idMargin);
            detail.setPricePublication(iter.getItem().getPrice());
            detail.setPriceEditProduct(iter.getPriceEditProduct());
            detail.setDescription(iter.getItem().getDescription());
            detail.setUserId(getAccountMeli(accountId, true).get().getUserId());
            detail.setPriceCostUSD(iter.getPriceCostUSD());
            detail.setPriceCostUYU(iter.getPriceCostUYU());
            if(detailP == null){
                detail.setSku(iter.getSku());
                iter.getImages().forEach(i ->i.setId(null));
                detail.setImages(iter.getImages());
            }


            Optional<MercadoLibrePublications> meli = mlPublishRepository.findById(iter.getIdPublicationProduct());
            if(meli.isPresent() && meli.get().getStates() == States.NOPUBLISHED.getId()) {
                detail.setIdMLPublication(meli.get().getId());
                meli.get().setStates((short)1);
                meliPublicationsList.add(meli.get());
            }
            else if(meli.isPresent() && meli.get().getStates() == States.PUBLISHED.getId()){}
            else
                continue;

            if(iter.getItem().getSaleTerms() != null) {
                Optional<SaleTerms> warrantyType = iter.getItem().getSaleTerms().stream().filter(p -> p.getId().equals("WARRANTY_TYPE")).findFirst();
                detail.setWarrantyType(warrantyType.get().getValueName());

                Optional<SaleTerms> warrantyTime = iter.getItem().getSaleTerms().stream().filter(p -> p.getId().equals("WARRANTY_TIME")).findFirst();
                detail.setWarrantyTime(warrantyTime.get().getValueName());
            }
            detail.setStatus(MeliStatusPublications.IN_PROCESS.getValue());
            detail.setDeleted(0);
            detailsMeli.add(detail);
        }
        detailsPublicationRepository.saveAll(detailsMeli);
        if(!meliPublicationsList.isEmpty()) {
            mlPublishRepository.saveAll(meliPublicationsList);
        }
        return true;
    }

    //Global Method to that use "createOrUpdateDetailPublicationsMeli" and "createPublication" methods to store and publish
    // one product in ML
    @Override
    public boolean createPublicationsFlow(List<ItemModel> items, Integer accountId, Short idMargin) throws NoSuchFieldException {
        List<DetailsPublicationsMeli> detailsToUpdate = new ArrayList<>();

        if(createOrUpdateDetailPublicationsMeli(items, accountId, idMargin)){
            for (ItemModel item: items) {
                Map<String, Object> response = createPublication(item.getItem(), accountId);
                if(response.containsKey("response")){
                    Object obj = response.get("response");
                    DetailsModelResponse detailM = mapper.convertValue(obj, DetailsModelResponse.class);
                    if(!item.getSku().isBlank()){
                        DetailsPublicationsMeli detailP = detailsPublicationRepository.findBySKUAndAccountId(item.getSku(), accountId);
                        detailP.setStatus(detailM.getStatus());
                        detailP.setIdPublicationMeli(detailM.getIdPublication());
                        detailP.setLastUpgrade(detailM.getLastUpdated());
                        detailP.setPermalink(detailM.getPermalink());
                        detailsToUpdate.add(detailP);
                    }
                }
                else{
                    if(!item.getSku().isBlank()){
                        DetailsPublicationsMeli detailP = detailsPublicationRepository.findBySKUAndAccountId(item.getSku(), accountId);
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
    public DetailsPublicationsMeliGrid updateProductPublished(DetailsPublicationsMeliGrid product) throws ApiException {
        try {
        Optional<SellerAccount> accountFounded = sellerAccountRepository.findById(product.getAccountMeli());
        Map<String, Object> response = new HashMap<>();
        DetailsPublicationsMeliGrid productResponse = new DetailsPublicationsMeliGrid();
        if (accountFounded.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND.value(), String.format("No se encontro la cuenta: %s", product.getAccountName()));
        }
        else if(!MeliUtils.validateTokenExpiration(accountFounded.get().getExpirationDate())){
            apiService.getTokenByRefreshToken(accountFounded.get());
            accountFounded = sellerAccountRepository.findById(product.getAccountMeli());
        }

            DescriptionRequest descriptionRequest = new DescriptionRequest();
            descriptionRequest.setDescription(product.getDescription());
            response.put("response", apiService.updateDescription(descriptionRequest, accountFounded.get().getAccessToken(), product.getIdPublicationMeli()));

            //Para las imagenes
            Source source = new Source();
            List<Source> sources = new ArrayList<>();

            //Ordeno el arreglo segun orden de ubicacion de las imagenes
            List<ImagePublicationMeli> newImageList= BurbbleSort.burbbleLowerToHigherByImagesDetails(product.getImages());
            for (ImagePublicationMeli image: newImageList) {
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
                detailP.setPricePublication(detailM.getPrice());
                detailP.setMargin(product.getMargin());
                detailsPublicationRepository.save(detailP);
                productResponse = product;
                productResponse.setLastUpgrade(detailM.getLastUpdated());
                return productResponse;
            }
            else{
                throw new ApiException(HttpStatus.CONFLICT.value(), "Fallo actualizando producto en Mercado Libre");
            }
        }
        catch (TokenException e) {
            logger.error(" Error getting token Meli Response: {}", e.getMessage());
            //response.put(MELI_ERROR, new ApiMeliModelException(e.getCode(), "Error al obtener token de Mercado Libre. Pude que la API este presentando problema de conexión"));
        throw new ApiException(e.getCode(), "Error al obtener token de Mercado Libre. Pude que la API este presentando problema de conexión");
        }
        catch (ApiException e){
            //comprobar los codigos de Token Vencido
            logger.error(" Error actualizando publicaciones: {}", e.getResponseBody());
            throw new ApiException(HttpStatus.CONFLICT.value(), String.format("Error obteniendo el token de seguridad: %s", e.getResponseBody()));
        }
        catch (Exception e){
            //comprobar los codigos de Token Vencido
            logger.error(" Error en el sistema: {}", e.getMessage());
            throw new ApiException(HttpStatus.CONFLICT.value(), String.format("Error en el sistema: %s", e.getMessage()));
        }
    }

    @Override
    public Map<String, Object> deletePublication(Integer accountId, String statusPublication, String idPublication){
        boolean delete = false;
        Map<String, Object> response = new HashMap<>();
        DeletePublicationRequest request = new DeletePublicationRequest();
        try {
            DetailsPublicationsMeli details = detailsPublicationRepository.findByIdPublicationMeli(idPublication);
            if(Objects.isNull(details)){
                logger.error("Detail Publication with id: {} not found", idPublication);
                response.put(MapResponseConstants.ERROR, new ApiMeliModelException(HttpStatus.NOT_FOUND.value(), String.format("Account with id: %s not found", accountId)));
                return response;
            }
            if (!statusPublication.equals(ChangeStatusPublicationType.CLOSED.getStatus())) {
                var result = changeStatusPublication(accountId, 5, idPublication);
                if(!result.containsKey("response")){
                    //Hubo un error que ya fue registrado en el metodo que se llamó
                    return result;
                }
                details.setStatus(MeliStatusPublications.CLOSED.getValue());
                detailsPublicationRepository.save(details);
            }
          //Si llega aquí es porque la publicacion ya está en estado closed
            Optional<SellerAccount> accountFounded = getAccountMeli(accountId, false);
            if (accountFounded.isEmpty()) {
                logger.error("Account with id: {} not found", accountId);
                response.put(MapResponseConstants.ERROR, new ApiMeliModelException(HttpStatus.NOT_FOUND.value(), String.format("Account with id: %s not found", accountId)));
                return response;
            }
            else if(MeliUtils.isExpiredToken(accountFounded.get())){
                accountFounded = Optional.ofNullable(apiService.getTokenByRefreshToken(accountFounded.get()));
            }
            Object result = apiService.deletePublication(request, accountFounded.get().getAccessToken(), idPublication);
            if (!Objects.isNull(result)) {
                Map<String, Object> map = setProductToNopublishedStatus(details.getIdMLPublication(), States.NOPUBLISHED.getId());
                if(map.containsKey("response")) {
                    details.setDeleted(1);
                    details.setIdMLPublication(-1);
                    detailsPublicationRepository.save(details);
                    response.put("response", "deleted");
                }else{
                    response.putAll(map);
                }
            } else {
                logger.error("Publication cannot be deleted by Mercado Libre, publicationId: {}", idPublication);
                response.put(MapResponseConstants.MELI_ERROR, ChangeStatusPublicationType.ofCode(-1).getStatus());
            }
            return response;
        }catch (IllegalArgumentException e){
            logger.error("The status: {} you provide is not correct");
            response.put(MapResponseConstants.ERROR, new ApiMeliModelException(HttpStatus.BAD_REQUEST.value(), String.format("The status that you provide is not correct")));
            return response;
        } catch (TokenException e) {
            logger.error(" Error getting token Meli Response: {}", e.getMessage());
            response.put(MapResponseConstants.MELI_ERROR, new ApiMeliModelException(e.getCode(), "Error al obtener token de Mercado Libre. Pude que la API este presentando problema de conexión"));
            return response;
        }catch (ApiException e) {
            if(e.getCode() == 409){
                logger.error(String.format("You must wait a few seconds for the change to update to, publicationId: {}", idPublication), e.getCode());
                response.put(MapResponseConstants.MELI_ERROR, new ApiMeliModelException(e.getCode(), String.format("You must wait a few seconds for the change to update to, publicationId: {}", idPublication)));
            }else{
                logger.error(String.format("Publication cannot be deleted, publicationId: {}", idPublication), e.getCode());
                response.put(MapResponseConstants.MELI_ERROR, new ApiMeliModelException(e.getCode(), String.format("Publication cannot be deleted, publicationId: {}", idPublication)));
            }
            return response;
        }
    }

    @Override
    public Map<String, Object> deletePublicationFailed(Integer id){
        Map<String, Object> response = new HashMap<>();
        try{
             Optional<DetailsPublicationsMeli> details = detailsPublicationRepository.findById(id);
             if(details.isPresent()){
                 Map<String, Object> map = setProductToNopublishedStatus(details.get().getIdMLPublication(), States.NOPUBLISHED.getId());
                 details.get().setDeleted(1);
                 details.get().setIdMLPublication(-1);
                 detailsPublicationRepository.save(details.get());

                 if(map.containsKey("response")) {
                     response.put("response", "deleted");
                 }else{
                     response.putAll(map);
                 }
             }else{
                 logger.error("Publication not found");
                 response.put(ActionResult.NOT_FOUND.getValue(), "Publication not found");
             }
            return response;
         }catch (Exception e){
             logger.error("Error storing in Data Base:", e.getStackTrace());
             response.put(ActionResult.DATABASE_ERROR.getValue(), "Error storing in database");
             return response;
         }
    }
    @Override
    public Map<String, Object> republishPublication(Integer accountId, String idPublication) {
        Map<String, Object> response = new HashMap<>();
        RepublishPublicationRequest request = new RepublishPublicationRequest();

        try {
            DetailsPublicationsMeli details = detailsPublicationRepository.findByIdPublicationMeli(idPublication);
            if(Objects.isNull(details)){
                logger.error("Detail Publication with id: {} not found", idPublication);
                response.put(MapResponseConstants.ERROR, new ApiMeliModelException(HttpStatus.NOT_FOUND.value(), String.format("Account with id: %s not found", accountId)));
            } else {
                Optional<SellerAccount> accountFounded = sellerAccountRepository.findById(accountId);
                if (accountFounded.isEmpty()) {
                    logger.error("Account with id: {} not found", accountId);
                    response.put(MapResponseConstants.ERROR, new ApiMeliModelException(HttpStatus.NOT_FOUND.value(), String.format("Account with id: %s not found", accountId)));
                } else {
                    if(!MeliUtils.validateTokenExpiration(accountFounded.get().getExpirationDate())){
                        accountFounded = Optional.ofNullable(apiService.getTokenByRefreshToken(accountFounded.get()));
                    }
                    request.setListing_type_id("bronze");
                    request.setPrice(details.getPricePublication());
                    request.setQuantity((int) itemRepository.findById(details.getSku()).get().getStockActual());
                    Object obj = apiService.republishPublication(request, accountFounded.get().getAccessToken(), idPublication);
                    if (!Objects.isNull(obj)){
                        DetailsModelResponse detailM = mapper.convertValue(obj, DetailsModelResponse.class);
                        details.setStatus(detailM.getStatus());
                        details.setIdPublicationMeli(detailM.getIdPublication());
                        details.setLastUpgrade(detailM.getLastUpdated());
                        details.setPermalink(detailM.getPermalink());
                        response.put(MapResponseConstants.RESPONSE, detailsPublicationRepository.save(details).getStatus().trim());
                    }
                    else{
                        logger.error("The product do not republished");
                        response.put(MapResponseConstants.MELI_ERROR, new ApiMeliModelException(HttpStatus.NOT_MODIFIED.value(), String.format("The product do not republished")));
                    }
                }
            }
            return response;
        }
        catch (TokenException e) {
            logger.error(" Error getting token Meli Response: {}", e.getMessage());
            response.put(MapResponseConstants.MELI_ERROR, new ApiMeliModelException(e.getCode(), "Error al obtener token de Mercado Libre. Pude que la API este presentando problema de conexión"));
        }
        catch (ApiException e) {
            //if(e.getCode() == 400) ya esta eliminada
            logger.error(" Error With MErcado Libre API: {}", e.getMessage());
            response.put(MapResponseConstants.MELI_ERROR, new ApiMeliModelException(e.getCode(), e.getResponseBody()));
        }
        catch (Exception e){
            logger.error(" Error of the system: {}", e.getMessage());
            response.put(MapResponseConstants.ERROR, new ApiMeliModelException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
        return response;
    }

    @Override
    public Map<String, Object> republishMultiplePublications(List<ChangeMultipleStatusRequest> multiple){
        Map<String, Object> response = new HashMap<>();

        for (ChangeMultipleStatusRequest one: multiple) {
            for (String idPublication: one.getPublicationsIds()) {
                var result = republishPublication(one.getAccountId(), idPublication);
                response.putAll(result);
            }
        }
        return response;
    }

    public Map<String, Object> updatePropertiesWithoutSales(PropertiesWithoutSalesRequest product, String token, String idPublicationMeli){
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("response", apiService.updatePropertiesWithoutSales(product, token, idPublicationMeli));
            return response;
        } catch (ApiException e) {
            //comprobar los codigos de Token Vencido
            logger.error(" Error obteniendo el token de seguridad: {}", e.getResponseBody());
            response.put(MapResponseConstants.MELI_ERROR, new ApiMeliModelException(e.getCode(), e.getResponseBody()));
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
            response.put(MapResponseConstants.MELI_ERROR, new ApiMeliModelException(e.getCode(), e.getResponseBody()));
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
            response.put(MapResponseConstants.MELI_ERROR, new ApiMeliModelException(e.getCode(), e.getResponseBody()));
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
             response.put(MapResponseConstants.ERROR, new ApiMeliModelException(HttpStatus.BAD_REQUEST.value(), String.format("The status: %d you provide is not correct", status)));
            return response;
        }

        DetailsPublicationsMeli details = detailsPublicationRepository.findByIdPublicationMeli(idPublication);
        if(Objects.isNull(details)){
            logger.error("Detail Publication with id: {} not found", idPublication);
            response.put(MapResponseConstants.ERROR, new ApiMeliModelException(HttpStatus.NOT_FOUND.value(), String.format("Account with id: %s not found", accountId)));
        } else {
            Optional<SellerAccount> accountFounded = sellerAccountRepository.findById(accountId);
            if (accountFounded.isEmpty()) {
                logger.error("Account with id: {} not found", accountId);
                response.put(MapResponseConstants.ERROR, new ApiMeliModelException(HttpStatus.NOT_FOUND.value(), String.format("Account with id: %s not found", accountId)));
            } else {
                try {
                    if(MeliUtils.isExpiredToken(accountFounded.get())){
                        accountFounded = Optional.ofNullable(apiService.getTokenByRefreshToken(accountFounded.get()));
                    }
                    Object result = apiService.changeStatusPublications(request, accountFounded.get().getAccessToken(), idPublication);
                    if (!Objects.isNull(result)) {
                        details.setStatus(ChangeStatusPublicationType.ofCode(status).getStatus());
                        response.put(MapResponseConstants.RESPONSE, detailsPublicationRepository.save(details).getStatus().trim());
                    } else {
                        logger.error("Publication not changed: status to change: {}, publicationId: {}", status, idPublication);
                        response.put(MapResponseConstants.ERROR,  ChangeStatusPublicationType.ofCode(-1).getStatus());
                    }
                }
                catch (TokenException e) {
                    logger.error(" Error getting token Meli Response: {}", e.getMessage());
                    response.put(MapResponseConstants.MELI_ERROR, new ApiMeliModelException(e.getCode(), "Error al obtener token de Mercado Libre. Pude que la API este presentando problema de conexión"));
                }
                catch (ApiException e) {
                   response.put(MapResponseConstants.MELI_ERROR, new ApiMeliModelException(e.getCode(), e.getResponseBody()));
                }

            }
        }

        return response;
    }

    public Map<String, Object> changeStatusMultiplePublications(List<ChangeMultipleStatusRequest> multiple, int status){
        Map<String, Object> response = new HashMap<>();

        for (ChangeMultipleStatusRequest one: multiple) {
            for (String idPublication: one.getPublicationsIds()) {
                var result = changeStatusPublication(one.getAccountId(), status, idPublication);
                response.putAll(result);
            }
        }
        return response;
    }

    @Override
    public void updatePricePublication(Margin margin, Integer idProfile){
        List<SellerAccount> accountList;
        List<Integer> accountIdList;
        List<DetailsPublicationsMeli> detailsPublicationUpdatedList = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Profile> profileDb = profileRepository.findById(idProfile);
            if(!profileDb.isPresent()){
                logger.error(String.format("Error, the profile with id %s not found: {}: ", idProfile));
                return;
            }
            accountList = profileDb.get().getSellerAccounts();
            accountIdList = accountList.stream().map(SellerAccount::getId).collect(Collectors.toList());
            List<DetailsPublicationsMeli> detailsPublicationList = detailsPublicationRepository.findByIdAccountsAndMargin(accountIdList, margin.getId(), 0);

            //Update price and pending to all publications
            for (DetailsPublicationsMeli detail: detailsPublicationList) {
                Integer price;
                if(margin.getType() == MarginType.PERCENT.getCode()){
                    price = Math.toIntExact(Math.round((detail.getPriceEditProduct() * (margin.getValue() / 100)) + detail.getPriceEditProduct()));
                }
                else{
                    price = Math.toIntExact(Math.round(detail.getPriceEditProduct() + margin.getValue()));
                }
                detail.setPendingMarginUpdate(true);
                detail.setPricePublication(price);
            }

            detailsPublicationRepository.saveAll(detailsPublicationList);

            //Update all publications in ML if these are in 'active' status
            List<SellerAccount> finalAccountList = accountList;
            for (DetailsPublicationsMeli detail: detailsPublicationList) {
                if(detail.getStatus().equals(MeliStatusPublications.ACTIVE.getValue())){
                    ChangePriceRequest changePrice = new ChangePriceRequest(detail.getPricePublication());
                    Optional<SellerAccount> accountFounded = finalAccountList.stream().filter(a -> a.getId() == detail.getAccountMeli()).findFirst();
                    try {
                        if(!MeliUtils.validateTokenExpiration(accountFounded.get().getExpirationDate())){
                            accountFounded = Optional.ofNullable(apiService.getTokenByRefreshToken(accountFounded.get()));
                        }

                        Object obj = apiService.updatePricePublication(changePrice, accountFounded.get().getAccessToken(), detail.getIdPublicationMeli());
                        //comprobar codigo de actualizado -- 200 OK
                        detail.setPendingMarginUpdate(false);
                        detailsPublicationUpdatedList.add(detail);
                    } catch (ApiException e) {
                        logger.error(" Error de Mercado Libre: {}", e.getResponseBody());
                    }catch (TokenException e) {
                        logger.error(" Error getting token Meli Response: {}", e.getMessage());
                    }
                }
            }

            if(!detailsPublicationUpdatedList.isEmpty()) {
                detailsPublicationRepository.saveAll(detailsPublicationUpdatedList);
            }
        }
        catch (Exception e){
            logger.error(" Error of the system: {}", e.getMessage());
        }
    }

    @Override
    public Map<String, Object> synchronizePublication(Integer idProfile, List<Integer> idDetailsPublicationsList) {
        List<DetailsPublicationsMeli> toUpdate = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();
        try {
            List<DetailsPublicationsMeli> detailsList = detailsPublicationRepository.findAllById(idDetailsPublicationsList);
            if (!detailsList.isEmpty()) {
                //Sincroniza los estados de las publicaciones en ML con la base datos
                detailsList.forEach(d -> {
                    Map<String, Object> status = apiService.getStatusPublication(d.getIdPublicationMeli());
                    if (status.containsKey(MapResponseConstants.RESPONSE)) {
                        Object obj = status.get(MapResponseConstants.RESPONSE);
                        MeliCodeResponse detailM = mapper.convertValue(obj, MeliCodeResponse.class);
                        if (!detailM.getBody().getStatus().equals(d.getStatus())) {
                            d.setStatus(detailM.getBody().getStatus());
                            toUpdate.add(d);
                        }
                    }
                });
                if (!toUpdate.isEmpty()) {
                    detailsPublicationRepository.saveAll(toUpdate);
                }

                //Actualiza las publicaciones con cambios pendientes
                List<DetailsPublicationsMeli> toUpdateList = detailsList.stream().filter(d -> d.getStatus().equals(MeliStatusPublications.ACTIVE.getValue()) && d.getPendingMarginUpdate()).collect(Collectors.toList());
                if (!toUpdateList.isEmpty()) {
                    response.putAll(updatePriceMeliOfActivePublications(idProfile, toUpdateList));
                } else {
                    response.put(MapResponseConstants.RESPONSE, "No existen publicaciones para actualizar");
                }
            }
            return response;
        }catch (Exception e){
            logger.error(String.format("Error of systems "), e.getStackTrace());
            response.put(ActionResult.ERROR.getValue(), String.format("Error of systems: {}: ", e.getMessage()));
            return response;
        }
    }

    /**** Metodos auxiliares ****/
    private Optional<SellerAccount> getAccountMeli(Integer accountId, boolean search){
        if(accountMeli == null || search == true){
            accountMeli = sellerAccountRepository.findById(accountId);
        }
        else if(!accountMeli.isPresent()){
            accountMeli = sellerAccountRepository.findById(accountId);
        }
        return accountMeli;
    }

    private Map<String, Object> updatePriceMeliOfActivePublications(Integer idProfile, List<DetailsPublicationsMeli> detailsPublicationList){
        //Update all publications in ML if these are in 'active' status
        Map<String, Object> response = new HashMap<>();
        Optional<Profile> profileDb = profileRepository.findById(idProfile);
        if(!profileDb.isPresent()){
            logger.error(String.format("Not Found, the profile with id %s not found: {}: ", idProfile));
            return (Map<String, Object>) response.put("Error", String.format("Error, the profile with id %s not found: {}: ", idProfile));
        }
        List<DetailsPublicationsMeli> listDetails = new ArrayList<>();
        List<SellerAccount> finalAccountList = profileDb.get().getSellerAccounts();
        for (DetailsPublicationsMeli detail: detailsPublicationList) {

                ChangePriceRequest changePrice = new ChangePriceRequest(detail.getPricePublication());
                Optional<SellerAccount> accountFounded = finalAccountList.stream().filter(a -> a.getId() == detail.getAccountMeli()).findFirst();
                try {
                    if(!MeliUtils.validateTokenExpiration(accountFounded.get().getExpirationDate())){
                        accountFounded = Optional.ofNullable(apiService.getTokenByRefreshToken(accountFounded.get()));
                    }

                    Object obj = apiService.updatePricePublication(changePrice, accountFounded.get().getAccessToken(), detail.getIdPublicationMeli());
                    //comprobar codigo de actualizado -- 200 OK
                    detail.setPendingMarginUpdate(false);
                    listDetails.add(detail);

                } catch (ApiException e) {
                    logger.error(" Error de Mercado Libre: {}", e.getResponseBody());
                    return (Map<String, Object>) response.put("Error", String.format("Error de Mercado Libre: {}", e.getResponseBody()));
                }catch (TokenException e) {
                    logger.error(" Error getting token Meli Response: {}", e.getMessage());
                     return (Map<String, Object>) response.put("Meli_Error", String.format("Error getting token Meli Response: {}", e.getMessage()));
                }

        }

        if(!listDetails.isEmpty()) {
            detailsPublicationRepository.saveAll(listDetails);
        }
        return (Map<String, Object>) response.put(MapResponseConstants.RESPONSE, String.format("All publications were updated"));
    }

    private Map<String, Object> setProductToNopublishedStatus(Integer idProduct, Short status){
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<MercadoLibrePublications> product = mlPublishRepository.findById(idProduct);
            if (product.isPresent()) {
                product.get().setStates(status);
                mlPublishRepository.save(product.get());
                response.put("response", "Updated");
                return response;
            }
        }catch (Exception e){
            logger.error(ActionResult.DATABASE_ERROR.getValue(), e.getStackTrace());
            response.put(ActionResult.DATABASE_ERROR.getValue(), e.getMessage());
        }
        return response;
    }
}
