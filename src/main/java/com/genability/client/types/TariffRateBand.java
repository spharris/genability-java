package com.genability.client.types;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_TariffRateBand.Builder.class)
public abstract class TariffRateBand {

  public abstract @Nullable Long getTariffRateBandId();
  public abstract @Nullable Long getTariffRateId();
  public abstract @Nullable Integer getRateSequenceNumber();
  public abstract @Nullable Boolean getHasConsumptionLimit();
  public abstract @Nullable BigDecimal getConsumptionUpperLimit();
  public abstract @Nullable Boolean getHasDemandLimit();
  public abstract @Nullable BigDecimal getDemandUpperLimit();
  public abstract @Nullable Boolean getHasPropertyLimit();
  public abstract @Nullable BigDecimal getPropertyUpperLimit();
  public abstract @Nullable String getApplicabilityValue();
  public abstract @Nullable BigDecimal getCalculationFactor();
  public abstract @Nullable BigDecimal getRateAmount();
  public abstract @Nullable RateUnit getRateUnit();
  public abstract @Nullable Boolean getIsCredit();
  public abstract @Nullable String getPrevUpperLimit();
  
  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_TariffRateBand.Builder();
  }
  
  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {
    
    public abstract Builder setTariffRateBandId(@Nullable Long tariffRateBandId);
    public abstract Builder setTariffRateId(@Nullable Long tariffRateId);
    public abstract Builder setRateSequenceNumber(@Nullable Integer rateSequenceNumber);
    public abstract Builder setHasConsumptionLimit(@Nullable Boolean hasConsumptionLimit);
    public abstract Builder setConsumptionUpperLimit(@Nullable BigDecimal consumptionUpperLimit);
    public abstract Builder setHasDemandLimit(@Nullable Boolean hasDemandLimit);
    public abstract Builder setDemandUpperLimit(@Nullable BigDecimal demandUpperLimit);
    public abstract Builder setHasPropertyLimit(@Nullable Boolean hasPropertyLimit);
    public abstract Builder setPropertyUpperLimit(@Nullable BigDecimal propertyUpperLimit);
    public abstract Builder setApplicabilityValue(@Nullable String applicabilityValue);
    public abstract Builder setCalculationFactor(@Nullable BigDecimal calculationFactor);
    public abstract Builder setRateAmount(@Nullable BigDecimal rateAmount);
    public abstract Builder setRateUnit(@Nullable RateUnit rateUnit);
    public abstract Builder setIsCredit(@Nullable Boolean isCredit);
    public abstract Builder setPrevUpperLimit(@Nullable String prevUpperLimit);
    
    public abstract TariffRateBand build();
  }
}
