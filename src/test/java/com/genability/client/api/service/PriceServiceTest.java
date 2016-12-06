package com.genability.client.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.joda.time.DateTime;
import org.junit.Test;

import com.genability.client.api.request.GetPriceRequest;
import com.genability.client.types.Price;
import com.genability.client.types.PriceChange;
import com.genability.client.types.Response;

public class PriceServiceTest extends BaseServiceTest {

  private static PriceService priceService = genabilityClient.getPriceService();

  @Test
  public void testGetPrice() {

    DateTime start = DateTime.now();
    GetPriceRequest request = GetPriceRequest.builder()
        .setMasterTariffId(520L) // PGE residential tariff
        .setFromDateTime(start)
        .setToDateTime(start.plusDays(1))
        .build();

    Response<Price> restResponse = priceService.getPrice(request);

    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", Response.STATUS_SUCCESS, restResponse.getStatus());
    assertNotNull("results null", restResponse.getResults());

    for (Price price : restResponse.getResults()) {

      assertNotNull("price null", price);


      if (price.getPriceChanges() != null) {

        for (PriceChange priceChange : price.getPriceChanges()) {

          log.debug("Price Change " + priceChange.getChangeName());

          assertNotNull("priceChange null", priceChange);

        }
      }

    }

  }


}

