package com.genability.client.types;

import static com.google.common.base.MoreObjects.firstNonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.genability.client.util.EnumUtil;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

@AutoValue
@JsonDeserialize(builder = AutoValue_Tariff.Builder.class)
public abstract class Tariff {

  public static final String REST_TYPE = "Tariff";

  @JsonIgnore public abstract @Nullable ImmutableSet<ChargeType> getChargeTypes();
  @JsonProperty("chargeTypes")
  public String getChargeTypesString() {
    return EnumUtil.enumListString(getChargeTypes());
  }

  public abstract @Nullable Long getTariffId();
  public abstract @Nullable Long getMasterTariffId();
  public abstract @Nullable String getTariffCode();
  public abstract @Nullable String getTariffName();
  public abstract @Nullable String getTariffBookName();
  public abstract @Nullable Long getLseId();
  public abstract @Nullable String getLseName();
  public abstract @Nullable String getLseCode();
  public abstract @Nullable ServiceType getServiceType();
  public abstract @Nullable Long getPriorTariffId();
  public abstract @Nullable Long getDistributionLseId();
  public abstract @Nullable TariffType getTariffType();
  public abstract @Nullable CustomerClass getCustomerClass();
  public abstract @Nullable Integer getCustomerCount();
  public abstract @Nullable BigDecimal getCustomerLikelihood();
  public abstract @Nullable String getCustomerCountSource();
  public abstract @Nullable Long getTerritoryId();
  public abstract @Nullable LocalDate getEffectiveDate();
  public abstract @Nullable LocalDate getEndDate();
  public abstract @Nullable LocalDate getClosedDate();
  public abstract @Nullable ZoneId getTimeZone();
  public abstract @Nullable String getEffectiveOnRule();
  public abstract @Nullable Period getBillingPeriod();
  public abstract @Nullable String getCurrency();
  public abstract @Nullable String getChargePeriod();
  public abstract @Nullable BigDecimal getMinMonthlyConsumption();
  public abstract @Nullable BigDecimal getMaxMonthlyConsumption();
  public abstract @Nullable BigDecimal getMinMonthlyDemand();
  public abstract @Nullable BigDecimal getMaxMonthlyDemand();
  public abstract @Nullable Boolean getHasTimeOfUseRates();
  public abstract @Nullable Boolean getHasTieredRates();
  public abstract @Nullable Boolean getHasContractedRates();
  public abstract @Nullable Boolean getHasTariffApplicability();
  public abstract @Nullable Boolean getHasRateApplicability();
  public abstract @Nullable Boolean getHasNetMetering();
  public abstract @Nullable Boolean getIsActive();
  public abstract @Nullable String getPrivacy();
  public abstract @Nullable ImmutableList<TariffProperty> getProperties();
  public abstract @Nullable ImmutableList<TariffRate> getRates();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_Tariff.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    @JsonIgnore public abstract Builder setChargeTypes(
        @Nullable ImmutableSet<ChargeType> chargeTypes);
    @JsonIgnore public abstract Builder setChargeTypes(@Nullable ChargeType... chargeTypes);
    @JsonProperty("chargeTypes")
    public Builder setChargeTypesString(String chargeTypes) {
      setChargeTypes(EnumUtil.parseEnumList(chargeTypes, ChargeType.class));
      return this;
    }

    // TODO: DateTimeZone deserializer
    @JsonIgnore public abstract Builder setTimeZone(@Nullable ZoneId timeZone);
    @JsonProperty("timeZone")
    public Builder setTimeZoneString(@Nullable String timeZone) {
      if (timeZone != null) {
        setTimeZone(ZoneId.of(timeZone));
      }
      
      return this;
    }
    
    public abstract Builder setTariffId(@Nullable Long tariffId);
    public abstract Builder setMasterTariffId(@Nullable Long masterTariffId);
    public abstract Builder setTariffCode(@Nullable String tariffCode);
    public abstract Builder setTariffName(@Nullable String tariffName);
    public abstract Builder setTariffBookName(@Nullable String tariffBookName);
    public abstract Builder setLseId(@Nullable Long lseId);
    public abstract Builder setLseName(@Nullable String lseName);
    public abstract Builder setLseCode(@Nullable String lseCode);
    public abstract Builder setServiceType(@Nullable ServiceType serviceType);
    public abstract Builder setPriorTariffId(@Nullable Long priorTariffId);
    public abstract Builder setDistributionLseId(@Nullable Long distributionLseId);
    public abstract Builder setTariffType(@Nullable TariffType tariffType);
    public abstract Builder setCustomerClass(@Nullable CustomerClass customerClass);
    public abstract Builder setCustomerCount(@Nullable Integer customerCount);
    public abstract Builder setCustomerLikelihood(@Nullable BigDecimal customerLikelihood);
    public abstract Builder setCustomerCountSource(@Nullable String customerCountSource);
    public abstract Builder setTerritoryId(@Nullable Long territoryId);
    public abstract Builder setEffectiveDate(@Nullable LocalDate effectiveDate);
    public abstract Builder setEndDate(@Nullable LocalDate endDate);
    public abstract Builder setClosedDate(@Nullable LocalDate closedDate);
    public abstract Builder setEffectiveOnRule(@Nullable String effectiveOnRule);
    public abstract Builder setBillingPeriod(@Nullable Period billingPeriod);
    public abstract Builder setCurrency(@Nullable String currency);
    public abstract Builder setChargePeriod(@Nullable String chargePeriod);
    public abstract Builder setMinMonthlyConsumption(@Nullable BigDecimal minMonthlyConsumption);
    public abstract Builder setMaxMonthlyConsumption(@Nullable BigDecimal maxMonthlyConsumption);
    public abstract Builder setMinMonthlyDemand(@Nullable BigDecimal minMonthlyDemand);
    public abstract Builder setMaxMonthlyDemand(@Nullable BigDecimal maxMonthlyDemand);
    public abstract Builder setHasTimeOfUseRates(@Nullable Boolean hasTimeOfUseRates);
    public abstract Builder setHasTieredRates(@Nullable Boolean hasTieredRates);
    public abstract Builder setHasContractedRates(@Nullable Boolean hasContractedRates);
    public abstract Builder setHasTariffApplicability(@Nullable Boolean hasTariffApplicability);
    public abstract Builder setHasRateApplicability(@Nullable Boolean hasRateApplicability);
    public abstract Builder setHasNetMetering(@Nullable Boolean hasNetMetering);
    public abstract Builder setIsActive(@Nullable Boolean isActive);
    public abstract Builder setPrivacy(@Nullable String privacy);
    
    @JsonIgnore
    public abstract Builder setProperties(@Nullable TariffProperty... properties);
    
    @JsonProperty("properties")
    public abstract Builder setProperties(@Nullable Iterable<TariffProperty> properties);
    
    @JsonIgnore
    public abstract Builder setRates(@Nullable TariffRate... rates);
    
    @JsonProperty("rates")
    public abstract Builder setRates(@Nullable Iterable<TariffRate> rates);

    protected abstract ImmutableSet<ChargeType> getChargeTypes();
    protected abstract ImmutableList<TariffProperty> getProperties();
    protected abstract ImmutableList<TariffRate> getRates();
    protected abstract Tariff autoBuild();
    
    public Tariff build() {
      setChargeTypes(firstNonNull(getChargeTypes(), ImmutableSet.of()));
      setProperties(firstNonNull(getProperties(), ImmutableList.of()));
      setRates(firstNonNull(getRates (), ImmutableList.of()));
      return autoBuild();
    }
  }
}
