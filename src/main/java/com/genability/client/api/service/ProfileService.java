package com.genability.client.api.service;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import javax.inject.Inject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.genability.client.api.GenabilityClient;
import com.genability.client.api.request.DeleteProfileRequest;
import com.genability.client.api.request.GetProfileRequest;
import com.genability.client.api.request.GetProfilesRequest;
import com.genability.client.api.request.ReadingDataRequest;
import com.genability.client.types.Profile;
import com.genability.client.types.ReadingData;
import com.genability.client.types.Response;
import com.google.common.util.concurrent.ListenableFuture;

public class ProfileService extends BaseService {

  private static final TypeReference<Response<Profile>> PROFILE_RESPONSE_TYPEREF =
      new TypeReference<Response<Profile>>() {};
  private static final TypeReference<Response<ReadingData>> READING_DATA_RESPONSE_TYPEREF =
      new TypeReference<Response<ReadingData>>() {};

  private final GenabilityClient client;
      
  @Inject
  ProfileService(GenabilityClient client) {
    this.client = client;
  }
      
  /**
   * Calls the REST service to get a Profile based on the arguments passed in.
   * 
   * @param request The request.
   * @return The return value.
   */
  public ListenableFuture<Response<Profile>> getProfile(GetProfileRequest request) {
    checkArgument(request.getProfileId() != null || request.getProviderProfileId() != null, "Either"
        + " profileId or providerProfileId must be provided");
    checkArgument(request.getProfileId() == null || request.getProviderProfileId() == null, "Only"
        + " one of profileId and providerProfileId may be provided");

    // Set uri based on if providerProfileId was used
    String uri = "/rest/v1/profiles/";
    if (request.getProviderProfileId() != null) {
      uri += "pid/";
    }

    uri += Optional.ofNullable(request.getProfileId()).orElse(request.getProviderProfileId());
    return client.getAsync(uri, request.getQueryParams(), PROFILE_RESPONSE_TYPEREF);
  }

  /**
   * Calls the REST service to get Profiles based on the arguments passed in.
   * 
   * @param request The request.
   * @return The return value.
   */
  public ListenableFuture<Response<Profile>> getProfiles(GetProfilesRequest request) {
    String uri = "/rest/v1/profiles";
    return client.getAsync(uri, request.getQueryParams(), PROFILE_RESPONSE_TYPEREF);
  }

  public ListenableFuture<Response<Profile>> addProfile(Profile profile) {
    String uri = "/rest/v1/profiles";
    return client.postAsync(uri, profile, PROFILE_RESPONSE_TYPEREF);
  }

  public ListenableFuture<Response<Profile>> updateProfile(Profile profile) {
    String uri = "/rest/v1/profiles";
    return client.putAsync(uri, profile, PROFILE_RESPONSE_TYPEREF);
  }

  /**
   * Calls the REST service to delete a Profile based on the arguments passed in.
   * 
   * @param request The request.
   * @return The return value.
   */
  public ListenableFuture<Response<Profile>> deleteProfile(DeleteProfileRequest request) {
    checkNotNull(request.getProfileId(), "profileId must be set");

    String uri = String.format("/rest/v1/profiles/%s", request.getProfileId());
    return client.deleteAsync(uri, request.getQueryParams(), PROFILE_RESPONSE_TYPEREF);
  }

  public ListenableFuture<Response<ReadingData>> updateReadings(ReadingDataRequest request) {
    checkNotNull(request.getUsageProfileId(), "usageProfileId must be set");

    String uri = String.format("/rest/v1/profiles/%s/readings", request.getUsageProfileId());
    return client.putAsync(uri, request, READING_DATA_RESPONSE_TYPEREF);
  }

  public ListenableFuture<Response<ReadingData>> addReadings(ReadingDataRequest request) {
    checkNotNull(request.getUsageProfileId(), "usageProfileId must be set");

    String uri = String.format("/rest/v1/profiles/%s/readings", request.getUsageProfileId());
    return client.postAsync(uri, request, READING_DATA_RESPONSE_TYPEREF);
  }
}
