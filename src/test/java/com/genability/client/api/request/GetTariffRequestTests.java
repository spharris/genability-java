package com.genability.client.api.request;

import static com.google.common.truth.Truth.assertThat;

import java.util.List;

import org.apache.http.NameValuePair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class GetTariffRequestTests {

  @Test
  public void testTerritoryIdParameter() {
    Long tid = Long.valueOf(125);
    GetTariffRequest request = GetTariffRequest.builder()
        .setTerritoryId(tid)
        .build();
    List<NameValuePair> parameters = request.getQueryParams();

    boolean foundTerritoryId = false;
    for (NameValuePair p : parameters) {
      if (p.getName().equals("territoryId")) {
        foundTerritoryId = tid.toString().equals(p.getValue());
      }
    }

    assertThat(foundTerritoryId).named("territoryId").isTrue();
  }
}
