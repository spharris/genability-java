package com.genability.client.api.service;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.genability.client.api.GenabilityClient;
import com.genability.client.api.request.DeleteAccountRequest;
import com.genability.client.api.request.GetAccountRatesRequest;
import com.genability.client.api.request.GetAccountRequest;
import com.genability.client.api.request.GetAccountTariffsRequest;
import com.genability.client.api.request.GetAccountsRequest;
import com.genability.client.types.Account;
import com.genability.client.types.PropertyData;
import com.genability.client.types.Response;
import com.genability.client.types.Tariff;
import com.genability.client.types.TariffRate;
import com.google.common.util.concurrent.ListenableFuture;

public class AccountService {

  private static final TypeReference<Response<Account>> ACCOUNT_RESPONSE_TYPEREF =
      new TypeReference<Response<Account>>() {};

  private static final TypeReference<Response<PropertyData>> PROPERTY_DATA_RESPONSE_TYPEREF =
      new TypeReference<Response<PropertyData>>() {};

  private static final TypeReference<Response<TariffRate>> TARIFF_RATE_RESPONSE_TYPEREF =
      new TypeReference<Response<TariffRate>>() {};

  private static final TypeReference<Response<Tariff>> TARIFF_RESPONSE_TYPEREF =
      new TypeReference<Response<Tariff>>() {};

  private final GenabilityClient client;
      
  @Inject
  AccountService(GenabilityClient client) {
    this.client = client;
  }
      
  public ListenableFuture<Response<Account>> getAccounts(GetAccountsRequest request) {
    checkNotNull(request);

    return client.getAsync("/rest/v1/accounts", request.getQueryParams(), ACCOUNT_RESPONSE_TYPEREF);
  }

  public ListenableFuture<Response<Account>> getAccount(GetAccountRequest request) {
    String uri = String.format("/rest/v1/accounts/%s", request.getAccountId());
    return client.getAsync(uri, request.getQueryParams(), ACCOUNT_RESPONSE_TYPEREF);
  }

  public ListenableFuture<Response<Account>> deleteAccount(DeleteAccountRequest request) {
    checkNotNull(request.getAccountId());
    
    String uri = String.format("/rest/v1/accounts/%s", request.getAccountId());
    return client.deleteAsync(uri, request.getQueryParams(), ACCOUNT_RESPONSE_TYPEREF);
  }

  public ListenableFuture<Response<PropertyData>> getAccountProperties(GetAccountRequest request) {
    checkNotNull(request.getAccountId());

    String uri = String.format("/rest/v1/accounts/%s/properties", request.getAccountId());
    return client.getAsync(uri, request.getQueryParams(), PROPERTY_DATA_RESPONSE_TYPEREF);
  }

  public ListenableFuture<Response<Account>> addAccount(Account account) {
    checkNotNull(account);

    return client.postAsync("/rest/v1/accounts", account, ACCOUNT_RESPONSE_TYPEREF);
  }

  public ListenableFuture<Response<Account>> updateAccount(Account account) {
    checkNotNull(account);
    
    String uri = "/rest/v1/accounts";
    if (account.getAccountId() != null) {
      uri += "/" + account.getAccountId();
    }

    return client.putAsync(uri, account, ACCOUNT_RESPONSE_TYPEREF);
  }

  public ListenableFuture<Response<PropertyData>> interviewAccount(
      String accountId, PropertyData interviewAnswer) {
    checkNotNull(accountId);
    checkNotNull(interviewAnswer);

    String uri = String.format("/rest/v1/accounts/%s/interview", accountId);
    return client.putAsync(uri, interviewAnswer, PROPERTY_DATA_RESPONSE_TYPEREF);
  }

  public ListenableFuture<Response<TariffRate>> getAccountRates(GetAccountRatesRequest request) {
    checkNotNull(request.getAccountId());

    String uri = String.format("/rest/v1/accounts/%s/rates", request.getAccountId());
    return client.getAsync(uri, request.getQueryParams(), TARIFF_RATE_RESPONSE_TYPEREF);
  }

  public ListenableFuture<Response<Tariff>> getAccountTariffs(GetAccountTariffsRequest request) {
    checkNotNull(request.getAccountId());

    String uri = String.format("/rest/v1/accounts/%s/tariffs", request.getAccountId());
    return client.getAsync(uri, request.getQueryParams(), TARIFF_RESPONSE_TYPEREF);
  }
}
