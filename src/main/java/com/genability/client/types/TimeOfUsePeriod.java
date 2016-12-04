package com.genability.client.types;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_TimeOfUsePeriod.Builder.class)
public abstract class TimeOfUsePeriod {

  public abstract @Nullable Integer getFromDayOfWeek();
  public abstract @Nullable Integer getFromHour();
  public abstract @Nullable Integer getFromMinute();
  public abstract @Nullable Integer getToDayOfWeek();
  public abstract @Nullable Integer getToHour();
  public abstract @Nullable Integer getToMinute();
  public abstract @Nullable Integer getTouId();
  public abstract @Nullable Integer getTouPeriodId();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_TimeOfUsePeriod.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setFromDayOfWeek(@Nullable Integer fromDayOfWeek);
    public abstract Builder setFromHour(@Nullable Integer fromHour);
    public abstract Builder setFromMinute(@Nullable Integer fromMinute);
    public abstract Builder setToDayOfWeek(@Nullable Integer toDayOfWeek);
    public abstract Builder setToHour(@Nullable Integer toHour);
    public abstract Builder setToMinute(@Nullable Integer toMinute);
    public abstract Builder setTouId(@Nullable Integer touId);
    public abstract Builder setTouPeriodId(@Nullable Integer touPeriodId);

    public abstract TimeOfUsePeriod build();
  }
}
