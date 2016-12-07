package com.genability.client.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.genability.client.api.request.GetCalendarDatesRequest;
import com.genability.client.api.request.GetCalendarRequest;
import com.genability.client.api.request.GetCalendarsRequest;
import com.genability.client.testing.TestClientModule;
import com.genability.client.types.Calendar;
import com.genability.client.types.CalendarEventDate;
import com.genability.client.types.Response;
import com.google.inject.Guice;

@RunWith(JUnit4.class)
public class CalendarServiceTest {

  @Inject private CalendarService calendarService;
     
  @Before
  public void createInjector() {
    Guice.createInjector(new TestClientModule()).injectMembers(this);
  }

  @Test
  public void testGetCalendar() throws Exception {

    //
    // Assign
    //
    GetCalendarRequest request = GetCalendarRequest.builder()
        .setCalendarId(2L)
        .build();

    //
    // Act
    //
    Response<Calendar> restResponse = calendarService.getCalendar(request).get();

    //
    // Assert
    //
    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);

  }

  @Test
  public void testGetCalendars() throws Exception {

    //
    // Assign
    //
    GetCalendarsRequest request = GetCalendarsRequest.builder()
        .setLseId(new Long(734)) // Pacific Gas & Electric
        .build();

    //
    // Act
    //
    Response<Calendar> restResponse = calendarService.getCalendars(request).get();

    //
    // Assert
    //
    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);

  }

  @Test
  public void testGetCalendarDates() throws Exception {

    //
    // Assign
    //
    GetCalendarDatesRequest request = GetCalendarDatesRequest.builder()
        .setLseId(new Long(734)) // Pacific Gas & Electric
        .build();

    //
    // Act
    //
    Response<CalendarEventDate> restResponse = calendarService.getCalendarEventDates(request).get();

    //
    // Assert
    //
    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);

  }
}
