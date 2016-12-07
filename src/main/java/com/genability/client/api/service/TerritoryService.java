package com.genability.client.api.service;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.genability.client.api.GenabilityClient;
import com.genability.client.api.request.GetTerritoriesRequest;
import com.genability.client.api.request.GetTerritoryRequest;
import com.genability.client.types.Response;
import com.genability.client.types.Territory;
import com.google.common.util.concurrent.ListenableFuture;

public class TerritoryService {
  private static final TypeReference<Response<Territory>> TERRITORY_RESPONSE_TYPEREF =
      new TypeReference<Response<Territory>>() {};
  private static final String URL_BASE = "/rest/public/territories/";

  private final GenabilityClient client;
      
  @Inject
  TerritoryService(GenabilityClient client) {
    this.client = client;
  }
  
  public ListenableFuture<Response<Territory>> getTerritory(GetTerritoryRequest request) {
    checkNotNull(request.getTerritoryId());
    
    String url = URL_BASE + request.getTerritoryId();
    return client.getAsync(url, request.getQueryParams(), TERRITORY_RESPONSE_TYPEREF);
  }

  public ListenableFuture<Response<Territory>> getTerritories(GetTerritoriesRequest request) {
    return client.getAsync(URL_BASE, request.getQueryParams(), TERRITORY_RESPONSE_TYPEREF);
  }
}
