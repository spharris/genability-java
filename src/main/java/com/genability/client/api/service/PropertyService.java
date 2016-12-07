package com.genability.client.api.service;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.genability.client.api.GenabilityClient;
import com.genability.client.api.request.GetPropertyKeyRequest;
import com.genability.client.api.request.GetPropertyKeysRequest;
import com.genability.client.api.request.GetPropertyLookupsRequest;
import com.genability.client.types.PropertyKey;
import com.genability.client.types.PropertyLookup;
import com.genability.client.types.PropertyLookupStats;
import com.genability.client.types.Response;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ListenableFuture;

public class PropertyService {

  private static final TypeReference<Response<PropertyKey>> PROPERTY_RESPONSE_TYPEREF =
      new TypeReference<Response<PropertyKey>>() {};

  private static final TypeReference<Response<PropertyLookup>> PROPERTY_LOOKUP_RESPONSE_TYPEREF =
      new TypeReference<Response<PropertyLookup>>() {};

  private static final TypeReference<Response<PropertyLookupStats>> PROPERTY_LOOKUP_STATS_RESPONSE_TYPEREF =
      new TypeReference<Response<PropertyLookupStats>>() {};

  private final GenabilityClient client;
      
  @Inject
  PropertyService(GenabilityClient client) {
    this.client = client;
  }

  public ListenableFuture<Response<PropertyKey>> getPropertyKey(GetPropertyKeyRequest request) {
    checkNotNull(request.getKeyName());

    String uri = "/rest/public/properties/" + request.getKeyName();
    return client.getAsync(uri, request.getQueryParams(), PROPERTY_RESPONSE_TYPEREF);
  }

  public ListenableFuture<Response<PropertyKey>> getPropertyKeys(GetPropertyKeysRequest request) {
    String uri = "/rest/public/properties";
    return client.getAsync(uri, request.getQueryParams(), PROPERTY_RESPONSE_TYPEREF);
  }

  public ListenableFuture<Response<PropertyLookup>> getPropertyLookups(GetPropertyLookupsRequest request) {
    String uri = "/rest/public/properties/lookups";
    return client.getAsync(uri, request.getQueryParams(), PROPERTY_LOOKUP_RESPONSE_TYPEREF);
  }

  public ListenableFuture<Response<PropertyLookupStats>> getPropertyStats(String propertyKeyName) {
    checkNotNull(propertyKeyName);

    String uri = "/rest/public/properties/" + propertyKeyName + "/stats";
    return client.getAsync(uri, ImmutableList.of(), PROPERTY_LOOKUP_STATS_RESPONSE_TYPEREF);
  }
}
