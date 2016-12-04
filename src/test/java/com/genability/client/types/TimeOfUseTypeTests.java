package com.genability.client.types;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

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
public class TimeOfUseTypeTests {
  
  @Inject private ObjectMapper mapper = new ObjectMapper();
  
  @Before
  public void createInjector() {
    Guice.createInjector(new TestClientModule()).injectMembers(this);
  }
  
  @Test
  public void testTouTypeSerialize() throws JsonProcessingException {
    TimeOfUseType np = TimeOfUseType.ON_PEAK;
    assertEquals("Serialized TimeOfUseType to the wrong format", "\"ON_PEAK\"",
        mapper.writeValueAsString(np));
  }

  @Test
  public void testValidJsonDeserialize() throws JsonProcessingException, IOException {
    String input = "\"ON_PEAK\"";
    assertEquals("Deserialized TimeOfUseType to the wrong format", TimeOfUseType.ON_PEAK,
        mapper.readValue(input, TimeOfUseType.class));
  }

  @Test(expected = JsonProcessingException.class)
  public void testInvalidJsonDeserialize() throws IOException {
    mapper.readValue("Invalid", TimeOfUseType.class);
  }
}
