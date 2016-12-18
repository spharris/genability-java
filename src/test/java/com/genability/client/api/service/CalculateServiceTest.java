package com.genability.client.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.genability.client.api.request.GetCalculatedCostRequest;
import com.genability.client.api.request.GetCalculationInputsRequest;
import com.genability.client.testing.DataLoaderUtil;
import com.genability.client.testing.TestClientModule;
import com.genability.client.types.Account;
import com.genability.client.types.CalculatedCost;
import com.genability.client.types.CalculatedCostItem;
import com.genability.client.types.DetailLevel;
import com.genability.client.types.GroupBy;
import com.genability.client.types.Profile;
import com.genability.client.types.PropertyData;
import com.genability.client.types.ReadingData;
import com.genability.client.types.Response;
import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;

@RunWith(JUnit4.class)
public class CalculateServiceTest {

  @Inject private CalculateService calculateService;
  @Inject private DataLoaderUtil dataLoader;
     
  @Before
  public void createInjector() {
    Guice.createInjector(new TestClientModule()).injectMembers(this);
  }
  
  @Test
  public void testZipCode() throws Exception {
    ZoneId tz = ZoneId.of("America/Los_Angeles");
    ZonedDateTime fromDateTime = ZonedDateTime.of(2015, 3, 1, 0, 0, 0, 0, tz);
    ZonedDateTime toDateTime = ZonedDateTime.of(2015, 4, 1, 0, 0, 0, 0, tz);

    GetCalculatedCostRequest request = GetCalculatedCostRequest.builder()
        .setFromDateTime(fromDateTime)
        .setToDateTime(toDateTime)
        .setGroupBy(GroupBy.ALL)
        .setDetailLevel(DetailLevel.TOTAL)
        .setZipCode("94105")
        .setMasterTariffId(522L)
        .setTariffInputs(PropertyData.builder()
            .setKeyName("baselineType")
            .setDataValue("typicalElectricity")
            .build())
        .build();

    Response<CalculatedCost> resp = calculateService.getCalculatedCost(request).get();
    if (resp.getStatus().equals("SUCCESS")) {
      System.out.println("Hello, world!");
    }
  }

  @Test
  public void testCalculateTariff512() throws Exception {

    ZonedDateTime fromDateTime = ZonedDateTime.parse("2011-12-01T00:00:00-05:00",
      DateTimeFormatter.ISO_DATE_TIME);
    ZonedDateTime toDateTime = ZonedDateTime.parse("2012-01-01T00:00:00-05:00",
      DateTimeFormatter.ISO_DATE_TIME);

    GetCalculatedCostRequest request = GetCalculatedCostRequest.builder()
        .setFromDateTime(fromDateTime)
        .setToDateTime(toDateTime)
        .setMasterTariffId(512l)
        .setTariffInputs(PropertyData.builder()
            .setFromDateTime(fromDateTime)
            .setToDateTime(toDateTime)
            .setDataValue("220")
            .setKeyName("consumption")
            .build(),
          PropertyData.builder()
            .setFromDateTime(fromDateTime)
            .setToDateTime(toDateTime)
            .setDataValue("Inside")
            .setKeyName("cityLimits ")
            .build(),
          PropertyData.builder()
            .setFromDateTime(fromDateTime)
            .setToDateTime(toDateTime)
            .setDataValue("Primary")
            .setKeyName("connectionType")
            .build())
        .build();
    callRunCalc("Test for master tariff 512", request);
  }



  @Test
  public void testCalculateTariff522() throws Exception {

    // Where the tariff has a time zone (most do) you can use it to make sure your dates are the
    // same
    ZonedDateTime fromDateTime = ZonedDateTime.of(2012, 1, 1, 0, 0, 0, 0, ZoneId.of("US/Pacific"));
    ZonedDateTime toDateTime = ZonedDateTime.of(2013, 1, 1, 0, 0, 0, 0, ZoneId.of("US/Pacific"));

    GetCalculatedCostRequest.Builder request = GetCalculatedCostRequest.builder()
        .setFromDateTime(fromDateTime)
        .setToDateTime(toDateTime)
        .setMasterTariffId(522l) // PGE E1 - residential tariff
        .setDetailLevel(DetailLevel.CHARGE_TYPE)
        .setGroupBy(GroupBy.MONTH);

    // Set the territoryId property
    PropertyData newProp2 = PropertyData.builder()
        .setFromDateTime(fromDateTime)
        .setToDateTime(toDateTime)
        .setDataValue("3534") // Baseline Region P - 3534
        .setKeyName("territoryId")
        .build();

    ImmutableList.Builder<PropertyData> builder = ImmutableList.<PropertyData>builder()
        .add(newProp2);

    //
    // Create consumption inputs for each hour of the day, first for
    // weekdays then for weekends.
    //
    ZonedDateTime propertyStartDateTime = fromDateTime;
    while (propertyStartDateTime.isBefore(toDateTime)) {

      for (int hour = 0; hour < 24; hour++) {

        // Set the consumption property
        PropertyData weekdayProp = PropertyData.builder()
            .setFromDateTime(propertyStartDateTime)
            .setToDateTime(propertyStartDateTime.plusMonths(1))
            .setPeriod("1:5e " + hour + "H")
            .setDataValue("0.5")
            .setKeyName("consumption")
            .build();

        builder.add(weekdayProp);

        PropertyData weekendProp = PropertyData.builder()
            .setFromDateTime(fromDateTime)
            .setToDateTime(toDateTime)
            .setPeriod("6:7e " + hour + "H")
            .setDataValue("0.5")
            .setKeyName("consumption")
            .build();

        builder.add(weekendProp);

        propertyStartDateTime = propertyStartDateTime.plusMonths(1);

      }
    }

    callRunCalc("Test for master tariff 522", request.setTariffInputs(builder.build()).build());

  }

  @Test
  public void testCalculateTariff522GroupBy() throws Exception {

    ZonedDateTime fromDateTime =
        ZonedDateTime.of(2014, 1, 1, 0, 0, 0, 0, ZoneId.of("US/Pacific"));
    ZonedDateTime toDateTime =
        ZonedDateTime.of(2014, 2, 1, 0, 0, 0, 0, ZoneId.of("US/Pacific"));

    GetCalculatedCostRequest request = GetCalculatedCostRequest.builder()
        .setFromDateTime(fromDateTime)
        .setToDateTime(toDateTime)
        .setMasterTariffId(522l)
        .setDetailLevel(DetailLevel.CHARGE_TYPE)
        .setGroupBy(GroupBy.MONTH)
        .setTariffInputs(PropertyData.builder()
            .setFromDateTime(fromDateTime)
            .setToDateTime(toDateTime)
            .setDataValue("220")
            .setKeyName("consumption")
            .build())
        .build();

    CalculatedCost calculatedCost = callRunCalc("Test for grouping master tariff 522", request);

    // Assert groupBy dates
    for (CalculatedCostItem costItem : calculatedCost.getItems()) {

      assertEquals(costItem.getFromDateTime().toInstant(),
        fromDateTime.toInstant());
      assertEquals(costItem.getToDateTime().toInstant(), toDateTime.toInstant());

    }

  }

  @Test
  public void testCalculateForAccount() throws Exception {

    Account newAccount = dataLoader.createAccount();
    // Now we run the calculation for the new Account. We set the date
    // range for which to run the calc.

    // Where the tariff has a time zone (most do) you can use it to make sure your dates are the
    // same
    ZonedDateTime fromDateTime = ZonedDateTime.of(2012, 1, 1, 1, 0, 0, 0, ZoneId.of("US/Pacific"));
    ZonedDateTime toDateTime = ZonedDateTime.of(2013, 1, 1, 1, 0, 0, 0, ZoneId.of("US/Pacific"));

    GetCalculatedCostRequest request = GetCalculatedCostRequest.builder()
        .setFromDateTime(fromDateTime)
        .setToDateTime(toDateTime)
        .setDetailLevel(DetailLevel.ALL)
        .setAccountId(newAccount.getAccountId())
        .setTariffInputs(PropertyData.builder()
            .setFromDateTime(fromDateTime)
            .setToDateTime(toDateTime)
            .setDataValue(newAccount.getAccountId())
            .setKeyName("accountId")
            .build())
        .build();

    callRunCalc("Test for calculateForAccount", request);

    dataLoader.cleanup(newAccount.getAccountId());

  }


  @Test
  public void testCalculateMultipleProfiles() throws Exception {

    // create profile with readings
    List<ReadingData> readings = new ArrayList<ReadingData>();
    // add two months of readings
    ZonedDateTime fromDateTime1 = ZonedDateTime.of(2014, 3, 1, 1, 0, 0, 0, ZoneId.of("US/Pacific"));
    ZonedDateTime toDateTime1 = ZonedDateTime.of(2014, 4, 1, 1, 0, 0, 0, ZoneId.of("US/Pacific"));
    ReadingData readingData1 = ReadingData.builder()
        .setQuantityUnit("kWh")
        .setFromDateTime(fromDateTime1)
        .setToDateTime(toDateTime1)
        .setQuantityValue(new BigDecimal("1000"))
        .build();
    readings.add(readingData1);

    // create profile 1
    ZonedDateTime fromDateTime2 = ZonedDateTime.of(2014, 3, 1, 1, 0, 0, 0, ZoneId.of("US/Pacific"));
    ZonedDateTime toDateTime2 = ZonedDateTime.of(2014, 4, 1, 1, 0, 0, 0, ZoneId.of("US/Pacific"));
    Profile profile1 = dataLoader.createProfileWithReadings(readings);
    List<ReadingData> readings2 = new ArrayList<ReadingData>();
    ReadingData readingData2 = ReadingData.builder()
        .setQuantityUnit("kWh")
        .setFromDateTime(fromDateTime2)
        .setToDateTime(toDateTime2)
        .setQuantityValue(new BigDecimal("900"))
        .build();
    readings2.add(readingData2);

    // create profile 2
    Profile profile2 = dataLoader.createProfileWithReadings(readings2);
    ZonedDateTime fromDateTime = ZonedDateTime.of(2014, 3, 1, 1, 0, 0, 0, ZoneId.of("US/Pacific"));
    ZonedDateTime toDateTime = ZonedDateTime.of(2014, 4, 1, 1, 0, 0, 0, ZoneId.of("US/Pacific"));

    // set up calculation request
    GetCalculatedCostRequest request = GetCalculatedCostRequest.builder()
        .setFromDateTime(fromDateTime)
        .setToDateTime(toDateTime)
        .setDetailLevel(DetailLevel.ALL)
        .setMasterTariffId(512l)
        .setTariffInputs(PropertyData.builder()
            .setFromDateTime(fromDateTime)
            .setToDateTime(toDateTime)
            .setDataValue(profile1.getProfileId())
            .setKeyName("profileId")
            .build(),
          PropertyData.builder()
            .setFromDateTime(fromDateTime)
            .setToDateTime(toDateTime)
            .setDataValue(profile2.getProfileId())
            .setKeyName("profileId")
            .build())
        .build();

    // run calc
    callRunCalc("Test for calculateForAccount", request);

    //
    // Add tests
    //

    // clean up data
    dataLoader.cleanup(profile1.getAccountId());
    dataLoader.cleanup(profile2.getAccountId());

  }

  @Test
  public void testGetCalculationInputs() throws Exception {

    // Where the tariff has a time zone (most do) you can use it to make sure your dates are the
    // same
    ZonedDateTime fromDateTime = ZonedDateTime.of(2012, 1, 1, 0, 0, 0, 0, ZoneId.of("US/Pacific"));
    ZonedDateTime toDateTime = ZonedDateTime.of(2013, 1, 1, 0, 0, 0, 0, ZoneId.of("US/Pacific"));

    GetCalculationInputsRequest request = GetCalculationInputsRequest.builder()
        .setFromDateTime(fromDateTime)
        .setToDateTime(toDateTime)
        .setMasterTariffId(522L)
        .setTerritoryId(3534L)
        .build();

    Response<PropertyData> restResponse = calculateService.getCalculationInputs(request).get();

    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("bad count", restResponse.getCount() > 0);
    assertNotNull("results null", restResponse.getResults());
    assertTrue("results were empty", restResponse.getResults().size() != 0);
  }


  public CalculatedCost callRunCalc(
      String testCase, GetCalculatedCostRequest request) throws Exception {

    Response<CalculatedCost> restResponse = calculateService.getCalculatedCost(request).get();

    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("bad count", restResponse.getCount() > 0);
    assertNotNull("results null", restResponse.getResults());
    assertEquals("results count", restResponse.getResults().size(), 1);

    CalculatedCost calculatedCost = restResponse.getResults().get(0);

    assertNotNull("calculatedCost total", calculatedCost.getTotalCost());

    for (CalculatedCostItem costItem : calculatedCost.getItems()) {

      assertNotNull("cost null", costItem.getCost());

    }

    return calculatedCost;
  }
}
