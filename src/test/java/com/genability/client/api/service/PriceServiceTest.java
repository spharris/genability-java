package com.genability.client.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.genability.client.api.request.GetPriceRequest;
import com.genability.client.testing.TestClientModule;
import com.genability.client.types.Price;
import com.genability.client.types.PriceChange;
import com.genability.client.types.Response;
import com.google.inject.Guice;

@RunWith(JUnit4.class)
public class PriceServiceTest {

  @Inject private PriceService priceService;
     
  @Before
  public void createInjector() {
    Guice.createInjector(new TestClientModule()).injectMembers(this);
  }
  
  @Test
  public void testGetPrice() throws Exception {
    DateTime start = DateTime.now();
    GetPriceRequest request = GetPriceRequest.builder()
        .setMasterTariffId(520L) // PGE residential tariff
        .setFromDateTime(start)
        .setToDateTime(start.plusDays(1))
        .build();

    Response<Price> restResponse = priceService.getPrice(request).get();

    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", Response.STATUS_SUCCESS, restResponse.getStatus());
    assertNotNull("results null", restResponse.getResults());

    for (Price price : restResponse.getResults()) {
      assertNotNull("price null", price);
      if (price.getPriceChanges() != null) {
        for (PriceChange priceChange : price.getPriceChanges()) {
          assertNotNull("priceChange null", priceChange);
        }
      }
    }
  }
}

