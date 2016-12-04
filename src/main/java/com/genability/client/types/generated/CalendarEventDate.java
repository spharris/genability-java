package com.genability.client.types.generated;

import javax.annotation.Nullable;

import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_CalendarEventDate.Builder.class)
public abstract class CalendarEventDate {

  public abstract @Nullable Long getCalendarEventId();
  public abstract @Nullable DateTime getEndDateTime();
  public abstract @Nullable Long getEventDateId();
  public abstract @Nullable String getEventName();
  public abstract @Nullable Long getLseId();
  public abstract @Nullable DateTime getStartDateTime();
  public abstract @Nullable String getSubKey();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_CalendarEventDate.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setCalendarEventId(@Nullable Long calendarEventId);
    public abstract Builder setEndDateTime(@Nullable DateTime endDateTime);
    public abstract Builder setEventDateId(@Nullable Long eventDateId);
    public abstract Builder setEventName(@Nullable String eventName);
    public abstract Builder setLseId(@Nullable Long lseId);
    public abstract Builder setStartDateTime(@Nullable DateTime startDateTime);
    public abstract Builder setSubKey(@Nullable String subKey);

    public abstract CalendarEventDate build();
  }
}
