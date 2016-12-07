package com.genability.client.api.service;

import javax.inject.Inject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.genability.client.api.GenabilityClient;
import com.genability.client.api.request.GetIncentiveApplicabilitiesRequest;
import com.genability.client.api.request.GetIncentivesRequest;
import com.genability.client.types.Incentive;
import com.genability.client.types.IncentiveApplicability;
import com.genability.client.types.Response;
import com.google.common.util.concurrent.ListenableFuture;

public class IncentiveService {

  private static final TypeReference<Response<Incentive>> INCENTIVE_RESPONSE_TYPEREF =
      new TypeReference<Response<Incentive>>() {};

  private static final TypeReference<Response<IncentiveApplicability>> 
      INCENTIVE_APPLICABILITY_RESPONSE_TYPEREF =
          new TypeReference<Response<IncentiveApplicability>>() {};
  
  private static final String BASE_URL = "/rest/beta/incentives";

  private final GenabilityClient client;
      
  @Inject
  IncentiveService(GenabilityClient client) {
    this.client = client;
  }
  
  public ListenableFuture<Response<Incentive>> getIncentives(GetIncentivesRequest request) {
    return client.getAsync(BASE_URL, request.getQueryParams(), INCENTIVE_RESPONSE_TYPEREF);
  }

  public ListenableFuture<Response<Incentive>> getIncentive(long masterIncentiveId) {
    String url = String.format("%s/%s", BASE_URL, masterIncentiveId);
    return client.getAsync(url, null, INCENTIVE_RESPONSE_TYPEREF);
  }

  public ListenableFuture<Response<IncentiveApplicability>> getIncentiveApplicabilities(
      GetIncentiveApplicabilitiesRequest request) {
    String url = String.format("%s/applicabilities", BASE_URL);
    return client.getAsync(url, request.getQueryParams(), INCENTIVE_APPLICABILITY_RESPONSE_TYPEREF);
  }
}
