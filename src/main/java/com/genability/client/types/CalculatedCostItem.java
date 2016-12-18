package com.genability.client.types;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_CalculatedCostItem.Builder.class)
public abstract class CalculatedCostItem {

  public abstract @Nullable String getChargeClass();
  public abstract @Nullable ChargeType getChargeType();
  public abstract @Nullable BigDecimal getCost();
  public abstract @Nullable ZonedDateTime getDemandInterval();
  public abstract @Nullable Integer getDuration();
  public abstract @Nullable String getFamily();
  public abstract @Nullable String getFormula();
  public abstract @Nullable ZonedDateTime getFromDateTime();
  public abstract @Nullable Double getItemQuantity();
  public abstract @Nullable String getPeriod();
  public abstract @Nullable String getQuantityKey();
  public abstract @Nullable BigDecimal getRateAmount();
  public abstract @Nullable String getRateGroupName();
  public abstract @Nullable String getRateName();
  public abstract @Nullable BigDecimal getRateProration();
  public abstract @Nullable Integer getRateSequenceNumber();
  public abstract @Nullable RateUnit getRateType();
  public abstract @Nullable Long getSeasonId();
  public abstract @Nullable Long getTariffRateBandId();
  public abstract @Nullable Long getTariffRateId();
  public abstract @Nullable BigDecimal getTierLowerLimit();
  public abstract @Nullable BigDecimal getTierUpperLimit();
  public abstract @Nullable ZonedDateTime getToDateTime();
  public abstract @Nullable Long getTouId();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_CalculatedCostItem.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setChargeClass(@Nullable String chargeClass);
    public abstract Builder setChargeType(@Nullable ChargeType chargeType);
    public abstract Builder setCost(@Nullable BigDecimal cost);
    public abstract Builder setDemandInterval(@Nullable ZonedDateTime demandInterval);
    public abstract Builder setDuration(@Nullable Integer duration);
    public abstract Builder setFamily(@Nullable String family);
    public abstract Builder setFormula(@Nullable String formula);
    public abstract Builder setFromDateTime(@Nullable ZonedDateTime fromDateTime);
    public abstract Builder setItemQuantity(@Nullable Double itemQuantity);
    public abstract Builder setPeriod(@Nullable String period);
    public abstract Builder setQuantityKey(@Nullable String quantityKey);
    public abstract Builder setRateAmount(@Nullable BigDecimal rateAmount);
    public abstract Builder setRateGroupName(@Nullable String rateGroupName);
    public abstract Builder setRateName(@Nullable String rateName);
    public abstract Builder setRateProration(@Nullable BigDecimal rateProration);
    public abstract Builder setRateSequenceNumber(@Nullable Integer rateSequenceNumber);
    public abstract Builder setRateType(@Nullable RateUnit rateType);
    public abstract Builder setSeasonId(@Nullable Long seasonId);
    public abstract Builder setTariffRateBandId(@Nullable Long tariffRateBandId);
    public abstract Builder setTariffRateId(@Nullable Long tariffRateId);
    public abstract Builder setTierLowerLimit(@Nullable BigDecimal tierLowerLimit);
    public abstract Builder setTierUpperLimit(@Nullable BigDecimal tierUpperLimit);
    public abstract Builder setToDateTime(@Nullable ZonedDateTime toDateTime);
    public abstract Builder setTouId(@Nullable Long touId);

    public abstract CalculatedCostItem build();
  }
}
