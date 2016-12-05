package com.genability.client.api.request;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.Fields;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class GetAccountRatesRequest extends AbstractGetNRequest {

  GetAccountRatesRequest() {}

  public abstract @Nullable String getAccountId();
  public abstract @Nullable String getProviderAccountId();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_GetAccountRatesRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractGetNRequest.Builder<Builder> {

    public abstract Builder setAccountId(@Nullable String accountId);
    public abstract Builder setProviderAccountId(@Nullable String providerAccountId);

    public abstract GetAccountRatesRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("accountId", getAccountId())
        .addParam("providerAccountId", getProviderAccountId())
        .build();
  }
}
