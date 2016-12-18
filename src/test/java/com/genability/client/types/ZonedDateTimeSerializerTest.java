package com.genability.client.types;

import static com.google.common.truth.Truth.assertThat;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genability.client.testing.TestClientModule;
import com.google.inject.Guice;

@RunWith(JUnit4.class)
public class ZonedDateTimeSerializerTest {

  @Inject private ObjectMapper mapper = new ObjectMapper();
  
  @Before
  public void createInjector() {
    Guice.createInjector(new TestClientModule()).injectMembers(this);
  }
  
  @Test
  public void serializesZonedDateTimesCorrectly() throws Exception {
    assertThat(mapper.writeValueAsString(
      ZonedDateTime.of(2015,  1, 1, 0, 0, 0, 0, ZoneId.of("America/Los_Angeles"))))
    .isEqualTo("\"2015-01-01T00:00:00-08:00\"");
  }
}
