package com.genability.client.types;

import static com.google.common.truth.Truth.assertThat;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genability.client.testing.TestClientModule;
import com.google.common.collect.Iterables;
import com.google.inject.Guice;

@RunWith(JUnit4.class)
public class ResponseTest {

  @Inject private ObjectMapper mapper = new ObjectMapper();
  
  @Before
  public void createInjector() {
    Guice.createInjector(new TestClientModule()).injectMembers(this);
  }
  
  @Test
  public void deserializeResponse() throws Exception {
    String data = "{\"results\":[{\"accountId\":\"12345\"}]}";
    
    Response<Account> result = mapper.readValue(data, new TypeReference<Response<Account>>() {});
    
    assertThat(Iterables.getOnlyElement(result.getResults())).isInstanceOf(Account.class);
  }
}
