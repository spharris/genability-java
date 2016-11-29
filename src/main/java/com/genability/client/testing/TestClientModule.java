package com.genability.client.testing;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.Executors;

import com.genability.client.api.GenabilityClientModule;
import com.google.common.io.CharSource;
import com.google.common.io.Resources;
import com.google.inject.AbstractModule;

public class TestClientModule extends AbstractModule {

  @Override
  protected void configure() {
    Properties creds = getApiCreds();
    
    install(GenabilityClientModule.builder(creds.getProperty("appId"), creds.getProperty("appKey"))
      .setExecutor(Executors.newSingleThreadExecutor())
      .build());
  }
  
  private static Properties getApiCreds() {
    CharSource propsFile = Resources.asCharSource(
      Resources.getResource("genability.properties"), StandardCharsets.UTF_8);
    
    try (Reader reader = propsFile.openStream()) {
      Properties properties = new Properties();
      properties.load(reader);
      return properties;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
