package com.genability.client.api.request;

import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.Fields;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class GetTimeOfUseIntervalsRequest extends AbstractGetNRequest {

  GetTimeOfUseIntervalsRequest() {}

  public abstract @Nullable ZonedDateTime getFromDateTime();
  public abstract @Nullable ZonedDateTime getToDateTime();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_GetTimeOfUseIntervalsRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractGetNRequest.Builder<Builder> {

    public abstract Builder setFromDateTime(@Nullable ZonedDateTime fromDateTime);
    public abstract Builder setToDateTime(@Nullable ZonedDateTime toDateTime);

    public abstract GetTimeOfUseIntervalsRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("fromDateTime", getFromDateTime())
        .addParam("toDateTime", getToDateTime())
        .build();
  }
}
