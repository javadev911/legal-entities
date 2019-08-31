package com.trade.legalentities.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.trade.legalentities.enums.Country;
import com.trade.legalentities.exceptions.ApplicationException;
import com.trade.legalentities.model.LegalEntity;

@Service
public class LegalEntityService {

    private static ConcurrentHashMap<Integer, LegalEntity> legalEntityData = new ConcurrentHashMap<>();
    private final AtomicInteger autoIdCounter = new AtomicInteger();
    private static final String LE_NOT_FOUND_ERR_MSG = "Legal Entity with id: %s is not found!";
    private static final String LE_COUNTRY_NOT_VALID_ERR_MSG = "Unknown Country: %s!";

    public List<LegalEntity> getAllLegalEntities() {
        return new ArrayList<LegalEntity>(legalEntityData.values());
    }

    public LegalEntity findLegalEntityById(Integer id) throws ApplicationException {
        Optional<LegalEntity> internalLegalEntity = Optional.ofNullable(legalEntityData.get(id));
        if (!internalLegalEntity.isPresent()) {
            logAndThrowNotFoundException(id);
        }
        return legalEntityData.get(id);
    }

    public void deleteLegalEntity(Integer id) throws ApplicationException {
        Optional<LegalEntity> internalLegalEntity = Optional.ofNullable(findLegalEntityById(id));
        if (!internalLegalEntity.isPresent()) {
            logAndThrowNotFoundException(id);
        }
        legalEntityData.remove(id);
    }

    public LegalEntity updateLegalEntity(LegalEntity legalEntity, Integer id) throws ApplicationException {
        Optional<LegalEntity> internalLegalEntity = Optional.ofNullable(findLegalEntityById(id));
        if (!internalLegalEntity.isPresent()) {
            logAndThrowNotFoundException(id);
        }
        if(!StringUtils.isEmpty(legalEntity.getCountryOfIncorporation())) {
            internalLegalEntity.get().setCountryOfIncorporation(legalEntity.getCountryOfIncorporation());
        }
        if(!StringUtils.isEmpty(legalEntity.getIncorporationDate())) {
            internalLegalEntity.get().setIncorporationDate(legalEntity.getIncorporationDate());
        }
        if(legalEntity.getTotalNumberOfShares() != null) {
            internalLegalEntity.get().setTotalNumberOfShares(legalEntity.getTotalNumberOfShares());
        }
        if(!CollectionUtils.isEmpty(legalEntity.getShareHolders())) {
            internalLegalEntity.get().getShareHolders().addAll(legalEntity.getShareHolders());
        }
        legalEntityData.put(id, internalLegalEntity.get());
        return legalEntityData.get(id);        
    }

    private void logAndThrowNotFoundException(Integer id) throws ApplicationException {
        String error_message = String.format(LE_NOT_FOUND_ERR_MSG, id);
        //log.error(error_message);
        throw new ApplicationException(error_message);
    }

    public LegalEntity createLegalEntity(LegalEntity legalEntity) throws ApplicationException {
        if(!Country.isAllowed(legalEntity.getCountryOfIncorporation())) {
            throw new ApplicationException(String.format(LE_COUNTRY_NOT_VALID_ERR_MSG, legalEntity.getCountryOfIncorporation()));
        }
        legalEntity.setId(autoIdCounter.incrementAndGet());
        legalEntityData.put(legalEntity.getId(), legalEntity);
        return legalEntityData.get(legalEntity.getId());
    }

}
