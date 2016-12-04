package com.genability.client.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.net.URI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.genability.client.api.request.BulkUploadRequest;
import com.genability.client.types.Profile;
import com.genability.client.types.ReadingData;
import com.genability.client.types.Response;

public class BulkUploadTests extends BaseServiceTests {

  private static BulkUploadService bulkUploadService = genabilityClient.getBulkUploadService();

  private Profile profile;

  @Before
  public void setUp() {
    // create test profile
    profile = this.createProfile();
  }

  @After
  public void tearDown() {
    // clean up test data
    if (profile != null) {
      this.cleanup(profile.getAccountId());
    }
  }

  @Test
  public void testUploadCSV() throws Exception {

    // bulk load readings to account
    BulkUploadRequest request = new BulkUploadRequest();

    URI resourceUri = new URI(getClass().getResource("/interval_data.csv").toString());
    File file = new File(resourceUri.getPath());

    request.setFileData(file);
    request.setFileFormat("csv");
    request.setUsageProfileId(profile.getProfileId());
    upload("Case upload CSV", request);

  }

  private void upload(String testCase, BulkUploadRequest request) {

    Response<ReadingData> restResponse = bulkUploadService.uploadFile(request);

    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
  }


}
