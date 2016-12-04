package com.genability.client.types.generated;

import static com.google.common.base.MoreObjects.firstNonNull;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.genability.client.types.CustomerClass;
import com.genability.client.types.Incentive.Eligibility;
import com.genability.client.types.RateUnit;
import com.genability.client.types.ServiceType;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
@JsonDeserialize(builder = AutoValue_Incentive.Builder.class)
public abstract class Incentive {

  public abstract @Nullable ImmutableList<IncentiveApplicability> getApplicabilities();
  public abstract @Nullable CustomerClass getCustomerClass();
  public abstract @Nullable Eligibility getEligibility();
  public abstract @Nullable DateTime getEndDate();
  public abstract @Nullable Long getIncentiveId();
  public abstract @Nullable String getIncentiveName();
  public abstract @Nullable String getIncentivePaidTo();
  public abstract @Nullable String getIncentiveType();
  public abstract @Nullable Boolean getIsExhausted();
  public abstract @Nullable String getJurisdiction();
  public abstract @Nullable Long getLseId();
  public abstract @Nullable String getLseName();
  public abstract @Nullable Long getMasterIncentiveId();
  public abstract @Nullable Long getPaymentCap();
  public abstract @Nullable Long getPaymentDuration();
  public abstract @Nullable BigDecimal getPercentCostCap();
  public abstract @Nullable PropertyKey getPercentCostCapKey();
  public abstract @Nullable String getProjectType();
  public abstract @Nullable Boolean getProjectTypeExclusive();
  public abstract @Nullable PropertyKey getQuantityKey();
  public abstract @Nullable String getQuantityKeyCap();
  public abstract @Nullable BigDecimal getRate();
  public abstract @Nullable RateUnit getRateUnit();
  public abstract @Nullable ImmutableList<IncentiveApplicability> getRequiredData();
  public abstract @Nullable String getRequirements();
  public abstract @Nullable ServiceType getServiceType();
  public abstract @Nullable DateTime getStartDate();
  public abstract @Nullable String getState();
  public abstract @Nullable String getSummary();
  public abstract @Nullable String getTariffCode();
  public abstract @Nullable Long getTerritoryId();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_Incentive.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setCustomerClass(@Nullable CustomerClass customerClass);
    public abstract Builder setEligibility(@Nullable Eligibility eligibility);
    public abstract Builder setEndDate(@Nullable DateTime endDate);
    public abstract Builder setIncentiveId(@Nullable Long incentiveId);
    public abstract Builder setIncentiveName(@Nullable String incentiveName);
    public abstract Builder setIncentivePaidTo(@Nullable String incentivePaidTo);
    public abstract Builder setIncentiveType(@Nullable String incentiveType);
    public abstract Builder setIsExhausted(@Nullable Boolean isExhausted);
    public abstract Builder setJurisdiction(@Nullable String jurisdiction);
    public abstract Builder setLseId(@Nullable Long lseId);
    public abstract Builder setLseName(@Nullable String lseName);
    public abstract Builder setMasterIncentiveId(@Nullable Long masterIncentiveId);
    public abstract Builder setPaymentCap(@Nullable Long paymentCap);
    public abstract Builder setPaymentDuration(@Nullable Long paymentDuration);
    public abstract Builder setPercentCostCap(@Nullable BigDecimal percentCostCap);
    public abstract Builder setPercentCostCapKey(@Nullable PropertyKey percentCostCapKey);
    public abstract Builder setProjectType(@Nullable String projectType);
    public abstract Builder setProjectTypeExclusive(@Nullable Boolean projectTypeExclusive);
    public abstract Builder setQuantityKey(@Nullable PropertyKey quantityKey);
    public abstract Builder setQuantityKeyCap(@Nullable String quantityKeyCap);
    public abstract Builder setRate(@Nullable BigDecimal rate);
    public abstract Builder setRateUnit(@Nullable RateUnit rateUnit);
    public abstract Builder setRequirements(@Nullable String requirements);
    public abstract Builder setServiceType(@Nullable ServiceType serviceType);
    public abstract Builder setStartDate(@Nullable DateTime startDate);
    public abstract Builder setState(@Nullable String state);
    public abstract Builder setSummary(@Nullable String summary);
    public abstract Builder setTariffCode(@Nullable String tariffCode);
    public abstract Builder setTerritoryId(@Nullable Long territoryId);

    @JsonIgnore
    public abstract Builder setApplicabilities(@Nullable IncentiveApplicability... applicabilities);

    @JsonProperty("applicabilities")
    public abstract Builder setApplicabilities(@Nullable ImmutableList<IncentiveApplicability> applicabilities);

    @JsonIgnore
    public abstract Builder setRequiredData(@Nullable IncentiveApplicability... requiredData);

    @JsonProperty("requiredData")
    public abstract Builder setRequiredData(@Nullable ImmutableList<IncentiveApplicability> requiredData);

    protected abstract ImmutableList<IncentiveApplicability> getApplicabilities();
    protected abstract ImmutableList<IncentiveApplicability> getRequiredData();
    protected abstract Incentive autoBuild();

    public Incentive build() {
      setApplicabilities(firstNonNull(getApplicabilities(), ImmutableList.of()));
      setRequiredData(firstNonNull(getRequiredData(), ImmutableList.of()));
      return autoBuild();
    }
  }
}
