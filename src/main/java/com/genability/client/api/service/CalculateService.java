package com.genability.client.api.service;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.type.TypeReference;
import com.genability.client.api.GenabilityClient;
import com.genability.client.api.request.GetCalculatedCostRequest;
import com.genability.client.api.request.GetCalculationInputsRequest;
import com.genability.client.types.CalculatedCost;
import com.genability.client.types.DetailLevel;
import com.genability.client.types.GroupBy;
import com.genability.client.types.PropertyData;
import com.genability.client.types.Response;
import com.google.common.util.concurrent.ListenableFuture;

public class CalculateService {

  private static final TypeReference<Response<CalculatedCost>> CALCULATEDCOST_RESPONSE_TYPEREF =
      new TypeReference<Response<CalculatedCost>>() {};
  private static final TypeReference<Response<PropertyData>> PROPERTYDATA_RESPONSE_TYPEREF =
      new TypeReference<Response<PropertyData>>() {};

  private final GenabilityClient client;
      
  @Inject
  CalculateService(GenabilityClient client) {
    this.client = client;
  }
      
  /**
   * Calls the REST service to run a calculation
   * 
   * @param request The request.
   * @return The return value.
   */
  public ListenableFuture<Response<CalculatedCost>> getCalculatedCost(
      GetCalculatedCostRequest request) {
    String uri = "/rest/public/calculate";
    return client.postAsync(uri, request, CALCULATEDCOST_RESPONSE_TYPEREF);
  }

  /**
   * Calls the REST service to get the required inputs to run a Calculation for a given tariffId.
   * The tariffId can be explicitly stated within the GetCalculationInputsRequest object, or if it
   * is null, the service will expect it to be in the Account that is passed in.
   * 
   * @param request The request.
   * @return The return value.
   */
  public ListenableFuture<Response<PropertyData>> getCalculationInputs(
      GetCalculationInputsRequest request) {
    String uri = "/rest/public/calculate";
    if (request.getMasterTariffId() != null) {
      uri += "/" + request.getMasterTariffId();
    } else {
      // This means we will be getting the tariffId from within the Account.
      // Do nothing.
    }

    return client.getAsync(uri, request.getQueryParams(), PROPERTYDATA_RESPONSE_TYPEREF);
  }

  /**
   * Runs calculation on Account using a simplified method with passed in parameters.
   * 
   * @param accountId The accountId.
   * @param masterTariffId The masterTariffId.
   * @param fromDateTime The fromDateTime.
   * @param toDateTime The toDateTime.
   * @param detailLevel The detailLevel.
   * @param groupBy The groupBy.
   * @return The return value.
   */
  public ListenableFuture<Response<CalculatedCost>> runCalculationOnAccount(String accountId,
      Long masterTariffId,
      DateTime fromDateTime,
      DateTime toDateTime,
      DetailLevel detailLevel,
      GroupBy groupBy) {
    checkNotNull(accountId);
    
    String uri = String.format("/rest/public/calculate/account/%s", accountId);
    GetCalculatedCostRequest request = GetCalculatedCostRequest.builder()
        .setMasterTariffId(masterTariffId)
        .setAccountId(accountId)
        .setFromDateTime(fromDateTime)
        .setToDateTime(toDateTime)
        .setDetailLevel(detailLevel)
        .setGroupBy(groupBy)
        .build();

    return client.getAsync(uri, request.getQueryParams(), CALCULATEDCOST_RESPONSE_TYPEREF);
  }

}
