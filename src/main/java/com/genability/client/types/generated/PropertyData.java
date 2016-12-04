package com.genability.client.types.generated;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.genability.client.types.DataType;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_PropertyData.Builder.class)
public abstract class PropertyData {

  public abstract @Nullable BigDecimal getAccuracy();
  public abstract @Nullable BigDecimal getDataFactor();
  public abstract @Nullable DataType getDataType();
  public abstract @Nullable String getDataValue();
  public abstract @Nullable Long getDuration();
  public abstract @Nullable DateTime getFromDateTime();
  public abstract @Nullable String getKeyName();
  public abstract @Nullable String getOperator();
  public abstract @Nullable String getPeriod();
  public abstract @Nullable String getScenarios();
  public abstract @Nullable String getSource();
  public abstract @Nullable DateTime getToDateTime();
  public abstract @Nullable String getUnit();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_PropertyData.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setAccuracy(@Nullable BigDecimal accuracy);
    public abstract Builder setDataFactor(@Nullable BigDecimal dataFactor);
    public abstract Builder setDataType(@Nullable DataType dataType);
    public abstract Builder setDataValue(@Nullable String dataValue);
    public abstract Builder setDuration(@Nullable Long duration);
    public abstract Builder setFromDateTime(@Nullable DateTime fromDateTime);
    public abstract Builder setKeyName(@Nullable String keyName);
    public abstract Builder setOperator(@Nullable String operator);
    public abstract Builder setPeriod(@Nullable String period);
    public abstract Builder setScenarios(@Nullable String scenarios);
    public abstract Builder setSource(@Nullable String source);
    public abstract Builder setToDateTime(@Nullable DateTime toDateTime);
    public abstract Builder setUnit(@Nullable String unit);

    public abstract PropertyData build();
  }
}
