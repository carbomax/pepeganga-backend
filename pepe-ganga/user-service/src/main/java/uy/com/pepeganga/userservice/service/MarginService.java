package uy.com.pepeganga.userservice.service;

import uy.com.pepeganga.business.common.entities.Margin;

import java.util.List;

public interface MarginService {

    List<Margin> getMargins(Integer profileId);

    Margin createMargin(Margin margin, Integer profileId);

    Margin updateMargin(Margin margin, Integer idProfile, Short id);

    void deleteMargin(Short id);
}
