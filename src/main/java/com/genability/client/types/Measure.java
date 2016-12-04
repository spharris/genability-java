package com.genability.client.types;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_Measure.Builder.class)
public abstract class Measure {

  public abstract @Nullable BigDecimal getCostAccuracy();
  public abstract @Nullable BigDecimal getCostAmount();
  public abstract @Nullable BigDecimal getQuantityAccuracy();
  public abstract @Nullable BigDecimal getQuantityAmount();
  public abstract @Nullable BigDecimal getQuantitySum();
  public abstract @Nullable String getQuantityUnit();
  public abstract @Nullable BigDecimal getRateAccuracy();
  public abstract @Nullable BigDecimal getRateAmount();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_Measure.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setCostAccuracy(@Nullable BigDecimal costAccuracy);
    public abstract Builder setCostAmount(@Nullable BigDecimal costAmount);
    public abstract Builder setQuantityAccuracy(@Nullable BigDecimal quantityAccuracy);
    public abstract Builder setQuantityAmount(@Nullable BigDecimal quantityAmount);
    public abstract Builder setQuantitySum(@Nullable BigDecimal quantitySum);
    public abstract Builder setQuantityUnit(@Nullable String quantityUnit);
    public abstract Builder setRateAccuracy(@Nullable BigDecimal rateAccuracy);
    public abstract Builder setRateAmount(@Nullable BigDecimal rateAmount);

    public abstract Measure build();
  }
}
