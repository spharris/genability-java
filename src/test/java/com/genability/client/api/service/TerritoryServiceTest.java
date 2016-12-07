package com.genability.client.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.genability.client.api.request.GetTerritoriesRequest;
import com.genability.client.api.request.GetTerritoryRequest;
import com.genability.client.testing.TestClientModule;
import com.genability.client.types.Response;
import com.genability.client.types.Territory;
import com.google.inject.Guice;

@RunWith(JUnit4.class)
public class TerritoryServiceTest {

  @Inject private TerritoryService territoryService;
  
  @Before
  public void createInjector() {
    Guice.createInjector(new TestClientModule()).injectMembers(this);
  }

  @Test
  public void testGetOneTerritoryFromServer() throws Exception {
    Long territoryId = Long.valueOf(807L);

    GetTerritoryRequest request = GetTerritoryRequest.builder()
        .setTerritoryId(territoryId)
        .build();

    Response<Territory> result = territoryService.getTerritory(request).get();
    Territory t = result.getResults().get(0);

    assertEquals("Unsuccessful territory request", Response.STATUS_SUCCESS, result.getStatus());
    assertEquals("Got the wrong territory", territoryId, t.getTerritoryId());
  }

  @Test
  public void testGetOneTerritoryFromServerWithParameters() throws Exception {
    Long territoryId = Long.valueOf(807L);

    GetTerritoryRequest request = GetTerritoryRequest.builder()
        .setTerritoryId(territoryId)
        .setPopulateItems(true)
        .build();

    Response<Territory> result = territoryService.getTerritory(request).get();
    Territory t = result.getResults().get(0);

    assertEquals("Unsuccessful territory request", Response.STATUS_SUCCESS, result.getStatus());
    assertNotNull("Items was null even though it was specified", t.getItems());
  }

  @Test
  public void testGetTerritoriesFromServer() throws Exception {
    Long lseId = Long.valueOf(734L);

    GetTerritoriesRequest request = GetTerritoriesRequest.builder()
        .setLseId(lseId)
        .build();

    Response<Territory> result = territoryService.getTerritories(request).get();

    for (Territory t : result.getResults()) {
      assertEquals("Got data for the wrong LSE", lseId, t.getLseId());
    }
  }
}
