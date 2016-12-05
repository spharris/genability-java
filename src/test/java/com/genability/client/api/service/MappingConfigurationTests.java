package com.genability.client.api.service;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genability.client.testing.TestClientModule;
import com.genability.client.types.ChargeType;
import com.google.inject.Guice;

@RunWith(JUnit4.class)
public class MappingConfigurationTests extends BaseServiceTests {

  @Inject private ObjectMapper mapper;
  
  @Before
  public void createInjector() {
    Guice.createInjector(new TestClientModule()).injectMembers(this);
  }
  
  private static class TestClass {
    private ChargeType chargeType;

    @SuppressWarnings("unused")
    public void setChargeType(ChargeType ct) {
      chargeType = ct;
    }

    public ChargeType getChargeType() {
      return chargeType;
    }
  }

  @Test
  public void objectMapperIgnoresUnknownNullValues() throws Exception {
    String data = "{\"chargeType\":\"INVALID\"}";

    TestClass tc = mapper.readValue(data, TestClass.class);

    assertEquals(tc.getChargeType(), null);
  }
}
