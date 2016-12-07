package com.genability.client.api.service;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.genability.client.api.GenabilityClient;
import com.genability.client.api.request.GetTimeOfUseGroupsRequest;
import com.genability.client.api.request.GetTimeOfUseIntervalsRequest;
import com.genability.client.types.Response;
import com.genability.client.types.TimeOfUseGroup;
import com.genability.client.types.TimeOfUseInterval;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ListenableFuture;

public class TimeOfUseService {

  private static final TypeReference<Response<TimeOfUseGroup>> TOU_GROUP_RESPONSE_TYPEREF =
      new TypeReference<Response<TimeOfUseGroup>>() {};

  private static final TypeReference<Response<TimeOfUseInterval>> TOU_INTERVAL_RESPONSE_TYPEREF =
      new TypeReference<Response<TimeOfUseInterval>>() {};

  private static final String PUBLIC_URL_BASE = "/rest/public/timeofuses";
  private static final String PRIVATE_URL_BASE = "/rest/timeofuses";

  private final GenabilityClient client;
      
  @Inject
  TimeOfUseService(GenabilityClient client) {
    this.client = client;
  }
  
  public ListenableFuture<Response<TimeOfUseGroup>> getTimeOfUseGroup(long lseId, long touGroupId) {
    String url = String.format("%s/%d/%d", PUBLIC_URL_BASE, lseId, touGroupId);
    return client.getAsync(url, ImmutableList.of(), TOU_GROUP_RESPONSE_TYPEREF);
  }

  /*
   * Get a list of TimeOfUseGroups matching the request parameters.
   */
  public ListenableFuture<Response<TimeOfUseGroup>> getTimeOfUseGroups(
      GetTimeOfUseGroupsRequest request) {
    return client.getAsync(PUBLIC_URL_BASE, request.getQueryParams(), TOU_GROUP_RESPONSE_TYPEREF);
  }

  /*
   * Get a list of intervals for the specified timeOfUseGroup.
   */
  public ListenableFuture<Response<TimeOfUseInterval>> getTimeOfUseIntervals(long lseId,
      long touGroupId,
      GetTimeOfUseIntervalsRequest request) {
    String url = String.format("%s/%d/%d/intervals", PUBLIC_URL_BASE, lseId, touGroupId);
    return client.getAsync(url, request.getQueryParams(), TOU_INTERVAL_RESPONSE_TYPEREF);
  }

  /**
   * Creates a private TimeOfUseGroup out of the passed-in TOU group.
   * 
   * @param touGroup The touGroup.
   * @return The added TOU group that is sent back by the API.
   */
  public ListenableFuture<Response<TimeOfUseGroup>> addPrivateTimeOfUseGroup(
      TimeOfUseGroup touGroup) {
    checkNotNull(touGroup);

    return client.postAsync(PRIVATE_URL_BASE, touGroup, TOU_GROUP_RESPONSE_TYPEREF);
  }

  /**
   * Updates the passed-in private TimeOfUseGroup. You can only update a TOU group that belongs to
   * your organization.
   * 
   * @param touGroup The touGroup.
   * @return The added TOU group that is sent back by the API.
   */
  public ListenableFuture<Response<TimeOfUseGroup>> updatePrivateTimeOfUseGroup(
      TimeOfUseGroup touGroup) {
    checkNotNull(touGroup);

    return client.putAsync(PRIVATE_URL_BASE, touGroup, TOU_GROUP_RESPONSE_TYPEREF);
  }

  /**
   * Deletes a private TOU group. You can only delete a TOU group that belongs to your organization.
   * 
   * @param lseId The lseId.
   * @param touGroupId The touGroupId.
   * @return The deleted TOU group, which is sent back by the API.
   */
  public ListenableFuture<Response<TimeOfUseGroup>> deletePrivateTimeOfUseGroup(long lseId,
      long touGroupId) {
    String url = String.format("%s/%d/%d", PRIVATE_URL_BASE, lseId, touGroupId);
    return client.deleteAsync(url, null, TOU_GROUP_RESPONSE_TYPEREF);
  }
}
