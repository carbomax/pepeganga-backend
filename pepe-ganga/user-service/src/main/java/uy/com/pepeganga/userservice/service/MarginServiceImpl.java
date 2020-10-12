package uy.com.pepeganga.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uy.com.pepeganga.business.common.entities.Margin;
import uy.com.pepeganga.business.common.entities.Marketplace;
import uy.com.pepeganga.business.common.entities.Profile;
import uy.com.pepeganga.business.common.utils.enums.MarginType;
import uy.com.pepeganga.business.common.utils.enums.MarketplaceType;
import uy.com.pepeganga.userservice.repository.MarginRepository;
import uy.com.pepeganga.userservice.repository.ProfileRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class MarginServiceImpl implements MarginService {

	@Autowired
	private MarginRepository marginRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public List<Margin> getMargins(Integer profileId) {
		Optional<Profile> profile = profileRepository.findById(profileId);
		if (profile.isPresent()) {
			if (profile.get().getMargins() != null) {
				return profile.get().getMargins().stream().map(margin -> {
					 MarketplaceType marginType = MarketplaceType.of(margin.getMarketplaceId());
					
					margin.setMarketplace(new Marketplace(marginType.getId(), marginType.getValue()));

					return margin;
				}).collect(Collectors.toList());
			}
		} else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("Profile not valid with id %d", profileId));
		return Collections.emptyList();
	}

	@Override
	public Margin createMargin(Margin margin, Integer profileId) {
		try {
			MarginType.of(margin.getType());
			Margin marginSaved = marginRepository.save(margin);
			Optional<Profile> profile = profileRepository.findById(profileId);
			if (profile.isPresent()) {
				Profile profileToUpdate = profile.get();
				List<Margin> marginsToUpdate = profileToUpdate.getMargins();
				marginsToUpdate.add(marginSaved);
				profileToUpdate.setMargins(marginsToUpdate);
				profileRepository.save(profileToUpdate);
				Profile pro = new Profile();
				pro.setId(profileId);
				Optional<Margin> marginCreated = marginRepository.findById(marginSaved.getId());
				if (marginCreated.isPresent()) {
					return marginCreated.get();
				} else
					throw new ResponseStatusException(HttpStatus.NOT_MODIFIED,
							String.format("Margin not created, profile id not updated with id: %d", profileId));

			} else
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
						String.format("Margin not created, profile id not updated with id: %d", profileId));

		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					String.format("Type of margin %s it not permitted. Try [%d, %d]", margin.getType(),
							MarginType.SIMPLE.getCode(), MarginType.PERCENT.getCode()));
		}
	}

	@Override
	public Margin updateMargin(Margin margin, Integer idProfile, Short idMargin) {

		Optional<Profile> profileDb = profileRepository.findById(idProfile);
		AtomicReference<Profile> profileFinal = new AtomicReference<>(new Profile());
		profileDb.ifPresent(profile -> {
			Optional<Margin> marginDb = marginRepository.findById(idMargin);
			if (marginDb.isPresent()) {
				try {
					List<Margin> marginsToUpdate = profile.getMargins().stream().map(mag -> {
						if (mag.getId().equals(idMargin)) {
							Margin margin1 = marginDb.get();
							margin1.setType(margin.getType());
							margin1.setName(margin.getName());
							margin1.setValue(margin.getValue());
							return margin1;
						} else
							return mag;
					}).collect(Collectors.toList());
					profile.setMargins(marginsToUpdate);
					profileFinal.set(profileRepository.save(profile));

				} catch (IllegalArgumentException e) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
							String.format("Type of margin %s it not permittedTry [%d, %d]", margin.getType(),
									MarginType.SIMPLE.getCode(), MarginType.PERCENT.getCode()));
				}
			} else
				throw new ResponseStatusException(HttpStatus.NOT_FOUND,
						String.format("Margin not found with id %d", idMargin));
		});
		if (profileDb.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("Margin not created, profile id not founded with id: %d", idProfile));
		}
		Optional<Margin> first = profileFinal.get().getMargins().stream()
				.filter(margin1 -> margin1.getId().equals(idMargin)).findFirst();
		if (first.isPresent()) {
			return first.get();
		} else
			throw new ResponseStatusException(HttpStatus.NOT_MODIFIED,
					String.format("Margin not updated with id %d", idMargin));

	}

	@Override
	public void deleteMargin(Short idMargin) {
		if (marginRepository.existsById(idMargin)) {
			marginRepository.deleteById(idMargin);
		} else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					String.format("Margin not deleted with id %s, it not exist", idMargin));
	}
}
