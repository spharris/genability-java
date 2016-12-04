package com.genability.client.types.generated;

import javax.annotation.Nullable;

import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_ReadingDataSummary.Builder.class)
public abstract class ReadingDataSummary {

  public abstract @Nullable DateTime getFromDateTime();
  public abstract @Nullable Integer getNumberOfReadings();
  public abstract @Nullable String getQuantityUnit();
  public abstract @Nullable DateTime getToDateTime();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_ReadingDataSummary.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setFromDateTime(@Nullable DateTime fromDateTime);
    public abstract Builder setNumberOfReadings(@Nullable Integer numberOfReadings);
    public abstract Builder setQuantityUnit(@Nullable String quantityUnit);
    public abstract Builder setToDateTime(@Nullable DateTime toDateTime);

    public abstract ReadingDataSummary build();
  }
}
