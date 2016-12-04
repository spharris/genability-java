package com.genability.client.types;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_PropertyLookup.Builder.class)
public abstract class PropertyLookup {

  public abstract @Nullable BigDecimal getActualValue();
  public abstract @Nullable BigDecimal getBestAccuracy();
  public abstract @Nullable BigDecimal getBestValue();
  public abstract @Nullable BigDecimal getForecastAccuracy();
  public abstract @Nullable LocalDateTime getForecastDateTime();
  public abstract @Nullable BigDecimal getForecastValue();
  public abstract @Nullable LocalDateTime getFromDateTime();
  public abstract @Nullable Long getLookupId();
  public abstract @Nullable BigDecimal getLseForecastAccuracy();
  public abstract @Nullable BigDecimal getLseForecastValue();
  public abstract @Nullable String getPropertyKey();
  public abstract @Nullable String getSubPropertyKey();
  public abstract @Nullable LocalDateTime getToDateTime();
  public abstract @Nullable BigDecimal getValue();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_PropertyLookup.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setActualValue(@Nullable BigDecimal actualValue);
    public abstract Builder setBestAccuracy(@Nullable BigDecimal bestAccuracy);
    public abstract Builder setBestValue(@Nullable BigDecimal bestValue);
    public abstract Builder setForecastAccuracy(@Nullable BigDecimal forecastAccuracy);
    public abstract Builder setForecastDateTime(@Nullable LocalDateTime forecastDateTime);
    public abstract Builder setForecastValue(@Nullable BigDecimal forecastValue);
    public abstract Builder setFromDateTime(@Nullable LocalDateTime fromDateTime);
    public abstract Builder setLookupId(@Nullable Long lookupId);
    public abstract Builder setLseForecastAccuracy(@Nullable BigDecimal lseForecastAccuracy);
    public abstract Builder setLseForecastValue(@Nullable BigDecimal lseForecastValue);
    public abstract Builder setPropertyKey(@Nullable String propertyKey);
    public abstract Builder setSubPropertyKey(@Nullable String subPropertyKey);
    public abstract Builder setToDateTime(@Nullable LocalDateTime toDateTime);
    public abstract Builder setValue(@Nullable BigDecimal value);

    public abstract PropertyLookup build();
  }
}
