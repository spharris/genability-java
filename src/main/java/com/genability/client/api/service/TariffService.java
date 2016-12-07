package com.genability.client.api.service;

import javax.inject.Inject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.genability.client.api.GenabilityClient;
import com.genability.client.api.request.GetTariffRequest;
import com.genability.client.api.request.GetTariffsRequest;
import com.genability.client.types.Response;
import com.genability.client.types.Tariff;
import com.google.common.util.concurrent.ListenableFuture;

public class TariffService {

  private static final TypeReference<Response<Tariff>> TARIFF_RESPONSE_TYPEREF =
      new TypeReference<Response<Tariff>>() {};

  private final GenabilityClient client;
      
  @Inject
  TariffService(GenabilityClient client) {
    this.client = client;
  }
      
  /**
   * Calls the REST service to get a list of tariffs based on the arguments passed in.
   * 
   * @param request The request.
   * @return The return value.
   */
  public ListenableFuture<Response<Tariff>> getTariffs(GetTariffsRequest request) {
    return client.getAsync("/rest/public/tariffs", request.getQueryParams(),
      TARIFF_RESPONSE_TYPEREF);
  }


  /**
   * Calls the REST service to get one tariff based on the arguments passed in.
   * 
   * @param request The request.
   * @return The return value.
   */
  public ListenableFuture<Response<Tariff>> getTariff(GetTariffRequest request) {
    return client.getAsync("/rest/public/tariffs/" + request.getMasterTariffId(),
        request.getQueryParams(), TARIFF_RESPONSE_TYPEREF);
  }
}
