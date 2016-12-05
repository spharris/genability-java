package com.genability.client.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.fasterxml.jackson.core.type.TypeReference;
import com.genability.client.api.request.AccountAnalysisRequest;
import com.genability.client.api.request.DeleteAccountRequest;
import com.genability.client.testing.TestClientModule;
import com.genability.client.types.Account;
import com.genability.client.types.AccountAnalysis;
import com.genability.client.types.Address;
import com.genability.client.types.CalculatedCost;
import com.genability.client.types.CalculatedCostItem;
import com.genability.client.types.ChargeType;
import com.genability.client.types.Profile;
import com.genability.client.types.PropertyData;
import com.genability.client.types.ReadingData;
import com.genability.client.types.Response;
import com.genability.client.types.Series;
import com.genability.client.types.SeriesMeasure;
import com.genability.client.types.TariffRate;
import com.genability.client.types.TariffRateBand;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Guice;

/**
 * Created by nsingh on 11/20/14.
 */
@RunWith(JUnit4.class)
public class AccountAnalysisServiceTests extends BaseServiceTests {

  private AccountAnalysis testAnalysisWithCosts;
  private AccountAnalysis testAnalysisNoCosts;
  private static final TypeReference<Response<AccountAnalysis>> ACCOUNT_ANALYSIS_RESPONSE_TYPEREF =
      new TypeReference<Response<AccountAnalysis>>() {};

  @Inject private AccountService accountService;
     
  @Before
  public void createInjector() {
    Guice.createInjector(new TestClientModule()).injectMembers(this);
  }
      
  @Before
  public void setUp() {
    Response<AccountAnalysis> responseWithCosts =
        loadJsonFixture("account_analysis_withcosts.json", ACCOUNT_ANALYSIS_RESPONSE_TYPEREF);

    Response<AccountAnalysis> responseNoCosts =
        loadJsonFixture("account_analysis_nocosts.json", ACCOUNT_ANALYSIS_RESPONSE_TYPEREF);

    testAnalysisWithCosts = responseWithCosts.getResults().get(0);
    testAnalysisNoCosts = responseNoCosts.getResults().get(0);
  }

  @Test
  public void testSavingsAnalysis() throws Exception {

    Account account = Account.builder()
        .setAccountName("test-api")
        .setProviderAccountId("test-api" + UUID.randomUUID())
        .setAddress(Address.builder()
          .setAddressString("221 Main Street, San Francisco, CA 94105")
          .build())
        .setProperties(ImmutableMap.<String, PropertyData>builder()
          .put(
            "customerClass",
            PropertyData.builder().setKeyName("customerClass").setDataValue("1").build())
          .build())
        .build();

    Response<Account> restResponse = accountService.addAccount(account).get();
    assertNotNull("new account response is null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("bad count", restResponse.getCount() == 1);

    Account newAccount = restResponse.getResults().get(0);
    String accountId = newAccount.getAccountId();
    assertNotNull("accountId null", accountId);


    DateTime baseFromDateTime = new DateTime("2013-01-01");

    Profile.Builder usageProfile = Profile.builder()
        .setAccountId(newAccount.getAccountId());
    
    ImmutableList.Builder<ReadingData> readingDataList = ImmutableList.builder();
    for (int i = 0; i < 8760; i++) {
      ReadingData readingData = ReadingData.builder()
          .setFromDateTime(baseFromDateTime.plusHours(i))
          .setToDateTime(baseFromDateTime.plusHours(i + 1))
          .setQuantityUnit("kWh")
          .setQuantityValue(BigDecimal.valueOf(250))
          .build();
      readingDataList.add(readingData);
    }
    usageProfile.setReadingData(readingDataList.build());
    usageProfile.setProviderProfileId("USAGE_RESIDENTIAL_CA_V5" + UUID.randomUUID());
    profileService.addProfile(usageProfile.build());

    Profile.Builder productionProfile = Profile.builder()
        .setAccountId(newAccount.getAccountId());
    readingDataList = ImmutableList.builder();
    for (int i = 0; i < 8760; i++) {
      ReadingData readingData = ReadingData.builder()
          .setFromDateTime(baseFromDateTime.plusHours(i))
          .setToDateTime(baseFromDateTime.plusHours(i + 1))
          .setQuantityUnit("kWh")
          .setQuantityValue(BigDecimal.valueOf(200))
          .build();
      readingDataList.add(readingData);
    }
    usageProfile.setReadingData(readingDataList.build());
    productionProfile.setProviderProfileId("PRODUCTION_RESIDENTIAL_CA_V5" + UUID.randomUUID());
    profileService.addProfile(productionProfile.build());

    AccountAnalysisRequest request = createSavingsAnalysis(usageProfile.build(),
      productionProfile.build()).toBuilder()
        .setProviderAccountId(newAccount.getProviderAccountId())
        .build();

    try {
      Response<AccountAnalysis> aaResponse =
          accountAnalysisService.calculateSavingsAnalysis(request);

      assertNotNull("restResponse null", aaResponse);
      assertEquals("bad status", Response.STATUS_SUCCESS, aaResponse.getStatus());
      assertNotNull("results null", aaResponse.getResults());
      assertEquals("results should be length one", 1, aaResponse.getResults().size());

      AccountAnalysis result = aaResponse.getResults().get(0);
      Map<String, BigDecimal> summary = result.getSummary();

      assertNotNull("summary null", summary);

      BigDecimal netAvoidedCost = summary.get("netAvoidedCost");
      assertFalse("netAvoidedCost should not be zero",
          BigDecimal.ZERO.compareTo(netAvoidedCost) == 0);

      BigDecimal preTotalCost = summary.get("preTotalCost");
      assertFalse("preTotalCost should not be zero", BigDecimal.ZERO.compareTo(preTotalCost) == 0);

      BigDecimal postTotalCost = summary.get("postTotalCost");
      assertFalse("postTotalCost should not be zero",
          BigDecimal.ZERO.compareTo(postTotalCost) == 0);

      Series monthlyPreSolarUtilitySeries = result.getSeriesByParameters("before", "MONTH", null);
      assertNotNull("no pre-solar monthly series found", monthlyPreSolarUtilitySeries);

      Integer seriesId = monthlyPreSolarUtilitySeries.getSeriesId();
      List<SeriesMeasure> monthlyPreSolarUtilitySeriesData =
          result.getSeriesDataBySeriesId(seriesId);
      assertNotNull("no pre-solar monthly seriesData (seriesId " + seriesId + ")",
          monthlyPreSolarUtilitySeriesData);

      // Because it starts at 2014-10-10 (and therefore ends at 2015-10-10), we should see 13 months
      // of data:
      // Oct 2014 through Nov 2015
      assertEquals("for a mid-month fromDateTime, didn't get 13 buckets", 13,
          monthlyPreSolarUtilitySeriesData.size());
    } finally {
      // delete account so we keep things clean
      deleteAccount(accountId);
    }
  }

  @Test
  public void testPopulateCosts() throws Exception {
    Account account = Account.builder()
        .setAccountName("test-api")
        .setProviderAccountId("test-api" + UUID.randomUUID())
        .setAddress(Address.builder()
          .setAddressString("221 Main Street, San Francisco, CA 94105")
          .build())
        .setProperties(ImmutableMap.<String, PropertyData>builder()
          .put(
            "customerClass",
            PropertyData.builder().setKeyName("customerClass").setDataValue("1").build())
          .build())
        .build();
    Response<Account> restResponse = accountService.addAccount(account).get();
    assertNotNull("new account response is null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("bad count", restResponse.getCount() == 1);

    Account newAccount = restResponse.getResults().get(0);
    String accountId = newAccount.getAccountId();
    assertNotNull("accountId null", accountId);

    try {
      Profile.Builder usageProfile = Profile.builder()
          .setAccountId(newAccount.getAccountId());

      DateTime baseFromDateTime = new DateTime("2013-01-01");
      ImmutableList.Builder<ReadingData> readingDataList = ImmutableList.builder();
      for (int i = 0; i < 8760; i++) {
        ReadingData readingData = ReadingData.builder()
            .setFromDateTime(baseFromDateTime.plusHours(i))
            .setToDateTime(baseFromDateTime.plusHours(i + 1))
            .setQuantityUnit("kWh")
            .setQuantityValue(BigDecimal.valueOf(250))
            .build();
        readingDataList.add(readingData);
      }
      usageProfile.setReadingData(readingDataList.build());
      usageProfile.setProviderProfileId("USAGE_RESIDENTIAL_CA_V5" + UUID.randomUUID());
      profileService.addProfile(usageProfile.build());

      Profile.Builder productionProfile = Profile.builder()
          .setAccountId(newAccount.getAccountId());
      readingDataList = ImmutableList.builder();
      for (int i = 0; i < 8760; i++) {
        ReadingData readingData = ReadingData.builder()
            .setFromDateTime(baseFromDateTime.plusHours(i))
            .setToDateTime(baseFromDateTime.plusHours(i + 1))
            .setQuantityUnit("kWh")
            .setQuantityValue(BigDecimal.valueOf(200))
            .build();
        readingDataList.add(readingData);
      }
      usageProfile.setReadingData(readingDataList.build());
      productionProfile.setProviderProfileId("PRODUCTION_RESIDENTIAL_CA_V5" + UUID.randomUUID());
      profileService.addProfile(productionProfile.build());

      AccountAnalysisRequest request = createSavingsAnalysis(usageProfile.build(),
        productionProfile.build()).toBuilder()
          .setProviderAccountId(newAccount.getProviderAccountId())
          .setPopulateCosts(true)
          .build();

      Response<AccountAnalysis> aaResponse =
          accountAnalysisService.calculateSavingsAnalysis(request);

      assertNotNull("restResponse null", aaResponse);
      assertEquals("bad status", Response.STATUS_SUCCESS, aaResponse.getStatus());
      assertNotNull("results null", aaResponse.getResults());
      assertEquals("results should be length one", 1, aaResponse.getResults().size());

      AccountAnalysis result = aaResponse.getResults().get(0);
      Map<String, BigDecimal> summary = result.getSummary();

      assertNotNull("summary null", summary);

      BigDecimal netAvoidedCost = summary.get("netAvoidedCost");
      assertFalse("netAvoidedCost should not be zero",
          BigDecimal.ZERO.compareTo(netAvoidedCost) == 0);

      BigDecimal preTotalCost = summary.get("preTotalCost");
      assertFalse("preTotalCost should not be zero", BigDecimal.ZERO.compareTo(preTotalCost) == 0);

      BigDecimal postTotalCost = summary.get("postTotalCost");
      assertFalse("postTotalCost should not be zero",
          BigDecimal.ZERO.compareTo(postTotalCost) == 0);

      Series monthlyPreSolarUtilitySeries = result.getSeriesByParameters("before", "MONTH", null);
      assertNotNull("no pre-solar monthly series found", monthlyPreSolarUtilitySeries);

      Integer seriesId = monthlyPreSolarUtilitySeries.getSeriesId();
      List<SeriesMeasure> monthlyPreSolarUtilitySeriesData =
          result.getSeriesDataBySeriesId(seriesId);
      assertNotNull("no pre-solar monthly seriesData (seriesId " + seriesId + ")",
          monthlyPreSolarUtilitySeriesData);

      // populateCosts tests
      Map<Integer, CalculatedCost> seriesCosts = result.getSeriesCosts();
      assertNotNull("SeriesCosts was null", seriesCosts);
      assertEquals("Did not have the correct number of seriesCosts", 2, seriesCosts.size());

      boolean foundTierLimit = false;
      CalculatedCost firstScenCosts = seriesCosts.get(1);

      for (CalculatedCostItem c : firstScenCosts.getItems()) {
        if (c.getTierUpperLimit() != null || c.getTierLowerLimit() != null) {
          foundTierLimit = true;
        }
      }

      assertTrue("Didn't find a tierUpperLimit or tierLowerLimit", foundTierLimit);

      // Because it starts at 2014-10-10 (and therefore ends at 2015-10-10), we should see 13 months
      // of data:
      // Oct 2014 through Nov 2015
      assertEquals("for a mid-month fromDateTime, didn't get 13 buckets", 13,
          monthlyPreSolarUtilitySeriesData.size());
    } finally {
      // delete account so we keep things clean
      deleteAccount(accountId);
    }
  }

  @Test
  public void testGetValidSeriesByParameters() {
    Series series = testAnalysisWithCosts.getSeriesByParameters("before", "MONTH", null);

    assertEquals("Got the wrong series scenario", "before", series.getScenario());
    assertEquals("Got the wrong series period", "MONTH", series.getSeriesPeriod());
  }

  @Test
  public void testGetSeriesByInvalidParameters() {
    Series series = testAnalysisWithCosts.getSeriesByParameters("before", "100", null);

    assertNull(series);
  }

  @Test
  public void testGetSeriesByOneNullParameter() {
    Series series = testAnalysisWithCosts.getSeriesByParameters("before", null, null);

    assertNull(series);
  }

  @Test
  public void testGetSeriesByTwoNullParameters() {
    Series series = testAnalysisWithCosts.getSeriesByParameters(null, null, null);

    assertNull(series);
  }

  @Test
  public void testGetSeriesDataByGoodId() {
    Integer seriesId = new Integer(1);
    List<SeriesMeasure> measures = testAnalysisWithCosts.getSeriesDataBySeriesId(seriesId);

    for (SeriesMeasure measure : measures) {
      assertEquals("Got data for the wrong series", seriesId, measure.getSeriesId());
    }
  }

  @Test
  public void testGetSeriesDataByBadId() {
    Integer seriesId = new Integer(150);
    List<SeriesMeasure> measures = testAnalysisWithCosts.getSeriesDataBySeriesId(seriesId);

    assertEquals("Got measures for an invalid seriesId", 0, measures.size());
  }

  @Test
  public void testGetCostsByGoodSeriesName() {
    Long beforeMtid = new Long(525);
    Long afterMtid = new Long(522);
    CalculatedCost before =
        testAnalysisWithCosts.getSeriesCostsByParameters("before", "MONTH", null);
    CalculatedCost after = testAnalysisWithCosts.getSeriesCostsByParameters("after", "MONTH", null);

    assertEquals("Got the wrong scenario for before", beforeMtid, before.getMasterTariffId());
    assertEquals("Got the wrong scenario for after", afterMtid, after.getMasterTariffId());
  }

  @Test
  public void testGetCostsByNonExistentSeriesName() {
    CalculatedCost costs =
        testAnalysisWithCosts.getSeriesCostsByParameters("invalid", "MONTH", null);

    assertNull(costs);
  }

  @Test
  public void testGetCostsForBeforeAnnual() {
    CalculatedCost costs = testAnalysisWithCosts.getSeriesCostsByParameters("before", "YEAR", null);

    assertNull(costs);
  }

  @Test
  public void testGetCostsByNameWithNoCosts() {
    CalculatedCost costs = testAnalysisWithCosts.getSeriesCostsByParameters("solar", "MONTH", null);

    assertNull(costs);
  }

  @Test
  public void testGetCostsWhenNoCosts() {
    CalculatedCost before = testAnalysisNoCosts.getSeriesCostsByParameters("before", "MONTH", null);

    assertNull(before);
  }

  private AccountAnalysisRequest createSavingsAnalysis(Profile usageProfile,
      Profile productionProfile) {
    AccountAnalysisRequest request = AccountAnalysisRequest.builder()
        .setFromDateTime(new DateTime("2014-10-10"))
        .setPropertyInputs(
          PropertyData.builder()
              .setScenarios("before")
              .setKeyName("masterTariffId")
              .setDataValue("522")
              .build(),
          PropertyData.builder()
              .setScenarios("after")
              .setKeyName("masterTariffId")
              .setDataValue("522")
              .build(),
          PropertyData.builder()
              .setScenarios("before,after")
              .setKeyName("rateInflation")
              .setDataValue("3.5")
              .build(),
          PropertyData.builder()
              .setScenarios("solar")
              .setKeyName("rateInflation")
              .setDataValue("1.9")
              .build(),
          PropertyData.builder()
              .setScenarios("after,solar")
              .setKeyName("solarDegradation")
              .setDataValue("1.5")
              .build(),
          PropertyData.builder()
              .setScenarios("before")
              .setKeyName("providerProfileId")
              .setDataValue(usageProfile.getProviderProfileId())
              .build(),
          PropertyData.builder()
              .setScenarios("after,solar")
              .setKeyName("providerProfileId")
              .setDataValue(productionProfile.getProviderProfileId())
              .build())
        .setRateInputs(TariffRate.builder()
          .setChargeType(ChargeType.FIXED_PRICE)
          .setScenarios("solar")
          .setRateBands(TariffRateBand.builder()
            .setRateAmount(BigDecimal.valueOf(137.05))
            .build())
          .build())
        .build();

    return request;
  }

  private void deleteAccount(String accountId) throws Exception {

    // delete account so we keep things clean
    DeleteAccountRequest request = DeleteAccountRequest.builder()
        .setHardDelete(Boolean.TRUE)
        .setAccountId(accountId)
        .build();
    Response<Account> deleteResponse = accountService.deleteAccount(request).get();

    assertNotNull("restResponse null", deleteResponse);
    assertEquals("bad status", deleteResponse.getStatus(), Response.STATUS_SUCCESS);
  }

}
