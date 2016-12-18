package com.genability.client.types;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_PropertyLookupStats.Builder.class)
public abstract class PropertyLookupStats {

  public abstract @Nullable String getKeyName();
  public abstract @Nullable ZonedDateTime getLastUpdatedDate();
  public abstract @Nullable Integer getLookupCount();
  public abstract @Nullable ZonedDateTime getMaxToDateTime();
  public abstract @Nullable Long getMeanDuration();
  public abstract @Nullable BigDecimal getMeanValue();
  public abstract @Nullable ZonedDateTime getMinFromDateTime();
  public abstract @Nullable Long getMissingDuration();
  public abstract @Nullable Long getTotalDuration();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_PropertyLookupStats.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setKeyName(@Nullable String keyName);
    public abstract Builder setLastUpdatedDate(@Nullable ZonedDateTime lastUpdatedDate);
    public abstract Builder setLookupCount(@Nullable Integer lookupCount);
    public abstract Builder setMaxToDateTime(@Nullable ZonedDateTime maxToDateTime);
    public abstract Builder setMeanDuration(@Nullable Long meanDuration);
    public abstract Builder setMeanValue(@Nullable BigDecimal meanValue);
    public abstract Builder setMinFromDateTime(@Nullable ZonedDateTime minFromDateTime);
    public abstract Builder setMissingDuration(@Nullable Long missingDuration);
    public abstract Builder setTotalDuration(@Nullable Long totalDuration);

    public abstract PropertyLookupStats build();
  }
}
