package com.genability.client.types;

import static com.google.common.base.MoreObjects.firstNonNull;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
@JsonDeserialize(builder = AutoValue_TimeOfUse.Builder.class)
public abstract class TimeOfUse {

  public abstract @Nullable Integer getCalendarId();
  public abstract @Nullable Boolean getIsDynamic();
  public abstract @Nullable Long getLseId();
  public abstract @Nullable Season getSeason();
  public abstract @Nullable Integer getTouGroupId();
  public abstract @Nullable Long getTouId();
  public abstract @Nullable String getTouName();
  public abstract @Nullable ImmutableList<TimeOfUsePeriod> getTouPeriods();
  public abstract @Nullable String getTouType();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_TimeOfUse.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setCalendarId(@Nullable Integer calendarId);
    public abstract Builder setIsDynamic(@Nullable Boolean isDynamic);
    public abstract Builder setLseId(@Nullable Long lseId);
    public abstract Builder setSeason(@Nullable Season season);
    public abstract Builder setTouGroupId(@Nullable Integer touGroupId);
    public abstract Builder setTouId(@Nullable Long touId);
    public abstract Builder setTouName(@Nullable String touName);
    public abstract Builder setTouType(@Nullable String touType);

    @JsonIgnore
    public abstract Builder setTouPeriods(@Nullable TimeOfUsePeriod... touPeriods);

    @JsonProperty("touPeriods")
    public abstract Builder setTouPeriods(@Nullable ImmutableList<TimeOfUsePeriod> touPeriods);

    protected abstract ImmutableList<TimeOfUsePeriod> getTouPeriods();
    protected abstract TimeOfUse autoBuild();

    public TimeOfUse build() {
      setTouPeriods(firstNonNull(getTouPeriods(), ImmutableList.of()));
      return autoBuild();
    }
  }
}
