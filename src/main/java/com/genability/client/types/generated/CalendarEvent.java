package com.genability.client.types.generated;

import javax.annotation.Nullable;

import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.genability.client.types.CalendarEventType;
import com.genability.client.types.DateDefinitionType;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_CalendarEvent.Builder.class)
public abstract class CalendarEvent {

  public abstract @Nullable Integer getAdjustment();
  public abstract @Nullable Long getCalendarEventId();
  public abstract @Nullable String getCalendarEventName();
  public abstract @Nullable CalendarEventType getCalendarEventType();
  public abstract @Nullable DateDefinitionType getDateDefinitionType();
  public abstract @Nullable Integer getDayOfWeek();
  public abstract @Nullable Integer getFixedDay();
  public abstract @Nullable Integer getFixedMonth();
  public abstract @Nullable String getLocale();
  public abstract @Nullable Long getLseId();
  public abstract @Nullable DateTime getSeededUntil();
  public abstract @Nullable Integer getWeekOfMonth();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_CalendarEvent.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setAdjustment(@Nullable Integer adjustment);
    public abstract Builder setCalendarEventId(@Nullable Long calendarEventId);
    public abstract Builder setCalendarEventName(@Nullable String calendarEventName);
    public abstract Builder setCalendarEventType(@Nullable CalendarEventType calendarEventType);
    public abstract Builder setDateDefinitionType(@Nullable DateDefinitionType dateDefinitionType);
    public abstract Builder setDayOfWeek(@Nullable Integer dayOfWeek);
    public abstract Builder setFixedDay(@Nullable Integer fixedDay);
    public abstract Builder setFixedMonth(@Nullable Integer fixedMonth);
    public abstract Builder setLocale(@Nullable String locale);
    public abstract Builder setLseId(@Nullable Long lseId);
    public abstract Builder setSeededUntil(@Nullable DateTime seededUntil);
    public abstract Builder setWeekOfMonth(@Nullable Integer weekOfMonth);

    public abstract CalendarEvent build();
  }
}
