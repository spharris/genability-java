package com.genability.client.api.request;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.Fields;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class GetCalendarRequest extends AbstractRequest {

  GetCalendarRequest() {}

  public abstract @Nullable Long getCalendarId();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_GetCalendarRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractRequest.Builder<Builder> {

    public abstract Builder setCalendarId(@Nullable Long calendarId);

    public abstract GetCalendarRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .build();
  }
}
