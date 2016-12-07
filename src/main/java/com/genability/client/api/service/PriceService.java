package com.genability.client.api.service;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.genability.client.api.GenabilityClient;
import com.genability.client.api.request.GetPriceRequest;
import com.genability.client.types.Price;
import com.genability.client.types.Response;
import com.google.common.util.concurrent.ListenableFuture;

public class PriceService {

  private static final TypeReference<Response<Price>> PRICE_RESPONSE_TYPEREF =
      new TypeReference<Response<Price>>() {};

  private final GenabilityClient client;
      
  @Inject
  PriceService(GenabilityClient client) {
    this.client = client;
  }
      
  /**
   * Calls the REST service to get a Profile based on the arguments passed in.
   * 
   * @param request The request.
   * @return The return value.
   */
  public ListenableFuture<Response<Price>> getPrice(GetPriceRequest request) {
    checkNotNull(request.getMasterTariffId());

    String uri = String.format("/rest/public/prices/%s", request.getMasterTariffId());
    return client.getAsync(uri, request.getQueryParams(), PRICE_RESPONSE_TYPEREF);
  }
}
