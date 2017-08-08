package com.genability.client.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.annotation.Nullable;

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
  public abstract @Nullable BigDecimal getItemQuantity();
  public abstract @Nullable String getPeriod();
  public abstract @Nullable String getQuantityKey();
  public abstract @Nullable BigDecimal getRateAmount();
  public abstract @Nullable String getRateGroupName();
  public abstract @Nullable String getRateName();
  public abstract @Nullable BigDecimal getRateProration();
  public abstract @Nullable Integer getRateSequenceNumber();
  public abstract @Nullable RateUnit getRateType();
  public abstract @Nullable Long getSeasonId();
  public abstract @Nullable Long getTariffId();
  public abstract @Nullable Long getTariffRateBandId();
  public abstract @Nullable Long getTariffRateId();
  public abstract @Nullable BigDecimal getTierLowerLimit();
  public abstract @Nullable BigDecimal getTierUpperLimit();
  public abstract @Nullable ZonedDateTime getToDateTime();
  public abstract @Nullable Long getTouId();

  public static Builder of(TariffRate rate) {
    CalculatedCostItem.Builder builder = new AutoValue_CalculatedCostItem.Builder()
        .setTariffId(rate.getTariffId())
        .setTariffRateId(rate.getTariffRateId())
        .setRateGroupName(rate.getRateGroupName())
        .setRateName(rate.getRateName())
        .setQuantityKey(rate.getQuantityKey())
        .setChargeType(rate.getChargeType());
    
    if (rate.getSeason() != null) {
      builder.setSeasonId(rate.getSeason().getSeasonId());
    }
    
    if (rate.getTimeOfUse() != null) {
      builder.setTouId(rate.getTimeOfUse().getTouId());
    }
    
    return builder;
  }
  
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
    public abstract Builder setItemQuantity(@Nullable BigDecimal itemQuantity);
    public abstract Builder setPeriod(@Nullable String period);
    public abstract Builder setQuantityKey(@Nullable String quantityKey);
    public abstract Builder setRateAmount(@Nullable BigDecimal rateAmount);
    public abstract Builder setRateGroupName(@Nullable String rateGroupName);
    public abstract Builder setRateName(@Nullable String rateName);
    public abstract Builder setRateProration(@Nullable BigDecimal rateProration);
    public abstract Builder setRateSequenceNumber(@Nullable Integer rateSequenceNumber);
    public abstract Builder setRateType(@Nullable RateUnit rateType);
    public abstract Builder setSeasonId(@Nullable Long seasonId);
    public abstract Builder setTariffId(@Nullable Long tariffId);
    public abstract Builder setTariffRateBandId(@Nullable Long tariffRateBandId);
    public abstract Builder setTariffRateId(@Nullable Long tariffRateId);
    public abstract Builder setTierLowerLimit(@Nullable BigDecimal tierLowerLimit);
    public abstract Builder setTierUpperLimit(@Nullable BigDecimal tierUpperLimit);
    public abstract Builder setToDateTime(@Nullable ZonedDateTime toDateTime);
    public abstract Builder setTouId(@Nullable Long touId);

    public abstract CalculatedCostItem build();
  }
}
