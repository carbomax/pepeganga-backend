package uy.pepeganga.meli.service.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import meli.ApiException;
import meli.model.Attributes;
import meli.model.AttributesValues;
import meli.model.Item;
import meli.model.ItemPictures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uy.com.pepeganga.business.common.entities.SellerAccount;
import uy.com.pepeganga.business.common.entities.Profile;
import uy.pepeganga.meli.service.models.ApiMeliModelException;
import uy.pepeganga.meli.service.repository.SellerAccountRepository;
import uy.pepeganga.meli.service.repository.ProfileRepository;

import java.util.*;

@Service
public class MeliService  implements IMeliService{

    private static final Logger logger = LoggerFactory.getLogger(MeliService.class);

    @Autowired
    SellerAccountRepository sellerAccountRepository;

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

            Item item = new Item();
            item.title("Item de test - No Ofertar java");
            item.categoryId("MLU1915");
            item.price(350);
            item.currencyId("UYU");
            item.availableQuantity("12");
            item.buyingMode("buy_it_now");
            item.listingTypeId("bronze");
            item.condition("new");
            item.description("Item de Teste. Mercado Livre SDK");
            item.videoId("RXWn6kftTHY");
            item.pictures(pictures);
            item.attributes(attributes);
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


}
