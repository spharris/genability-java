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
@JsonDeserialize(builder = AutoValue_Calendar.Builder.class)
public abstract class Calendar {

  public abstract @Nullable Long getCalendarId();
  public abstract @Nullable String getCalendarName();
  public abstract @Nullable ImmutableList<CalendarEvent> getEvents();
  public abstract @Nullable Long getLseId();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_Calendar.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setCalendarId(@Nullable Long calendarId);
    public abstract Builder setCalendarName(@Nullable String calendarName);
    public abstract Builder setLseId(@Nullable Long lseId);

    @JsonIgnore
    public abstract Builder setEvents(@Nullable CalendarEvent... events);

    @JsonProperty("events")
    public abstract Builder setEvents(@Nullable ImmutableList<CalendarEvent> events);

    protected abstract ImmutableList<CalendarEvent> getEvents();
    protected abstract Calendar autoBuild();

    public Calendar build() {
      setEvents(firstNonNull(getEvents(), ImmutableList.of()));
      return autoBuild();
    }
  }
}
