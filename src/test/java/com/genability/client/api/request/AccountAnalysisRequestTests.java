package com.genability.client.api.request;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.ZoneId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AccountAnalysisRequestTests {
  
  private ObjectMapper mapper = new ObjectMapper();

  @Before
  public void registerJodaModule() {
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.setSerializationInclusion(Include.NON_NULL);
  }


  @Test
  public void testFromDateNoTimezone() throws JsonProcessingException {
    AccountAnalysisRequest request = AccountAnalysisRequest.builder()
        .setFromDateTime(2015, 1, 1)
        .build();
    String target = "{\"fields\":\"ext\",\"fromDateTime\":\"2015-01-01\"}";

    assertEquals("Didn't serialize fromDateTime correctly with just a date", target,
        mapper.writeValueAsString(request));
  }

  @Test
  public void testToDateNoTimezone() throws JsonProcessingException {
    AccountAnalysisRequest request = AccountAnalysisRequest.builder()
        .setToDateTime(2015, 1, 1)
        .build();
    String target = "{\"fields\":\"ext\",\"toDateTime\":\"2015-01-01\"}";

    assertEquals("Didn't serialize toDateTime correctly with just a date", target,
        mapper.writeValueAsString(request));
  }

  @Test
  public void testFromLocalDate() throws JsonProcessingException {
    AccountAnalysisRequest request = AccountAnalysisRequest.builder()
        .setFromDateTime(LocalDate.of(2015, 1, 1))
        .build();
    String target = "{\"fields\":\"ext\",\"fromDateTime\":\"2015-01-01\"}";

    assertEquals("Didn't serialize fromDateTime correctly with just a date", target,
        mapper.writeValueAsString(request));
  }

  @Test
  public void testToLocalDate() throws JsonProcessingException {
    AccountAnalysisRequest request = AccountAnalysisRequest.builder()
        .setToDateTime(LocalDate.of(2015, 1, 1))
        .build();
    String target = "{\"fields\":\"ext\",\"toDateTime\":\"2015-01-01\"}";

    assertEquals("Didn't serialize toDateTime correctly with just a date", target,
        mapper.writeValueAsString(request));
  }

  @Test
  public void testFromDateWithTimezone() throws JsonProcessingException {
    AccountAnalysisRequest request = AccountAnalysisRequest.builder()
        .setFromDateTime(LocalDate.of(2015,  1,  1).atStartOfDay(ZoneId.of("US/Pacific")))
        .build();
    String target = "{\"fields\":\"ext\",\"fromDateTime\":\"2015-01-01T08:00:00.000Z\"}";

    assertEquals("Didn't serialize fromDateTime correctly with a datetime", target,
        mapper.writeValueAsString(request));
  }

  @Test
  public void testToDateWithTimezone() throws JsonProcessingException {
    AccountAnalysisRequest request = AccountAnalysisRequest.builder()
        .setFromDateTime(LocalDate.of(2015,  1,  1).atStartOfDay(ZoneId.of("US/Pacific")))
        .build();
    String target = "{\"fields\":\"ext\",\"toDateTime\":\"2015-01-01T08:00:00.000Z\"}";

    assertEquals("Didn't serialize toDateTime correctly with a datetime", target,
        mapper.writeValueAsString(request));
  }
}
