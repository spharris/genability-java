package com.genability.client.api.service;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.genability.client.api.GenabilityClient;
import com.genability.client.api.request.GetLseRequest;
import com.genability.client.api.request.GetLsesRequest;
import com.genability.client.api.request.GetTerritoriesRequest;
import com.genability.client.types.Lse;
import com.genability.client.types.Response;
import com.genability.client.types.Territory;
import com.google.common.util.concurrent.ListenableFuture;


public class LseService {

  private static final TypeReference<Response<Territory>> TERRITORY_RESPONSE_TYPEREF =
      new TypeReference<Response<Territory>>() {};
  private static final TypeReference<Response<Lse>> LSE_RESPONSE_TYPEREF =
      new TypeReference<Response<Lse>>() {};

  private final GenabilityClient client;
      
  @Inject
  LseService(GenabilityClient client) {
    this.client = client;
  }
      
  /**
   * Calls the REST service to get a list of tariffs based on the arguments passed in.
   * 
   * @param request The request.
   * @return The return value.
   */
  public ListenableFuture<Response<Territory>> getTerritories(GetTerritoriesRequest request) {
    return client.getAsync("/rest/public/territories", request.getQueryParams(),
      TERRITORY_RESPONSE_TYPEREF);
  }

  /**
   * Calls the REST service to get the LSE requested
   * 
   * @param request The request.
   * @return The return value.
   */
  public ListenableFuture<Response<Lse>> getLse(GetLseRequest request) {
    checkNotNull(request.getLseId());

    String uri = String.format("/rest/public/lses/%s", request.getLseId());
    return client.getAsync(uri, request.getQueryParams(), LSE_RESPONSE_TYPEREF);
  }

  /**
   * Calls the REST service to get a list of LSEs based on the arguments passed in.
   * 
   * @param request The request.
   * @return The return value.
   */
  public ListenableFuture<Response<Lse>> getLses(GetLsesRequest request) {
    return client.getAsync("/rest/public/lses", request.getQueryParams(), LSE_RESPONSE_TYPEREF);
  }
}
