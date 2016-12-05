package com.genability.client.api.request;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.Fields;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class GetTimeOfUseGroupsRequest extends AbstractGetNRequest {

  GetTimeOfUseGroupsRequest() {}

  public abstract @Nullable Long getLseId();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_GetTimeOfUseGroupsRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractGetNRequest.Builder<Builder> {

    public abstract Builder setLseId(@Nullable Long lseId);

    public abstract GetTimeOfUseGroupsRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("lseId", getLseId())
        .build();
  }
}
