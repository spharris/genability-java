package com.genability.client.api.request;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.Fields;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class GetPropertyLookupsRequest extends AbstractRequest {

  GetPropertyLookupsRequest() {}

  public abstract @Nullable DateTime getFromDateTime();
  public abstract @Nullable String getKeyName();
  public abstract @Nullable String getSubKeyName();
  public abstract @Nullable DateTime getToDateTime();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_GetPropertyLookupsRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractRequest.Builder<Builder> {

    public abstract Builder setFromDateTime(@Nullable DateTime fromDateTime);
    public abstract Builder setKeyName(@Nullable String keyName);
    public abstract Builder setSubKeyName(@Nullable String subKeyName);
    public abstract Builder setToDateTime(@Nullable DateTime toDateTime);

    public abstract GetPropertyLookupsRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("fromDateTime", getFromDateTime())
        .addParam("keyName", getKeyName())
        .addParam("subKeyName", getSubKeyName())
        .addParam("toDateTime", getToDateTime())
        .build();
  }
}
