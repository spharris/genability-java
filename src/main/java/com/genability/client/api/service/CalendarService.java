package com.genability.client.api.service;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.genability.client.api.GenabilityClient;
import com.genability.client.api.request.GetCalendarDatesRequest;
import com.genability.client.api.request.GetCalendarRequest;
import com.genability.client.api.request.GetCalendarsRequest;
import com.genability.client.types.Calendar;
import com.genability.client.types.CalendarEventDate;
import com.genability.client.types.Response;
import com.google.common.util.concurrent.ListenableFuture;

public class CalendarService {

  private static final TypeReference<Response<Calendar>> CALENDAR_RESPONSE_TYPEREF =
      new TypeReference<Response<Calendar>>() {};
  private static final TypeReference<Response<CalendarEventDate>> CALENDAR_DATES_RESPONSE_TYPEREF =
      new TypeReference<Response<CalendarEventDate>>() {};

  private static final String CALENDAR_URI = "/rest/public/calendars";

  private final GenabilityClient client;
      
  @Inject
  CalendarService(GenabilityClient client) {
    this.client = client;
  }
  
  public ListenableFuture<Response<Calendar>> getCalendar(GetCalendarRequest request) {
    checkNotNull(request.getCalendarId());

    String uri = CALENDAR_URI + "/" + request.getCalendarId();
    return client.getAsync(uri, request.getQueryParams(), CALENDAR_RESPONSE_TYPEREF);
  }

  public ListenableFuture<Response<Calendar>> getCalendars(GetCalendarsRequest request) {
    return client.getAsync(CALENDAR_URI, request.getQueryParams(), CALENDAR_RESPONSE_TYPEREF);
  }

  public ListenableFuture<Response<CalendarEventDate>> getCalendarEventDates(
      GetCalendarDatesRequest request) {
    return client.getAsync(CALENDAR_URI + "/dates", request.getQueryParams(),
      CALENDAR_DATES_RESPONSE_TYPEREF);
  }
}
