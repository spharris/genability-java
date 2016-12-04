package com.genability.client.types;

import javax.annotation.Nullable;

import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_TimeOfUseInterval.Builder.class)
public abstract class TimeOfUseInterval {

  public abstract @Nullable Long getCalendarId();
  public abstract @Nullable DateTime getFromDateTime();
  public abstract @Nullable DateTime getToDateTime();
  public abstract @Nullable Long getTouGroupId();
  public abstract @Nullable Long getTouId();
  public abstract @Nullable String getTouName();
  public abstract @Nullable TimeOfUseType getTouType();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_TimeOfUseInterval.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setCalendarId(@Nullable Long calendarId);
    public abstract Builder setFromDateTime(@Nullable DateTime fromDateTime);
    public abstract Builder setToDateTime(@Nullable DateTime toDateTime);
    public abstract Builder setTouGroupId(@Nullable Long touGroupId);
    public abstract Builder setTouId(@Nullable Long touId);
    public abstract Builder setTouName(@Nullable String touName);
    public abstract Builder setTouType(@Nullable TimeOfUseType touType);

    public abstract TimeOfUseInterval build();
  }
}
