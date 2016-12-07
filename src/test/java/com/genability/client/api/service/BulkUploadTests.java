package com.genability.client.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.net.URI;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.genability.client.api.request.BulkUploadRequest;
import com.genability.client.testing.DataLoaderUtil;
import com.genability.client.testing.TestClientModule;
import com.genability.client.types.Profile;
import com.genability.client.types.ReadingData;
import com.genability.client.types.Response;
import com.google.inject.Guice;

@RunWith(JUnit4.class)
public class BulkUploadTests {

  private Profile profile;

  @Inject private BulkUploadService bulkUploadService;
  @Inject private DataLoaderUtil dataLoader;
     
  @Before
  public void createInjector() {
    Guice.createInjector(new TestClientModule()).injectMembers(this);
  }
  
  @Before
  public void setUp() throws Exception {
    profile = dataLoader.createProfile();
  }

  @After
  public void tearDown() {
    if (profile != null) {
      dataLoader.cleanup(profile.getAccountId());
    }
  }

  @Test
  public void testUploadCSV() throws Exception {

    // bulk load readings to account
    URI resourceUri = new URI(getClass().getResource("/interval_data.csv").toString());
    File file = new File(resourceUri.getPath());

    BulkUploadRequest request = BulkUploadRequest.builder()
        .setFileData(file)
        .setFileFormat("csv")
        .setUsageProfileId(profile.getProfileId())
        .build();
    upload("Case upload CSV", request);

  }

  private void upload(String testCase, BulkUploadRequest request) throws Exception {
    Response<ReadingData> restResponse = bulkUploadService.uploadFile(request).get();

    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
  }


}
