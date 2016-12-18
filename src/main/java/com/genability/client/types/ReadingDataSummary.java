package com.genability.client.types;

import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_ReadingDataSummary.Builder.class)
public abstract class ReadingDataSummary {

  public abstract @Nullable ZonedDateTime getFromDateTime();
  public abstract @Nullable Integer getNumberOfReadings();
  public abstract @Nullable String getQuantityUnit();
  public abstract @Nullable ZonedDateTime getToDateTime();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_ReadingDataSummary.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setFromDateTime(@Nullable ZonedDateTime fromDateTime);
    public abstract Builder setNumberOfReadings(@Nullable Integer numberOfReadings);
    public abstract Builder setQuantityUnit(@Nullable String quantityUnit);
    public abstract Builder setToDateTime(@Nullable ZonedDateTime toDateTime);

    public abstract ReadingDataSummary build();
  }
}
