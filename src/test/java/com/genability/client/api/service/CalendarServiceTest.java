package com.genability.client.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.genability.client.api.request.GetCalendarDatesRequest;
import com.genability.client.api.request.GetCalendarRequest;
import com.genability.client.api.request.GetCalendarsRequest;
import com.genability.client.types.Calendar;
import com.genability.client.types.CalendarEventDate;
import com.genability.client.types.Response;

public class CalendarServiceTest extends BaseServiceTest {


  private static CalendarService calendarService = genabilityClient.getCalendarService();


  @Test
  public void testGetCalendar() {

    //
    // Assign
    //
    GetCalendarRequest request = GetCalendarRequest.builder()
        .setCalendarId(2L)
        .build();

    //
    // Act
    //
    Response<Calendar> restResponse = calendarService.getCalendar(request);

    //
    // Assert
    //
    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);

  }

  @Test
  public void testGetCalendars() {

    //
    // Assign
    //
    GetCalendarsRequest request = GetCalendarsRequest.builder()
        .setLseId(new Long(734)) // Pacific Gas & Electric
        .build();

    //
    // Act
    //
    Response<Calendar> restResponse = calendarService.getCalendars(request);

    //
    // Assert
    //
    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);

  }

  @Test
  public void testGetCalendarDates() {

    //
    // Assign
    //
    GetCalendarDatesRequest request = GetCalendarDatesRequest.builder()
        .setLseId(new Long(734)) // Pacific Gas & Electric
        .build();

    //
    // Act
    //
    Response<CalendarEventDate> restResponse = calendarService.getCalendarEventDates(request);

    //
    // Assert
    //
    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);

  }
}
