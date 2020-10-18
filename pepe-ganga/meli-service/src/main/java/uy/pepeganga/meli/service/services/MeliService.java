package uy.pepeganga.meli.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uy.com.pepeganga.business.common.entities.MeliAccount;
import uy.com.pepeganga.business.common.entities.Profile;
import uy.pepeganga.meli.service.repository.MeliAccountRepository;
import uy.pepeganga.meli.service.repository.ProfileRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MeliService  implements IMeliService{

    @Autowired
    MeliAccountRepository meliAccountRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Override
    public MeliAccount createAccountByProfile(Integer profileId, MeliAccount meliAccount) {
        Optional<Profile> profileFound = profileRepository.findById(profileId);
        if(profileFound.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Profile with id: %s not found", profileId));
        }
        meliAccount.setProfile(profileFound.get());
        return meliAccountRepository.save(meliAccount);
    }

    @Override
    public MeliAccount updateMeliAccount(Integer accountId, MeliAccount meliAccount) {
        MeliAccount accountToUpdated = findAccountById(accountId);
        accountToUpdated.setNickname(meliAccount.getNickname());
        accountToUpdated.setBusinessDescription(meliAccount.getBusinessDescription());
        return meliAccountRepository.save(accountToUpdated);
    }

    @Override
    public void deleteMeliAccount(Integer accountId) {
        findAccountById(accountId);
        meliAccountRepository.deleteById(accountId);

    }

    @Override
    public List<MeliAccount> meliAccountsByProfileId(Integer profileId) {
        Optional<Profile> profileFound = profileRepository.findById(profileId);
        if(profileFound.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Profile with id: %s not found", profileId));
        }
        return profileFound.get().getMeliAccounts();
    }

    @Override
    public MeliAccount findAccountById(Integer accountId) {
        Optional<MeliAccount> accountFounded = meliAccountRepository.findById(accountId);
        if(accountFounded.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Account with id: %s not found", accountId));
        }
        return accountFounded.get();
    }


}
