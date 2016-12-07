package com.genability.client.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.genability.client.api.request.GetPropertyKeyRequest;
import com.genability.client.api.request.GetPropertyKeysRequest;
import com.genability.client.api.request.GetPropertyLookupsRequest;
import com.genability.client.testing.TestClientModule;
import com.genability.client.types.DataType;
import com.genability.client.types.PropertyKey;
import com.genability.client.types.PropertyLookup;
import com.genability.client.types.PropertyLookupStats;
import com.genability.client.types.Response;
import com.google.inject.Guice;

@RunWith(JUnit4.class)
public class PropertyServiceTest {

  @Inject private PropertyService propertyService;
  
  @Before
  public void createInjector() {
    Guice.createInjector(new TestClientModule()).injectMembers(this);
  }
  
  @Test
  public void testGetPropertyKey() throws Exception {

    //
    // Assign
    //
    GetPropertyKeyRequest request = GetPropertyKeyRequest.builder()
        .setKeyName("qosVariableRateKeyHourlyWithSubkey")
        .build();

    //
    // Act
    //
    Response<PropertyKey> restResponse = propertyService.getPropertyKey(request).get();

    //
    // Assert
    //
    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);

  }

  @Test
  public void testPaginatedPropertyKeysList() throws Exception {
    // PG&E, since it has a lot of property keys
    GetPropertyKeysRequest request = GetPropertyKeysRequest.builder()
        .setEntityId(734L)
        .setEntityType("LSE")
        .build();
    Response<PropertyKey> restResponse = propertyService.getPropertyKeys(request).get();

    int totalPropertyKeys = restResponse.getCount();
    int propertyKeysVisited = 0;

    while (propertyKeysVisited < totalPropertyKeys) {
      assertEquals("Didn't page through the account list correctly.", propertyKeysVisited,
          restResponse.getPageStart().intValue());

      propertyKeysVisited += restResponse.getResults().size();

      request = request.toBuilder().setPageStart(
        restResponse.getPageStart() + restResponse.getPageCount()).build();
      restResponse = propertyService.getPropertyKeys(request).get();
    }

    assertEquals("Visited too many property keys", propertyKeysVisited, totalPropertyKeys);
  }

  @Test
  public void testGetPropertyKeys() throws Exception {

    //
    // Assign
    //
    GetPropertyKeysRequest request = GetPropertyKeysRequest.builder()
        .setFamily("market")
        .setDataType(DataType.LOOKUP)
        .build();

    //
    // Act
    //
    Response<PropertyKey> restResponse = propertyService.getPropertyKeys(request).get();

    //
    // Assert
    //
    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);

  }

  @Test
  public void testGetPropertyLookups() throws Exception {

    //
    // Assign
    //
    String targetPropertyKey = "hourlyPricingDayAheadPJM";
    GetPropertyLookupsRequest request = GetPropertyLookupsRequest.builder()
        .setKeyName(targetPropertyKey)
        .build();

    //
    // Act
    //
    Response<PropertyLookup> restResponse = propertyService.getPropertyLookups(request).get();

    //
    // Assert
    //
    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("Didn't get any results.", restResponse.getCount() > 0);
    assertEquals("Got results for the wrong propertyKey", targetPropertyKey,
        restResponse.getResults().get(0).getPropertyKey());
  }

  @Test
  public void testGetPropertyLookupsWithSubkey() throws Exception {

    //
    // Assign
    //
    String targetPropertyKey = "hourlyPricingDayAheadPJM";
    String targetSubPropertyKey = "51291";
    GetPropertyLookupsRequest request = GetPropertyLookupsRequest.builder()
        .setKeyName(targetPropertyKey)
        .setSubKeyName(targetSubPropertyKey)
        .setFromDateTime(new DateTime("2014-01-01"))
        .setToDateTime(new DateTime("2014-01-02"))
        .build();

    //
    // Act
    //
    Response<PropertyLookup> restResponse = propertyService.getPropertyLookups(request).get();

    //
    // Assert
    //
    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
    assertEquals("Didn't get the correct number of results.", new Integer(24),
        restResponse.getCount());
    assertEquals("Got results for the wrong propertyKey", targetPropertyKey,
        restResponse.getResults().get(0).getPropertyKey());
    assertEquals("Got results for the wrong subPropertyKey", targetSubPropertyKey,
        restResponse.getResults().get(0).getSubPropertyKey());

  }


  @Test
  public void testGetPropertyStats() throws Exception {

    //
    // Assign
    //
    String keyName = "qosVariableRateKeyHourly";

    //
    // Act
    //
    Response<PropertyLookupStats> restResponse = propertyService.getPropertyStats(keyName).get();

    //
    // Assert
    //
    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
  }


}
