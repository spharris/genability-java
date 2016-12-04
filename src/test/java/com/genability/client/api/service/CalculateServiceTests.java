package com.genability.client.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import com.genability.client.api.request.GetCalculatedCostRequest;
import com.genability.client.api.request.GetCalculationInputsRequest;
import com.genability.client.types.Account;
import com.genability.client.types.CalculatedCost;
import com.genability.client.types.CalculatedCostItem;
import com.genability.client.types.DetailLevel;
import com.genability.client.types.GroupBy;
import com.genability.client.types.Profile;
import com.genability.client.types.PropertyData;
import com.genability.client.types.ReadingData;
import com.genability.client.types.Response;

public class CalculateServiceTests extends BaseServiceTests {

  private static CalculateService calculateService = genabilityClient.getCalculateService();

  @Test
  public void testZipCode() {
    DateTimeZone tz = DateTimeZone.forID("America/Los_Angeles");
    DateTime fromDateTime = new DateTime(2015, 3, 1, 0, 0, 0, tz);
    DateTime toDateTime = new DateTime(2015, 4, 1, 0, 0, 0, tz);

    GetCalculatedCostRequest request = new GetCalculatedCostRequest();
    request.setFromDateTime(fromDateTime);
    request.setToDateTime(toDateTime);
    request.setGroupBy(GroupBy.ALL);
    request.setDetailLevel(DetailLevel.TOTAL);
    request.setZipCode("94105");
    request.setMasterTariffId(522L);

    PropertyData ti = PropertyData.builder()
        .setKeyName("baselineType")
        .setDataValue("typicalElectricity")
        .build();
    List<PropertyData> tariffInputs = new LinkedList<PropertyData>();
    tariffInputs.add(ti);
    request.setTariffInputs(tariffInputs);

    Response<CalculatedCost> resp = calculateService.getCalculatedCost(request);
    if (resp.getStatus().equals("SUCCESS")) {
      System.out.println("Hello, world!");
    }
  }

  @Test
  public void testCalculateTariff512() {

    DateTime fromDateTime = new DateTime("2011-12-01T00:00:00.000-05:00");
    DateTime toDateTime = new DateTime("2012-01-01T00:00:00.000-05:00");

    GetCalculatedCostRequest request = new GetCalculatedCostRequest();

    request.setFromDateTime(fromDateTime);
    request.setToDateTime(toDateTime);
    request.setMasterTariffId(512l);

    // Set the consumption property
    PropertyData newProp3 = PropertyData.builder()
        .setFromDateTime(fromDateTime)
        .setToDateTime(toDateTime)
        .setDataValue("220")
        .setKeyName("consumption")
        .build();

    // Set the cityLimits property
    PropertyData newProp = PropertyData.builder()
        .setFromDateTime(fromDateTime)
        .setToDateTime(toDateTime)
        .setDataValue("Inside")
        .setKeyName("cityLimits ")
        .build();

    // Set the connectionType property
    PropertyData newProp2 = PropertyData.builder()
        .setFromDateTime(fromDateTime)
        .setToDateTime(toDateTime)
        .setDataValue("Primary")
        .setKeyName("connectionType")
        .build();

    request.addTariffInput(newProp);
    request.addTariffInput(newProp2);
    request.addTariffInput(newProp3);

    callRunCalc("Test for master tariff 512", request);

  }



  @Test
  public void testCalculateTariff522() {

    // Where the tariff has a time zone (most do) you can use it to make sure your dates are the
    // same
    DateTime fromDateTime = new DateTime(2012, 1, 1, 0, 0, 0, 0, DateTimeZone.forID("US/Pacific"));
    DateTime toDateTime = new DateTime(2013, 1, 1, 0, 0, 0, 0, DateTimeZone.forID("US/Pacific"));

    GetCalculatedCostRequest request = new GetCalculatedCostRequest();

    request.setFromDateTime(fromDateTime);
    request.setToDateTime(toDateTime);
    request.setMasterTariffId(522l); // PGE E1 - residential tariff
    request.setDetailLevel(DetailLevel.CHARGE_TYPE);
    request.setGroupBy(GroupBy.MONTH);

    // Set the territoryId property
    PropertyData newProp2 = PropertyData.builder()
        .setFromDateTime(fromDateTime)
        .setToDateTime(toDateTime)
        .setDataValue("3534") // Baseline Region P - 3534
        .setKeyName("territoryId")
        .build();

    request.addTariffInput(newProp2);

    //
    // Create consumption inputs for each hour of the day, first for
    // weekdays then for weekends.
    //
    DateTime propertyStartDateTime = new DateTime(fromDateTime);
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

        request.addTariffInput(weekdayProp);

        PropertyData weekendProp = PropertyData.builder()
            .setFromDateTime(fromDateTime)
            .setToDateTime(toDateTime)
            .setPeriod("6:7e " + hour + "H")
            .setDataValue("0.5")
            .setKeyName("consumption")
            .build();

        request.addTariffInput(weekendProp);

        propertyStartDateTime = propertyStartDateTime.plusMonths(1);

      }
    }

    callRunCalc("Test for master tariff 522", request);

  }

  @Test
  public void testCalculateTariff522GroupBy() {

    DateTime fromDateTime =
        new DateTime(2014, 1, 1, 0, 0, 0, 0, DateTimeZone.forID("America/Los_Angeles"));
    DateTime toDateTime =
        new DateTime(2014, 2, 1, 0, 0, 0, 0, DateTimeZone.forID("America/Los_Angeles"));

    GetCalculatedCostRequest request = new GetCalculatedCostRequest();

    // Group by chargeType
    request.setFromDateTime(fromDateTime);
    request.setToDateTime(toDateTime);
    request.setMasterTariffId(522l);
    request.setDetailLevel(DetailLevel.CHARGE_TYPE);
    request.setGroupBy(GroupBy.MONTH);

    // Set the consumption property
    PropertyData newProp = PropertyData.builder()
        .setFromDateTime(fromDateTime)
        .setToDateTime(toDateTime)
        .setDataValue("220")
        .setKeyName("consumption")
        .build();

    request.addTariffInput(newProp);

    CalculatedCost calculatedCost = callRunCalc("Test for grouping master tariff 522", request);

    // Assert groupBy dates
    for (CalculatedCostItem costItem : calculatedCost.getItems()) {

      assertEquals(costItem.getFromDateTime().getMillis(), fromDateTime.getMillis());
      assertEquals(costItem.getToDateTime().getMillis(), toDateTime.getMillis());

    }

  }

  @Test
  public void testCalculateForAccount() {

    Account newAccount = createAccount();
    // Now we run the calculation for the new Account. We set the date
    // range for which to run the calc.

    // Where the tariff has a time zone (most do) you can use it to make sure your dates are the
    // same
    DateTime fromDateTime = new DateTime(2012, 1, 1, 1, 0, 0, 0, DateTimeZone.forID("US/Pacific"));
    DateTime toDateTime = new DateTime(2013, 1, 1, 1, 0, 0, 0, DateTimeZone.forID("US/Pacific"));

    GetCalculatedCostRequest request = new GetCalculatedCostRequest();

    request.setFromDateTime(fromDateTime);
    request.setToDateTime(toDateTime);
    request.setDetailLevel(DetailLevel.ALL);

    request.setAccountId(newAccount.getAccountId());

    PropertyData newProp2 = PropertyData.builder()
        .setFromDateTime(fromDateTime)
        .setToDateTime(toDateTime)
        .setDataValue(newAccount.getAccountId())
        .setKeyName("accountId")
        .build();

    request.addTariffInput(newProp2);

    callRunCalc("Test for calculateForAccount", request);

    cleanup(newAccount.getAccountId());

  }


  @Test
  public void testCalculateMultipleProfiles() {

    // create profile with readings
    List<ReadingData> readings = new ArrayList<ReadingData>();
    // add two months of readings
    DateTime fromDateTime1 = new DateTime(2014, 3, 1, 1, 0, 0, 0, DateTimeZone.forID("US/Pacific"));
    DateTime toDateTime1 = new DateTime(2014, 4, 1, 1, 0, 0, 0, DateTimeZone.forID("US/Pacific"));
    ReadingData readingData1 = ReadingData.builder()
        .setQuantityUnit("kWh")
        .setFromDateTime(fromDateTime1)
        .setToDateTime(toDateTime1)
        .setQuantityValue(new BigDecimal("1000"))
        .build();
    readings.add(readingData1);

    // create profile 1
    DateTime fromDateTime2 = new DateTime(2014, 3, 1, 1, 0, 0, 0, DateTimeZone.forID("US/Pacific"));
    DateTime toDateTime2 = new DateTime(2014, 4, 1, 1, 0, 0, 0, DateTimeZone.forID("US/Pacific"));
    Profile profile1 = createProfileWithReadings(readings);
    List<ReadingData> readings2 = new ArrayList<ReadingData>();
    ReadingData readingData2 = ReadingData.builder()
        .setQuantityUnit("kWh")
        .setFromDateTime(fromDateTime2)
        .setToDateTime(toDateTime2)
        .setQuantityValue(new BigDecimal("900"))
        .build();
    readings2.add(readingData2);

    // create profile 2
    Profile profile2 = createProfileWithReadings(readings2);
    DateTime fromDateTime = new DateTime(2014, 3, 1, 1, 0, 0, 0, DateTimeZone.forID("US/Pacific"));
    DateTime toDateTime = new DateTime(2014, 4, 1, 1, 0, 0, 0, DateTimeZone.forID("US/Pacific"));

    // set up calculation request
    GetCalculatedCostRequest request = new GetCalculatedCostRequest();
    request.setFromDateTime(fromDateTime);
    request.setToDateTime(toDateTime);
    request.setDetailLevel(DetailLevel.ALL);
    request.setMasterTariffId(512l);

    // add profile1 input
    PropertyData profileProp1 = PropertyData.builder()
        .setFromDateTime(fromDateTime)
        .setToDateTime(toDateTime)
        .setDataValue(profile1.getProfileId())
        .setKeyName("profileId")
        .build();
    request.addTariffInput(profileProp1);

    // add profile2 input
    PropertyData profileProp2 = PropertyData.builder()
        .setFromDateTime(fromDateTime)
        .setToDateTime(toDateTime)
        .setDataValue(profile2.getProfileId())
        .setKeyName("profileId")
        .build();
    request.addTariffInput(profileProp2);

    // run calc
    callRunCalc("Test for calculateForAccount", request);

    //
    // Add tests
    //

    // clean up data
    cleanup(profile1.getAccountId());
    cleanup(profile2.getAccountId());

  }

  @Test
  public void testGetCalculationInputs() {

    // Where the tariff has a time zone (most do) you can use it to make sure your dates are the
    // same
    DateTime fromDateTime = new DateTime(2012, 1, 1, 0, 0, 0, 0, DateTimeZone.forID("US/Pacific"));
    DateTime toDateTime = new DateTime(2013, 1, 1, 0, 0, 0, 0, DateTimeZone.forID("US/Pacific"));

    GetCalculationInputsRequest request = new GetCalculationInputsRequest();

    request.setFromDateTime(fromDateTime);
    request.setToDateTime(toDateTime);
    request.setMasterTariffId(522l);
    request.setTerritoryId(3534l);

    Response<PropertyData> restResponse = calculateService.getCalculationInputs(request);

    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("bad count", restResponse.getCount() > 0);
    assertNotNull("results null", restResponse.getResults());
    assertTrue("results were empty", restResponse.getResults().size() != 0);
  }


  public CalculatedCost callRunCalc(String testCase, GetCalculatedCostRequest request) {

    Response<CalculatedCost> restResponse = calculateService.getCalculatedCost(request);

    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("bad count", restResponse.getCount() > 0);
    assertNotNull("results null", restResponse.getResults());
    assertEquals("results count", restResponse.getResults().size(), 1);

    CalculatedCost calculatedCost = restResponse.getResults().get(0);

    assertNotNull("calculatedCost total", calculatedCost.getTotalCost());
    log.debug("totalCost:" + calculatedCost.getTotalCost().toPlainString());

    for (CalculatedCostItem costItem : calculatedCost.getItems()) {

      assertNotNull("cost null", costItem.getCost());

    }

    return calculatedCost;
  }
}
