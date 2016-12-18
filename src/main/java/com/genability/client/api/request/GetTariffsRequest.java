package com.genability.client.api.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.ChargeType;
import com.genability.client.types.CustomerClass;
import com.genability.client.types.Fields;
import com.genability.client.types.ServiceType;
import com.genability.client.types.TariffType;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

@AutoValue
public abstract class GetTariffsRequest extends AbstractGetNRequest {

  GetTariffsRequest() {}

  public abstract @Nullable Long getLseId();
  public abstract @Nullable Long getMasterTariffId();
  public abstract @Nullable ZonedDateTime getEffectiveOn();
  public abstract @Nullable LocalDate getOpenOn();
  public abstract @Nullable ZonedDateTime getFromDateTime();
  public abstract @Nullable ZonedDateTime getToDateTime();
  public abstract @Nullable Boolean getIsActive();
  public abstract @Nullable ImmutableSet<CustomerClass> getCustomerClasses();
  public abstract @Nullable ImmutableSet<TariffType> getTariffTypes();
  public abstract @Nullable ImmutableSet<ServiceType> getServiceTypes();
  public abstract @Nullable ImmutableSet<ChargeType> getChargeTypes();
  public abstract @Nullable String getZipCode();
  public abstract @Nullable Boolean getPopulateRates();
  public abstract @Nullable Boolean getPopulateProperties();
  public abstract @Nullable String getAccountId();
  public abstract @Nullable BigDecimal getConsumption();
  public abstract @Nullable BigDecimal getDemand();
  public abstract @Nullable Boolean getHasNetMetering();
  public abstract @Nullable Boolean getHasTieredRates();
  public abstract @Nullable Boolean getHasContractedRates();
  public abstract @Nullable Boolean getHasTimeOfUseRates();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_GetTariffsRequest.Builder()
        .setFields(Fields.EXT);
  }
  
  @AutoValue.Builder
  public abstract static class Builder extends AbstractGetNRequest.Builder<Builder> {

    public abstract Builder setLseId(@Nullable Long lseId);
    public abstract Builder setMasterTariffId(@Nullable Long masterTariffId);
    public abstract Builder setEffectiveOn(@Nullable ZonedDateTime effectiveOn);
    public abstract Builder setOpenOn(@Nullable LocalDate openOn);
    public abstract Builder setFromDateTime(@Nullable ZonedDateTime fromDateTime);
    public abstract Builder setToDateTime(@Nullable ZonedDateTime toDateTime);
    public abstract Builder setIsActive(@Nullable Boolean isActive);
    public abstract Builder setCustomerClasses(@Nullable CustomerClass... customerClasses);
    public abstract Builder setCustomerClasses(@Nullable ImmutableSet<CustomerClass> customerClasses);
    public abstract Builder setTariffTypes(@Nullable TariffType... tariffTypes);
    public abstract Builder setTariffTypes(@Nullable ImmutableSet<TariffType> tariffTypes);
    public abstract Builder setServiceTypes(@Nullable ServiceType... serviceTypes);
    public abstract Builder setServiceTypes(@Nullable ImmutableSet<ServiceType> serviceTypes);
    public abstract Builder setChargeTypes(@Nullable ChargeType... chargeTypes);
    public abstract Builder setChargeTypes(@Nullable ImmutableSet<ChargeType> chargeTypes);
    public abstract Builder setZipCode(@Nullable String zipCode);
    public abstract Builder setPopulateRates(@Nullable Boolean populateRates);
    public abstract Builder setPopulateProperties(@Nullable Boolean populateProperties);
    public abstract Builder setAccountId(@Nullable String accountId);
    public abstract Builder setConsumption(@Nullable BigDecimal consumption);
    public abstract Builder setDemand(@Nullable BigDecimal demand);
    public abstract Builder setHasNetMetering(@Nullable Boolean hasNetMetering);
    public abstract Builder setHasTieredRates(@Nullable Boolean hasTieredRates);
    public abstract Builder setHasContractedRates(@Nullable Boolean hasContractedRates);
    public abstract Builder setHasTimeOfUseRates(@Nullable Boolean hasTimeOfUseRates);
    
    public abstract GetTariffsRequest build();
  }

  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("lseId", getLseId())
        .addParam("masterTariffId", getMasterTariffId())
        .addParam("effectiveOn", getEffectiveOn())
        .addParam("fromDateTime", getFromDateTime())
        .addParam("toDateTime", getToDateTime())
        .addParam("isActive", getIsActive())
        .addParam("customerClasses", getCustomerClasses())
        .addParam("tariffTypes", getTariffTypes())
        .addParam("serviceTypes", getServiceTypes())
        .addParam("chargeTypes", getChargeTypes())
        .addParam("zipCode", getZipCode())
        .addParam("populateProperties", getPopulateProperties())
        .addParam("populateRates", getPopulateRates())
        .addParam("accountId", getAccountId())
        .addParam("consumption", getConsumption())
        .addParam("demand", getDemand())
        .addParam("hasNetMetering", getHasNetMetering())
        .addParam("hasTieredRates", getHasTieredRates())
        .addParam("hasContractedRates", getHasContractedRates())
        .addParam("hasTimeOfUseRates", getHasTimeOfUseRates())
        .addParam("openOn", getOpenOn())
        .build();
  }
}
