package com.genability.client.api;

import static com.google.common.truth.Truth.assertThat;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.fasterxml.jackson.core.type.TypeReference;
import com.genability.client.testing.TestClientModule;
import com.genability.client.types.Response;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Guice;

@RunWith(JUnit4.class)
public class GenabilityClientTest {
  
  @Inject GenabilityClient client;
  
  @Before
  public void createInjector() {
    Guice.createInjector(new TestClientModule()).injectMembers(this);
  }
  
  @Test
  public void getEcho() {
    Response<String> response = client.get("/rest/echo", ImmutableList.of(),
      new TypeReference<Response<String>>() {});
    
    assertThat(response.getResults()).containsExactly("Echo");
  }
  
  @Test
  public void getEchoAsync() throws Exception {
    ListenableFuture<Response<String>> response = client.getAsync("/rest/echo", ImmutableList.of(),
      new TypeReference<Response<String>>() {});
    
    assertThat(response.get().getResults()).containsExactly("Echo");
  }
}
