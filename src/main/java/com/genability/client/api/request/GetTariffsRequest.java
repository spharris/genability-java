package com.genability.client.api.request;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.ChargeType;
import com.genability.client.types.CustomerClass;
import com.genability.client.types.Fields;
import com.genability.client.types.ServiceType;
import com.genability.client.types.TariffType;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class GetTariffsRequest extends AbstractGetNRequest2 {

  GetTariffsRequest() {}

  public abstract @Nullable Long getLseId();
  public abstract @Nullable Long getMasterTariffId();
  public abstract @Nullable DateTime getEffectiveOn();
  public abstract @Nullable LocalDate getOpenOn();
  public abstract @Nullable DateTime getFromDateTime();
  public abstract @Nullable DateTime getToDateTime();
  public abstract @Nullable Boolean getIsActive();
  public abstract @Nullable ImmutableList<CustomerClass> getCustomerClasses();
  public abstract @Nullable ImmutableList<TariffType> getTariffTypes();
  public abstract @Nullable ImmutableList<ServiceType> getServiceTypes();
  public abstract @Nullable ImmutableList<ChargeType> getChargeTypes();
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
  public abstract static class Builder extends AbstractGetNRequest2.Builder<Builder> {

    public abstract Builder setLseId(@Nullable Long lseId);
    public abstract Builder setMasterTariffId(@Nullable Long masterTariffId);
    public abstract Builder setEffectiveOn(@Nullable DateTime effectiveOn);
    public abstract Builder setOpenOn(@Nullable LocalDate openOn);
    public abstract Builder setFromDateTime(@Nullable DateTime fromDateTime);
    public abstract Builder setToDateTime(@Nullable DateTime toDateTime);
    public abstract Builder setIsActive(@Nullable Boolean isActive);
    public abstract Builder setCustomerClasses(@Nullable CustomerClass... customerClasses);
    public abstract Builder setCustomerClasses(@Nullable ImmutableList<CustomerClass> customerClasses);
    public abstract Builder setTariffTypes(@Nullable TariffType... tariffTypes);
    public abstract Builder setTariffTypes(@Nullable ImmutableList<TariffType> tariffTypes);
    public abstract Builder setServiceTypes(@Nullable ServiceType... serviceTypes);
    public abstract Builder setServiceTypes(@Nullable ImmutableList<ServiceType> serviceTypes);
    public abstract Builder setChargeTypes(@Nullable ChargeType... chargeTypes);
    public abstract Builder setChargeTypes(@Nullable ImmutableList<ChargeType> chargeTypes);
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
