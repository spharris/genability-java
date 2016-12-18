package com.genability.client.api.request;

import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.Fields;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class GetCalendarDatesRequest extends AbstractRequest {

  GetCalendarDatesRequest() {}

  public abstract @Nullable ImmutableList<String> getCalendarEventTypes();
  public abstract @Nullable Long getCalendarId();
  public abstract @Nullable ImmutableList<String> getDateDefinitionTypes();
  public abstract @Nullable ZonedDateTime getFromDateTime();
  public abstract @Nullable String getLocale();
  public abstract @Nullable Long getLseId();
  public abstract @Nullable ZonedDateTime getToDateTime();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_GetCalendarDatesRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractRequest.Builder<Builder> {

    public abstract Builder setCalendarEventTypes(@Nullable String... calendarEventTypes);
    public abstract Builder setCalendarEventTypes(@Nullable ImmutableList<String> calendarEventTypes);
    public abstract Builder setCalendarId(@Nullable Long calendarId);
    public abstract Builder setDateDefinitionTypes(@Nullable String... dateDefinitionTypes);
    public abstract Builder setDateDefinitionTypes(@Nullable ImmutableList<String> dateDefinitionTypes);
    public abstract Builder setFromDateTime(@Nullable ZonedDateTime fromDateTime);
    public abstract Builder setLocale(@Nullable String locale);
    public abstract Builder setLseId(@Nullable Long lseId);
    public abstract Builder setToDateTime(@Nullable ZonedDateTime toDateTime);

    public abstract GetCalendarDatesRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("calendarEventTypes", getCalendarEventTypes())
        .addParam("calendarId", getCalendarId())
        .addParam("dateDefinitionTypes", getDateDefinitionTypes())
        .addParam("fromDateTime", getFromDateTime())
        .addParam("locale", getLocale())
        .addParam("lseId", getLseId())
        .addParam("toDateTime", getToDateTime())
        .build();
  }
}
