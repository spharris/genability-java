package com.genability.client.types.generated;

import static com.google.common.base.MoreObjects.firstNonNull;

import javax.annotation.Nullable;

import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableMap;

@AutoValue
@JsonDeserialize(builder = AutoValue_IntervalInfo.Builder.class)
public abstract class IntervalInfo {

  public abstract @Nullable Long getDuration();
  public abstract @Nullable DateTime getFromDateTime();
  public abstract @Nullable String getId();
  public abstract @Nullable Measure getKW();
  public abstract @Nullable Measure getKWh();
  public abstract @Nullable ImmutableMap<String, Measure> getMeasures();
  public abstract @Nullable String getProfileId();
  public abstract @Nullable DateTime getToDateTime();
  public abstract @Nullable Measure getTotal();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_IntervalInfo.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setDuration(@Nullable Long duration);
    public abstract Builder setFromDateTime(@Nullable DateTime fromDateTime);
    public abstract Builder setId(@Nullable String id);
    public abstract Builder setKW(@Nullable Measure kW);
    public abstract Builder setKWh(@Nullable Measure kWh);
    public abstract Builder setMeasures(@Nullable ImmutableMap<String, Measure> measures);
    public abstract Builder setProfileId(@Nullable String profileId);
    public abstract Builder setToDateTime(@Nullable DateTime toDateTime);
    public abstract Builder setTotal(@Nullable Measure total);

    protected abstract ImmutableMap<String, Measure> getMeasures();
    protected abstract IntervalInfo autoBuild();

    public IntervalInfo build() {
      setMeasures(firstNonNull(getMeasures(), ImmutableMap.of()));
      return autoBuild();
    }
  }
}
