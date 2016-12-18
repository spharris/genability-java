package com.genability.client.api.request;

import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.Fields;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class GetPropertyKeyRequest extends AbstractRequest {

  GetPropertyKeyRequest() {}

  public abstract @Nullable ZonedDateTime getFromDateTime();
  public abstract @Nullable String getKeyName();
  public abstract @Nullable Boolean getPopulatePropertyLookups();
  public abstract @Nullable String getSubPropertyKeyName();
  public abstract @Nullable ZonedDateTime getToDateTime();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_GetPropertyKeyRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractRequest.Builder<Builder> {

    public abstract Builder setFromDateTime(@Nullable ZonedDateTime fromDateTime);
    public abstract Builder setKeyName(@Nullable String keyName);
    public abstract Builder setPopulatePropertyLookups(@Nullable Boolean populatePropertyLookups);
    public abstract Builder setSubPropertyKeyName(@Nullable String subPropertyKeyName);
    public abstract Builder setToDateTime(@Nullable ZonedDateTime toDateTime);

    public abstract GetPropertyKeyRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("fromDateTime", getFromDateTime())
        .addParam("populatePropertyLookups", getPopulatePropertyLookups())
        .addParam("subPropertyKeyName", getSubPropertyKeyName())
        .addParam("toDateTime", getToDateTime())
        .build();
  }
}
