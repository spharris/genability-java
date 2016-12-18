package com.genability.client.types;

import static com.google.common.base.MoreObjects.firstNonNull;

import java.time.ZonedDateTime;

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
@JsonDeserialize(builder = AutoValue_TariffRate.Builder.class)
public abstract class TariffRate {

  public static final String REST_TYPE = "TariffRate";

  @JsonProperty("chargeClass")
  public String getChargeClassString() {
    return EnumUtil.enumListString(getChargeClass());
  }
  
  public abstract @Nullable Long getTariffRateId();
  public abstract @Nullable Long getTariffId();
  public abstract @Nullable Long getRiderId();
  public abstract @Nullable Long getMasterTariffRateId();
  public abstract @Nullable Integer getTariffSequenceNumber();
  public abstract @Nullable Integer getTariffBookSequenceNumber();
  public abstract @Nullable String getRateGroupName();
  public abstract @Nullable String getTariffBookRateGroupName();
  public abstract @Nullable String getRateName();
  public abstract @Nullable String getTariffBookRateName();
  public abstract @Nullable ZonedDateTime getFromDateTime();
  public abstract @Nullable ZonedDateTime getToDateTime();
  public abstract @Nullable Territory getTerritory();
  public abstract @Nullable Season getSeason();
  public abstract @Nullable TimeOfUse getTimeOfUse();
  public abstract @Nullable ChargeType getChargeType();
  public abstract @Nullable ImmutableSet<ChargeClass> getChargeClass();
  public abstract @Nullable Period getChargePeriod();
  public abstract @Nullable TransactionType getTransactionType();
  public abstract @Nullable String getQuantityKey();
  public abstract @Nullable String getApplicabilityKey();
  public abstract @Nullable String getVariableLimitKey();
  public abstract @Nullable String getVariableRateKey();
  public abstract @Nullable String getVariableFactorKey();
  public abstract @Nullable String getVariableRateSubKey();
  public abstract @Nullable ImmutableList<TariffRateBand> getRateBands();
  public abstract @Nullable String getScenarios();
  
  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_TariffRate.Builder();
  }
  
  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {
    
    @JsonIgnore public abstract Builder setChargeClass(
        @Nullable ImmutableSet<ChargeClass> chargeClass);
    @JsonIgnore public abstract Builder setChargeClass(@Nullable ChargeClass... chargeClass);
    
    @JsonProperty("chargeClass")
    public Builder setChargeClassString(String chargeClass) {
      setChargeClass(EnumUtil.parseEnumList(chargeClass, ChargeClass.class));
      return this;
    }
    
    public abstract Builder setTariffRateId(@Nullable Long tariffRateId);
    public abstract Builder setTariffId(@Nullable Long tariffId);
    public abstract Builder setRiderId(@Nullable Long riderId);
    public abstract Builder setMasterTariffRateId(@Nullable Long masterTariffRateId);
    public abstract Builder setTariffSequenceNumber(@Nullable Integer tariffSequenceNumber);
    public abstract Builder setTariffBookSequenceNumber(@Nullable Integer tariffBookSequenceNumber);
    public abstract Builder setRateGroupName(@Nullable String rateGroupName);
    public abstract Builder setTariffBookRateGroupName(@Nullable String tariffBookRateGroupName);
    public abstract Builder setRateName(@Nullable String rateName);
    public abstract Builder setTariffBookRateName(@Nullable String tariffBookRateName);
    public abstract Builder setFromDateTime(@Nullable ZonedDateTime fromDateTime);
    public abstract Builder setToDateTime(@Nullable ZonedDateTime toDateTime);
    public abstract Builder setTerritory(@Nullable Territory territory);
    public abstract Builder setSeason(@Nullable Season season);
    public abstract Builder setTimeOfUse(@Nullable TimeOfUse timeOfUse);
    public abstract Builder setChargeType(@Nullable ChargeType chargeType);
    public abstract Builder setChargePeriod(@Nullable Period chargePeriod);
    public abstract Builder setTransactionType(@Nullable TransactionType transactionType);
    public abstract Builder setQuantityKey(@Nullable String quantityKey);
    public abstract Builder setApplicabilityKey(@Nullable String applicabilityKey);
    public abstract Builder setVariableLimitKey(@Nullable String variableLimitKey);
    public abstract Builder setVariableRateKey(@Nullable String variableRateKey);
    public abstract Builder setVariableFactorKey(@Nullable String variableFactorKey);
    public abstract Builder setVariableRateSubKey(@Nullable String variableRateSubKey);
    public abstract Builder setScenarios(@Nullable String scenarios);
    
    @JsonIgnore
    public abstract Builder setRateBands(@Nullable TariffRateBand... rateBands);
    
    @JsonProperty("rateBands")
    public abstract Builder setRateBands(@Nullable Iterable<TariffRateBand> rateBands);
    
    protected abstract ImmutableSet<ChargeClass> getChargeClass();
    protected abstract ImmutableList<TariffRateBand> getRateBands();
    protected abstract TariffRate autoBuild();
    
    public TariffRate build() {
      setChargeClass(firstNonNull(getChargeClass(), ImmutableSet.of()));
      setRateBands(firstNonNull(getRateBands(), ImmutableList.of()));
      return autoBuild();
    }
  }
}
