 package com.genability.client.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.genability.client.api.request.GetTerritoriesRequest;
import com.genability.client.testing.TestClientModule;
import com.genability.client.types.Response;
import com.genability.client.types.Territory;
import com.google.inject.Guice;

@RunWith(JUnit4.class)
public class LseServiceTest {

  @Inject private LseService lseService;
     
  @Before
  public void createInjector() {
    Guice.createInjector(new TestClientModule()).injectMembers(this);
  }
  
  @Test
  public void testGetTerritories() throws Exception {
    GetTerritoriesRequest request = GetTerritoriesRequest.builder()
        .setLseId(734L) // This is PG&E
        .build();
    callGetTerritories("Case 1", request);
  }

  @Test
  public void testGetTerritoryItems() throws Exception {
    GetTerritoriesRequest request = GetTerritoriesRequest.builder()
        .setLseId(734l)
        .setPopulateItems(true)
        .build();
    callGetTerritories("Case 2", request);
  }

  public void callGetTerritories(String testCase, GetTerritoriesRequest request) throws Exception {
    Response<Territory> restResponse = lseService.getTerritories(request).get();
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
