package com.genability.client.api.request;

import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.Fields;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class GetAccountTariffsRequest extends AbstractGetNRequest {

  GetAccountTariffsRequest() {}

  public abstract @Nullable String getAccountId();
  public abstract @Nullable ZonedDateTime getEffectiveOn();
  public abstract @Nullable ZonedDateTime getFromDateTime();
  public abstract @Nullable Boolean getIncludeNotApplicable();
  public abstract @Nullable Boolean getIsActive();
  public abstract @Nullable Long getMasterTariffId();
  public abstract @Nullable Boolean getPopulateProperties();
  public abstract @Nullable Boolean getPopulateRates();
  public abstract @Nullable ImmutableList<String> getPrivacy();
  public abstract @Nullable String getProviderAccountId();
  public abstract @Nullable ImmutableList<String> getServiceTypes();
  public abstract @Nullable ImmutableList<String> getTariffTypes();
  public abstract @Nullable ZonedDateTime getToDateTime();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_GetAccountTariffsRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractGetNRequest.Builder<Builder> {

    public abstract Builder setAccountId(@Nullable String accountId);
    public abstract Builder setEffectiveOn(@Nullable ZonedDateTime effectiveOn);
    public abstract Builder setFromDateTime(@Nullable ZonedDateTime fromDateTime);
    public abstract Builder setIncludeNotApplicable(@Nullable Boolean includeNotApplicable);
    public abstract Builder setIsActive(@Nullable Boolean isActive);
    public abstract Builder setMasterTariffId(@Nullable Long masterTariffId);
    public abstract Builder setPopulateProperties(@Nullable Boolean populateProperties);
    public abstract Builder setPopulateRates(@Nullable Boolean populateRates);
    public abstract Builder setPrivacy(@Nullable String... privacy);
    public abstract Builder setPrivacy(@Nullable ImmutableList<String> privacy);
    public abstract Builder setProviderAccountId(@Nullable String providerAccountId);
    public abstract Builder setServiceTypes(@Nullable String... serviceTypes);
    public abstract Builder setServiceTypes(@Nullable ImmutableList<String> serviceTypes);
    public abstract Builder setTariffTypes(@Nullable String... tariffTypes);
    public abstract Builder setTariffTypes(@Nullable ImmutableList<String> tariffTypes);
    public abstract Builder setToDateTime(@Nullable ZonedDateTime toDateTime);

    public abstract GetAccountTariffsRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("effectiveOn", getEffectiveOn())
        .addParam("fromDateTime", getFromDateTime())
        .addParam("includeNotApplicable", getIncludeNotApplicable())
        .addParam("isActive", getIsActive())
        .addParam("masterTariffId", getMasterTariffId())
        .addParam("populateProperties", getPopulateProperties())
        .addParam("populateRates", getPopulateRates())
        .addParam("privacy", getPrivacy())
        .addParam("providerAccountId", getProviderAccountId())
        .addParam("serviceTypes", getServiceTypes())
        .addParam("tariffTypes", getTariffTypes())
        .addParam("toDateTime", getToDateTime())
        .build();
  }
}
