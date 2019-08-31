package com.trade.legalentities.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.trade.legalentities.enums.Country;
import com.trade.legalentities.exceptions.ApplicationException;
import com.trade.legalentities.model.LegalEntity;
import com.trade.legalentities.model.ShareHolder;

@RunWith(MockitoJUnitRunner.class)
public class LegalEntityServiceTest {

    @InjectMocks
    private LegalEntityService testClass;

    @Test
    public void test_createLegalEntity_successful() {

        // GIVEN
        String name = RandomStringUtils.randomAlphabetic(10);
        Integer totalNoOfShares = new Random().nextInt();
        String country = Country.CH.toString();
        String dateOfIncorporation = "2019-01-05";
        LegalEntity expected = createLegalEntity(name, country, dateOfIncorporation, totalNoOfShares);

        // WHEN
        LegalEntity actual = null;
        try {
            actual = testClass.createLegalEntity(expected);
        } catch (ApplicationException e) {
            fail("Legal Entity creation has failed!");
        }

        // THEN
        assertThat(actual.getName(), is(expected.getName()));
        assertThat(actual.getTotalNumberOfShares(), is(expected.getTotalNumberOfShares()));
        assertThat(actual.getCountryOfIncorporation(), is(expected.getCountryOfIncorporation()));
        assertThat(actual.getIncorporationDate(), is(expected.getIncorporationDate()));
    }

    @Test
    public void test_createLegalEntity_with_shareholders_successful() {

        // GIVEN
        String name = RandomStringUtils.randomAlphabetic(10);
        Integer totalNoOfShares = new Random().nextInt();
        String country = Country.UK.toString();
        String dateOfIncorporation = "2019-01-05";
        LegalEntity expected = createLegalEntity(name, country, dateOfIncorporation, totalNoOfShares);
        List<ShareHolder> shareHolders = new ArrayList<>();
        shareHolders.add(createShareHolder(50, RandomStringUtils.randomAlphabetic(10)));
        expected.setShareHolders(shareHolders);

        // WHEN
        LegalEntity actual = null;
        try {
            actual = testClass.createLegalEntity(expected);
        } catch (ApplicationException e) {
            fail("Legal Entity creation has failed!");
        }

        // THEN
        assertThat(actual.getTotalNumberOfShares(), is(expected.getTotalNumberOfShares()));
        assertThat(actual.getCountryOfIncorporation(), is(expected.getCountryOfIncorporation()));
        assertThat(actual.getIncorporationDate(), is(expected.getIncorporationDate()));

        assertThat(actual.getShareHolders().size(), is(expected.getShareHolders().size()));

        assertThat(actual.getShareHolders().stream().findFirst().get().getNumberOfShares(),
                is(expected.getShareHolders().stream().findFirst().get().getNumberOfShares()));
        assertThat(actual.getShareHolders().stream().findFirst().get().getName(),
                is(expected.getShareHolders().stream().findFirst().get().getName()));
    }

    @Test
    public void test_getAllLegalEntities_including_shareholders() {

        // GIVEN
        String name = RandomStringUtils.randomAlphabetic(10);
        Integer totalNoOfShares = new Random().nextInt();
        String country = Country.US.toString();
        String dateOfIncorporation = "2019-01-05";
        LegalEntity expected = createLegalEntity(name, country, dateOfIncorporation, totalNoOfShares);
        List<ShareHolder> shareHolders = new ArrayList<>();
        shareHolders.add(createShareHolder(new Random().nextInt(), RandomStringUtils.randomAlphabetic(10)));
        shareHolders.add(createShareHolder(new Random().nextInt(), RandomStringUtils.randomAlphabetic(10)));
        expected.setShareHolders(shareHolders);

        try {
            testClass.createLegalEntity(expected);
        } catch (ApplicationException e) {
            fail("Legal Entity creation has failed!");
        }

        // WHEN
        int expectedEntitiesSize = 1;
        List<LegalEntity> actual = testClass.getAllLegalEntities();

        assertThat(actual.size(), is(expectedEntitiesSize));

    }

    @Test
    public void test_DeleteLegalEntities_including_shareholders() {

        // GIVEN
        String name = RandomStringUtils.randomAlphabetic(10);
        Integer totalNoOfShares = new Random().nextInt();
        String country = Country.US.toString();
        String dateOfIncorporation = "2019-01-05";
        LegalEntity expected = createLegalEntity(name, country, dateOfIncorporation, totalNoOfShares);
        List<ShareHolder> shareHolders = new ArrayList<>();
        shareHolders.add(createShareHolder(new Random().nextInt(), RandomStringUtils.randomAlphabetic(10)));
        shareHolders.add(createShareHolder(new Random().nextInt(), RandomStringUtils.randomAlphabetic(10)));
        expected.setShareHolders(shareHolders);

        LegalEntity legalEntityWithGenId = null;
        try {
            legalEntityWithGenId = testClass.createLegalEntity(expected);
        } catch (ApplicationException e) {
            fail("Legal Entity creation has failed!");
        }

        // WHEN
        try {
            testClass.deleteLegalEntity(legalEntityWithGenId.getId());
        } catch (ApplicationException e) {
            fail("Delete Legal Entity is not expected to fail!");
        }

        // THEN
        List<LegalEntity> actual = testClass.getAllLegalEntities();
        assertThat(actual.size(), is(0));

    }

    private LegalEntity createLegalEntity(String name, String country, String dateOfIncorporation, Integer noOfShares) {
        LegalEntity le = new LegalEntity();
        le.setTotalNumberOfShares(noOfShares);
        le.setCountryOfIncorporation(country);
        le.setIncorporationDate(dateOfIncorporation);
        return le;
    }

    private ShareHolder createShareHolder(Integer noOfShares, String name) {
        ShareHolder sh = new ShareHolder();
        sh.setNumberOfShares(noOfShares);
        sh.setName(name);
        return sh;
    }

}