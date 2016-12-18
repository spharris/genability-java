package com.genability.client.api.request;

import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.Fields;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class GetTariffRequest extends AbstractRequest {

  GetTariffRequest() {}
  
  public abstract @Nullable Long getMasterTariffId();
  public abstract @Nullable String getAccountId();
  public abstract @Nullable Boolean getPopulateProperties();
  public abstract @Nullable Boolean getPopulateRates();
  public abstract @Nullable Boolean getPopulateDocuments();
  public abstract @Nullable ZonedDateTime getEffectiveOn();
  public abstract @Nullable Boolean getApplicableRatesOnly();
  public abstract @Nullable Boolean getApplyContractedRates();
  public abstract @Nullable Boolean getLookupVariableRates();
  public abstract @Nullable Boolean getBundleRates();
  public abstract @Nullable ZonedDateTime getFromDateTime();
  public abstract @Nullable ZonedDateTime getToDateTime();
  public abstract @Nullable Long getTerritoryId();
  
  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_GetTariffRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractRequest.Builder<Builder> {
    
    public abstract Builder setTerritoryId(@Nullable Long territoryId);
    public abstract Builder setMasterTariffId(@Nullable Long masterTariffId);
    public abstract Builder setAccountId(@Nullable String accountId);
    public abstract Builder setPopulateProperties(@Nullable Boolean populateProperties);
    public abstract Builder setPopulateRates(@Nullable Boolean populateRates);
    public abstract Builder setPopulateDocuments(@Nullable Boolean populateDocuments);
    public abstract Builder setEffectiveOn(@Nullable ZonedDateTime effectiveOn);
    public abstract Builder setApplicableRatesOnly(@Nullable Boolean applicableRatesOnly);
    public abstract Builder setApplyContractedRates(@Nullable Boolean applyContractedRates);
    public abstract Builder setLookupVariableRates(@Nullable Boolean lookupVariableRates);
    public abstract Builder setBundleRates(@Nullable Boolean bundleRates);
    public abstract Builder setFromDateTime(@Nullable ZonedDateTime fromDateTime);
    public abstract Builder setToDateTime(@Nullable ZonedDateTime toDateTime);
    
    public abstract GetTariffRequest build();
  }

  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("accountId", getAccountId())
        .addParam("populateProperties", getPopulateProperties())
        .addParam("populateRates", getPopulateRates())
        .addParam("populateDocuments", getPopulateDocuments())
        .addParam("effectiveOn", getEffectiveOn())
        .addParam("applicableRatesOnly", getApplicableRatesOnly())
        .addParam("applyContractedRates", getApplyContractedRates())
        .addParam("lookupVariableRates", getLookupVariableRates())
        .addParam("bundleRates", getBundleRates())
        .addParam("fromDateTime", getFromDateTime())
        .addParam("toDateTime", getToDateTime())
        .addParam("territoryId", getTerritoryId())
        .build();
  }
}
