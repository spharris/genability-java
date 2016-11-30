package com.genability.client.api.service;

import static org.junit.Assert.assertEquals; 
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.genability.client.api.request.GetTariffRequest;
import com.genability.client.api.request.GetTariffsRequest;
import com.genability.client.testing.TestClientModule;
import com.genability.client.types.CustomerClass;
import com.genability.client.types.Response;
import com.genability.client.types.SortOrder;
import com.genability.client.types.Tariff;
import com.genability.client.types.TariffRate;
import com.genability.client.types.TariffRateBand;
import com.genability.client.types.TariffType;
import com.google.inject.Guice;

@RunWith(JUnit4.class)
public class TariffServiceTests {

  @Inject private TariffService tariffService;
  
  @Before
  public void createInjector() {
    Guice.createInjector(new TestClientModule()).injectMembers(this);
  }
  
  @Test
  public void testGetTariff() throws Exception {
    String testCase = "Case 1 - call to get SCE's D Domestic tariff";
    GetTariffRequest request = GetTariffRequest.builder()
        .setMasterTariffId(447L)
        .build();
    callGetTariff(testCase, request);
  }

  @Test
  public void testGetTariffWithVariableRates() throws Exception {

    String testCase = "Check that variable rates are returned";
    String variableRateKey = "peakShavingVASchedule1";
    GetTariffRequest request = GetTariffRequest.builder()
        .setPopulateRates(true)
        .setLookupVariableRates(true)
        .setFromDateTime(new DateTime("2014-12-10"))
        .setToDateTime(new DateTime("2014-12-15"))
        .setMasterTariffId(122L)
        .build();

    Tariff tariff = callGetTariff(testCase, request);

    // Check that the variable rate component has its rateAmount set to a non-zero value
    // (this happens only when we set lookupVariableRates to true)
    Boolean foundDesiredVariableRateBand = false;
    for (TariffRate rate : tariff.getRates()) {
      if (variableRateKey.equals(rate.getVariableRateKey())) {
        assertTrue("no rateBands for " + variableRateKey + " component!",
            rate.getRateBands() != null && rate.getRateBands().size() > 0);
        TariffRateBand rb = rate.getRateBands().get(0);
        assertTrue("rateAmount not filled in despite lookupVariableRates=true",
            BigDecimal.ZERO.compareTo(rb.getRateAmount()) != 0);
        foundDesiredVariableRateBand = true;
        break;
      }
    }
    assertTrue("didn't get a variable rate band we were looking for to test!",
        foundDesiredVariableRateBand);
  }

  @Test
  public void testGetTariffs() throws Exception {

    GetTariffsRequest request = GetTariffsRequest.builder().build();

    String testCase = "Case 1 - simple unparameterized call";
    callGetTariffs(testCase, request);

    testCase = "Case 2 - find by zipcode";
    request = request.toBuilder().setZipCode("94105").build();
    callGetTariffs(testCase, request);

    testCase = "Case 3 - include the properties populated";
    request = request.toBuilder().setPopulateProperties(Boolean.TRUE).build();
    callGetTariffs(testCase, request);

    testCase = "Case 4 - explicit effective on";
    request = request.toBuilder()
        .setEffectiveOn(new DateTime(DateTime.now().getYear() - 1, 11, 11, 1, 0, 0, 0))
        .build();
    callGetTariffs(testCase, request);

    testCase = "Case 5 - common scenario, active residential tariffs for a zipcode";
    request = GetTariffsRequest.builder()
        .setCustomerClasses(CustomerClass.RESIDENTIAL)
        .setTariffTypes(TariffType.DEFAULT, TariffType.ALTERNATIVE)
        .setEffectiveOn(DateTime.now())
        .setZipCode("94105")
        .setSortOn("tariffType")
        .setSortOrder(SortOrder.DESCENDING) // so default tariffs come before alternative
        .setPopulateProperties(true)// so you know what properties it will take to run a calc
        .build();

    callGetTariffs(testCase, request);
  }

  @Test
  public void getTariffsWithAfterTax() throws Exception {
    String testCase = "Get tariffs with AFTER_TAX charge class";

    GetTariffsRequest request = GetTariffsRequest.builder()
        .setPopulateRates(true)
        .setZipCode("95818")
        .build();

    callGetTariffs(testCase, request);
  }

  public void callGetTariffs(String testCase, GetTariffsRequest request) throws Exception {

    Response<Tariff> restResponse = tariffService.getTariffs(request).get();

    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", Response.STATUS_SUCCESS, restResponse.getStatus());
    assertEquals("bad type", Tariff.REST_TYPE, restResponse.getType());
    assertTrue("bad count", restResponse.getCount() > 0);

    for (Tariff tariff : restResponse.getResults()) {

      assertNotNull("tariffId null", tariff.getTariffId());
      assertNotNull("effectiveDate", tariff.getEffectiveDate());
      assertNotNull("currency", tariff.getCurrency());

    }

  }

  public Tariff callGetTariff(String testCase, GetTariffRequest request) throws Exception {

    Response<Tariff> restResponse = tariffService.getTariff(request).get();

    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", Response.STATUS_SUCCESS, restResponse.getStatus());
    assertEquals("bad type", Tariff.REST_TYPE, restResponse.getType());
    assertEquals("bad count", new Integer(1), restResponse.getCount());

    Tariff tariff = restResponse.getResults().get(0);

    assertNotNull("tariffId null", tariff.getTariffId());
    assertNotNull("effectiveDate", tariff.getEffectiveDate());
    assertNotNull("currency", tariff.getCurrency());

    return tariff;
  }

}
