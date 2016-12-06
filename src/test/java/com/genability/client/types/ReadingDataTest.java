package com.genability.client.types;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genability.client.testing.TestClientModule;
import com.google.inject.Guice;

@RunWith(JUnit4.class)
public class ReadingDataTest {
  
  @Inject private ObjectMapper mapper = new ObjectMapper();
  
  @Before
  public void createInjector() {
    Guice.createInjector(new TestClientModule()).injectMembers(this);
  }

  @Test
  public void testSerializeTouType() throws JsonProcessingException {
    String target = "{\"touType\":\"ON_PEAK\"}";
    ReadingData rd = ReadingData.builder()
        .setTouType(TimeOfUseType.ON_PEAK)
        .build();

    assertEquals("Didn't correctly serialize touType field", target, mapper.writeValueAsString(rd));
  }
}
