package com.genability.client.api.request;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.genability.client.util.EnumUtil;
import com.google.common.collect.ImmutableList;

final class QueryParamsBuilder {

  private ImmutableList.Builder<NameValuePair> builder;
  
  QueryParamsBuilder() {
    builder = ImmutableList.builder();
  }
  
  ImmutableList<NameValuePair> build() {
    return builder.build();
  }
  
  QueryParamsBuilder addParam(String paramName, Object value) {
    if (value != null) {
      builder.add(new BasicNameValuePair(paramName, value.toString()));
    }
    
    return this;
  }

  QueryParamsBuilder addParam(String paramName, String[] paramValues) {
    if (paramValues != null) {
      for (String paramValue : paramValues)
        builder.add(new BasicNameValuePair(paramName, paramValue));
    }
    
    return this;
  }

  <E extends Enum<E>> QueryParamsBuilder addParam(String paramName, E[] paramValues) {
    if (paramValues != null) {
      builder.add(new BasicNameValuePair(paramName, EnumUtil.enumListString(paramValues)));
    }
    
    return this;
  }

  <E extends Enum<E>> QueryParamsBuilder addParam(String paramName, E paramValue) {
    if (paramValue != null) {
      builder.add(new BasicNameValuePair(paramName, paramValue.name()));
    }
    
    return this;
  }
  
  QueryParamsBuilder addParam(String paramName, Iterable<?> paramValues) {
    if (paramValues != null) {
      for (Object value : paramValues) {
        builder.add(new BasicNameValuePair(paramName, value.toString()));
      }
    }
    
    return this;
  }
}
