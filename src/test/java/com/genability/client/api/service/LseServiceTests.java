package com.genability.client.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.genability.client.api.request.GetTerritoriesRequest;
import com.genability.client.types.Response;
import com.genability.client.types.Territory;

public class LseServiceTests extends BaseServiceTests {

  private static LseService lseService = genabilityClient.getLseService();

  @Test
  public void testGetTerritories() {

    GetTerritoriesRequest request = GetTerritoriesRequest.builder()
        .setLseId(734L) // This is PG&E
        .build();
    callGetTerritories("Case 1", request);

  }

  @Test
  public void testGetTerritoryItems() {

    GetTerritoriesRequest request = GetTerritoriesRequest.builder()
        .setLseId(734l)
        .setPopulateItems(true)
        .build();
    callGetTerritories("Case 2", request);

  }


  public void callGetTerritories(String testCase, GetTerritoriesRequest request) {

    Response<Territory> restResponse = lseService.getTerritories(request);

    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("bad count", restResponse.getCount() > 0);

    for (Territory territory : restResponse.getResults()) {

      assertNotNull("territoryId null", territory.getTerritoryId());
      assertNotNull("lseId null", territory.getLseId());
      assertNotNull("lseName null", territory.getLseName());

    }

  }
}
