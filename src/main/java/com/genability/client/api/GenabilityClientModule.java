package com.genability.client.api;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.genability.client.api.Annotations.AppId;
import com.genability.client.api.Annotations.AppKey;
import com.genability.client.api.Annotations.RequestCompression;
import com.genability.client.api.Annotations.ServerAddress;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.inject.AbstractModule;
import com.google.inject.Key;

public final class GenabilityClientModule extends AbstractModule {

  private static final String DEFAULT_SERVER_ADDRESS = "https://api.genability.com";
  private static final int DEFAULT_THREAD_COUNT = 5 ;
  
  private final String appId;
  private final String appKey;
  private final String serverAddress;
  private final boolean requestCompression;
  private final ObjectMapper objectMapper;
  private final HttpClient httpClient;
  private final ListeningExecutorService executor;
  
  private GenabilityClientModule(
      String appId,
      String appKey,
      String serverAddress,
      boolean requestCompression,
      ObjectMapper objectMapper,
      HttpClient httpClient,
      ListeningExecutorService executor) {
    this.appId = appId;
    this.appKey = appKey;
    this.serverAddress = serverAddress;
    this.requestCompression = requestCompression;
    this.objectMapper = objectMapper;
    this.httpClient = httpClient;
    this.executor = executor;
  }
      
  @Override
  protected void configure() {
    bind(Key.get(String.class, AppId.class)).toInstance(appId);
    bind(Key.get(String.class, AppKey.class)).toInstance(appKey);
    bind(Key.get(String.class, ServerAddress.class)).toInstance(serverAddress);
    bind(Key.get(boolean.class, RequestCompression.class)).toInstance(requestCompression);
    bind(ObjectMapper.class).toInstance(objectMapper);
    bind(HttpClient.class).toInstance(httpClient);
    bind(ListeningExecutorService.class).toInstance(executor);
  }
  
  public static Builder builder(String appId, String appKey) {
    return new Builder().setAppId(appId).setAppKey(appKey).setServerAddress(DEFAULT_SERVER_ADDRESS);
  }
  
  public final static class Builder {

    private Builder() {}
    
    private String appId;
    private String appKey;
    private String serverAddress;
    private boolean requestCompression;
    private Optional<ObjectMapper> objectMapper = Optional.empty();
    private Optional<HttpClient> httpClient = Optional.empty();
    private Optional<ExecutorService> executor = Optional.empty();
    
    public GenabilityClientModule build() {
      return new GenabilityClientModule(appId,
        appKey,
        serverAddress,
        requestCompression,
        objectMapper.orElse(defaultObjectMapper()),
        httpClient.orElse(defaultHttpClient()),
        createExecutor(executor));
    }
    
    private static ObjectMapper defaultObjectMapper() {
      return new ObjectMapper()
          .registerModule(new JodaModule())
          .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
          .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
          .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
          .setSerializationInclusion(Include.NON_NULL)
          .registerModule(new GuavaModule());
    }
    
    private static HttpClient defaultHttpClient() {
      return HttpClientBuilder.create().build();
    }
    
    private static ListeningExecutorService createExecutor(Optional<ExecutorService> executor) {
      if (executor.isPresent()) {
        return MoreExecutors.listeningDecorator(executor.get());
      } else {
        return MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(DEFAULT_THREAD_COUNT));
      }
    }
    
    public Builder setAppId(String appId) {
      this.appId = checkNotNull(appId);
      return this;
    }
    
    public Builder setAppKey(String appKey) {
      this.appKey = checkNotNull(appKey);
      return this;
    }
    
    public Builder setServerAddress(String serverAddress) {
      this.serverAddress = checkNotNull(serverAddress);
      return this;
    }
    
    public Builder setRequestCompression(boolean requestCompression) {
      this.requestCompression = requestCompression;
      return this;
    }
    
    public Builder setObjectMapper(ObjectMapper objectMapper) {
      this.objectMapper = Optional.ofNullable(objectMapper);
      return this;
    }
    
    public Builder setHttpClient(HttpClient httpClient) {
      this.httpClient = Optional.ofNullable(httpClient);
      return this;
    }
    
    public Builder setExecutor(ExecutorService executor) {
      this.executor = Optional.ofNullable(executor);
      return this;
    }
  }
}
