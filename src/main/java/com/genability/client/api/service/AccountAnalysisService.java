package com.genability.client.api.service;

import javax.inject.Inject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.genability.client.api.GenabilityClient;
import com.genability.client.api.request.AccountAnalysisRequest;
import com.genability.client.types.AccountAnalysis;
import com.genability.client.types.Response;
import com.google.common.util.concurrent.ListenableFuture;

public class AccountAnalysisService {

  private static final TypeReference<Response<AccountAnalysis>> ACCOUNT_ANALYSIS_RESPONSE_TYPEREF =
      new TypeReference<Response<AccountAnalysis>>() {};

  private final GenabilityClient client;
      
  @Inject
  AccountAnalysisService(GenabilityClient client) {
    this.client = client;
  }
      
  public ListenableFuture<Response<AccountAnalysis>> calculateSavingsAnalysis(
      AccountAnalysisRequest request) {
    return client.postAsync("/rest/v1/accounts/analysis", request, 
      ACCOUNT_ANALYSIS_RESPONSE_TYPEREF);
  }
}
