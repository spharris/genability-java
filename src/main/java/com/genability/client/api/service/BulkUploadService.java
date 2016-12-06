package com.genability.client.api.service;

import javax.inject.Inject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.genability.client.api.GenabilityClient;
import com.genability.client.api.request.BulkUploadRequest;
import com.genability.client.types.ReadingData;
import com.genability.client.types.Response;
import com.google.common.util.concurrent.ListenableFuture;

public class BulkUploadService {

  private static final TypeReference<Response<ReadingData>> READING_DATA_RESPONSE_TYPEREF =
      new TypeReference<Response<ReadingData>>() {};

  private final GenabilityClient client;
      
  @Inject
  BulkUploadService(GenabilityClient client) {
    this.client = client;
  }
      
  /**
   * Calls the REST service to upload a load profile or other usage data file
   * 
   * @param request The request.
   * @return The return value.
   */
  public ListenableFuture<Response<ReadingData>> uploadFile(BulkUploadRequest request) {
    String uri = "/rest/beta/loader/bulk/up";
    return client.fileUploadAsync(uri, request, READING_DATA_RESPONSE_TYPEREF);
  }
}
