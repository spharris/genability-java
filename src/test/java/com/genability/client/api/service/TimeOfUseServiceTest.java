package com.genability.client.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.genability.client.api.request.GetTimeOfUseGroupsRequest;
import com.genability.client.api.request.GetTimeOfUseIntervalsRequest;
import com.genability.client.testing.TestClientModule;
import com.genability.client.types.Response;
import com.genability.client.types.TimeOfUseGroup;
import com.genability.client.types.TimeOfUseInterval;
import com.google.inject.Guice;

public class TimeOfUseServiceTest {

  @Inject private TimeOfUseService touService;
  
  @Before
  public void createInjector() {
    Guice.createInjector(new TestClientModule()).injectMembers(this);
  }
  
  @Test
  public void testGetTouGroup() throws Exception {
    long lseId = 734L;
    long touGroupId = 1L;

    Response<TimeOfUseGroup> response = touService.getTimeOfUseGroup(lseId, touGroupId).get();

    assertEquals("Didn't successfully get a response from getTimeOfUseGroup",
        Response.STATUS_SUCCESS, response.getStatus());
    assertEquals("Didn't get exactly one result", 1, response.getResults().size());
    assertEquals("Didn't get the correct touGroup", Long.valueOf(touGroupId),
        response.getResults().get(0).getTouGroupId());
    assertEquals("Didn't get the correct lseId", Long.valueOf(lseId),
        response.getResults().get(0).getLseId());
  }

  @Test
  public void testGetTouGroups() throws Exception {
    Long lseId = Long.valueOf(734);

    GetTimeOfUseGroupsRequest request = GetTimeOfUseGroupsRequest.builder()
        .setLseId(lseId)
        .build();

    Response<TimeOfUseGroup> response = touService.getTimeOfUseGroups(request).get();

    assertEquals("Didn't successfully get a response from getTimeOfUseGroups",
        Response.STATUS_SUCCESS, response.getStatus());
    assertTrue("Didn't get more than one result", response.getResults().size() > 1);
    assertEquals("Didn't get the correct lseId", Long.valueOf(lseId),
        response.getResults().get(0).getLseId());
  }

  @Test
  public void testGetTouIntervals() throws Exception {
    long lseId = 734L;
    long touGroupId = 1L;
    DateTime fromDateTime = new DateTime(2015, 1, 1, 0, 0);
    DateTime toDateTime = new DateTime(2015, 1, 4, 0, 0);

    GetTimeOfUseIntervalsRequest request = GetTimeOfUseIntervalsRequest.builder()
        .setFromDateTime(fromDateTime)
        .setToDateTime(toDateTime)
        .build();

    Response<TimeOfUseInterval> response =
        touService.getTimeOfUseIntervals(lseId, touGroupId, request).get();

    assertEquals("Didn't successfully get a response from getTimeOfUseIntervals",
        Response.STATUS_SUCCESS, response.getStatus());
    assertTrue("Didn't get more than one result", response.getResults().size() > 1);

    for (TimeOfUseInterval i : response.getResults()) {
      assertEquals("Didn't get the correct TOU group", Long.valueOf(touGroupId), i.getTouGroupId());
      assertTrue("Interval is not in the correct time period",
          i.getFromDateTime().compareTo(fromDateTime) >= 0);
      assertTrue("Interval is not in the correct time period",
          i.getToDateTime().compareTo(toDateTime) <= 0);
    }
  }
}
