package com.genability.client.types;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_ReadingData.Builder.class)
public abstract class ReadingData {

  public abstract @Nullable String getDataValue();
  public abstract @Nullable Long getEndTime();
  public abstract @Nullable ZonedDateTime getFromDateTime();
  public abstract @Nullable String getKeyName();
  public abstract @Nullable String getPeriod();
  public abstract @Nullable BigDecimal getQuantityCost();
  public abstract @Nullable BigDecimal getQuantityRate();
  public abstract @Nullable String getQuantityUnit();
  public abstract @Nullable BigDecimal getQuantityValue();
  public abstract @Nullable String getSource();
  public abstract @Nullable Long getStartTime();
  public abstract @Nullable Integer getTimeAccuracy();
  public abstract @Nullable ZonedDateTime getToDateTime();
  public abstract @Nullable TimeOfUseType getTouType();
  public abstract @Nullable String getUnit();
  public abstract @Nullable String getUsageProfileId();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_ReadingData.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setDataValue(@Nullable String dataValue);
    public abstract Builder setEndTime(@Nullable Long endTime);
    public abstract Builder setFromDateTime(@Nullable ZonedDateTime fromDateTime);
    public abstract Builder setKeyName(@Nullable String keyName);
    public abstract Builder setPeriod(@Nullable String period);
    public abstract Builder setQuantityCost(@Nullable BigDecimal quantityCost);
    public abstract Builder setQuantityRate(@Nullable BigDecimal quantityRate);
    public abstract Builder setQuantityUnit(@Nullable String quantityUnit);
    public abstract Builder setQuantityValue(@Nullable BigDecimal quantityValue);
    public abstract Builder setSource(@Nullable String source);
    public abstract Builder setStartTime(@Nullable Long startTime);
    public abstract Builder setTimeAccuracy(@Nullable Integer timeAccuracy);
    public abstract Builder setToDateTime(@Nullable ZonedDateTime toDateTime);
    public abstract Builder setTouType(@Nullable TimeOfUseType touType);
    public abstract Builder setUnit(@Nullable String unit);
    public abstract Builder setUsageProfileId(@Nullable String usageProfileId);

    public abstract ReadingData build();
  }
}
