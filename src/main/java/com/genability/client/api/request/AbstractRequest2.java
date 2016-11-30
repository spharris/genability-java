package com.genability.client.api.request;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.Fields;
import com.google.common.collect.ImmutableList;

abstract class AbstractRequest2 {

  public abstract @Nullable Fields getFields();
  
  public abstract static class Builder<T extends Builder<T>> {
    
    public abstract T setFields(@Nullable Fields fields);
  }
  
  public abstract ImmutableList<NameValuePair> getQueryParams();
  
  @JsonIgnore
  protected QueryParamsBuilder getBaseQueryParams() {
    return new QueryParamsBuilder()
        .addParam("fields", getFields());
  }
}
