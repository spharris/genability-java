package com.genability.client.api.request;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.Fields;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class GetAccountsRequest extends AbstractGetNRequest {

  GetAccountsRequest() {}


  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_GetAccountsRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractGetNRequest.Builder<Builder> {


    public abstract GetAccountsRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .build();
  }
}
