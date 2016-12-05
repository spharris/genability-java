package com.genability.client.api.request;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.Fields;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class GetCalendarsRequest extends AbstractRequest {

  GetCalendarsRequest() {}

  public abstract @Nullable Long getLseId();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_GetCalendarsRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractRequest.Builder<Builder> {

    public abstract Builder setLseId(@Nullable Long lseId);

    public abstract GetCalendarsRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("lseId", getLseId())
        .build();
  }
}
