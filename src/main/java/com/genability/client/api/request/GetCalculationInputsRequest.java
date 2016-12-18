package com.genability.client.api.request;

import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.Fields;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class GetCalculationInputsRequest extends AbstractRequest {

  GetCalculationInputsRequest() {}

  public abstract @Nullable String getAccountId();
  public abstract @Nullable String getEstimate();
  public abstract @Nullable ZonedDateTime getFromDateTime();
  public abstract @Nullable Long getMasterTariffId();
  public abstract @Nullable String getProviderAccountId();
  public abstract @Nullable Long getTerritoryId();
  public abstract @Nullable ZonedDateTime getToDateTime();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_GetCalculationInputsRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractRequest.Builder<Builder> {

    public abstract Builder setAccountId(@Nullable String accountId);
    public abstract Builder setEstimate(@Nullable String estimate);
    public abstract Builder setFromDateTime(@Nullable ZonedDateTime fromDateTime);
    public abstract Builder setMasterTariffId(@Nullable Long masterTariffId);
    public abstract Builder setProviderAccountId(@Nullable String providerAccountId);
    public abstract Builder setTerritoryId(@Nullable Long territoryId);
    public abstract Builder setToDateTime(@Nullable ZonedDateTime toDateTime);

    public abstract GetCalculationInputsRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("accountId", getAccountId())
        .addParam("estimate", getEstimate())
        .addParam("fromDateTime", getFromDateTime())
        .addParam("masterTariffId", getMasterTariffId())
        .addParam("providerAccountId", getProviderAccountId())
        .addParam("territoryId", getTerritoryId())
        .addParam("toDateTime", getToDateTime())
        .build();
  }
}
