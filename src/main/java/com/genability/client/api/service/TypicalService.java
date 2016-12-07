package com.genability.client.api.service;

import javax.inject.Inject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.genability.client.api.GenabilityClient;
import com.genability.client.api.request.BaselineRequest;
import com.genability.client.types.Baseline;
import com.genability.client.types.Response;
import com.google.common.util.concurrent.ListenableFuture;

public class TypicalService {

  private static final TypeReference<Response<Baseline>> BASELINE_RESPONSE_TYPEREF =
      new TypeReference<Response<Baseline>>() {};

  private static final String BEST_BASELINE_PATH = "/rest/v1/typicals/baselines/best";
  private static final String BEST_SOLAR_BASELINE_PATH = "/rest/v1/typicals/solar/best";

  private final GenabilityClient client;
      
  @Inject
  TypicalService(GenabilityClient client) {
    this.client = client;
  }
  
  public ListenableFuture<Response<Baseline>> getBestBaseline(final BaselineRequest request) {
    return client.getAsync(BEST_BASELINE_PATH, request.getQueryParams(), BASELINE_RESPONSE_TYPEREF);
  }

  public ListenableFuture<Response<Baseline>> getSolarBaseline(final BaselineRequest request) {
    return client.getAsync(BEST_SOLAR_BASELINE_PATH, request.getQueryParams(),
        BASELINE_RESPONSE_TYPEREF);
  }
}
