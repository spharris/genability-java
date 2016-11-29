package com.genability.client.api;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.GzipCompressingEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genability.client.api.Annotations.AppId;
import com.genability.client.api.Annotations.AppKey;
import com.genability.client.api.Annotations.ServerAddress;
import com.genability.client.api.service.AccountAnalysisService;
import com.genability.client.api.service.AccountService;
import com.genability.client.api.service.BaseService;
import com.genability.client.api.service.BulkUploadService;
import com.genability.client.api.service.CalculateService;
import com.genability.client.api.service.CalendarService;
import com.genability.client.api.service.GenabilityException;
import com.genability.client.api.service.IncentiveService;
import com.genability.client.api.service.LseService;
import com.genability.client.api.service.PriceService;
import com.genability.client.api.service.ProfileService;
import com.genability.client.api.service.PropertyService;
import com.genability.client.api.service.TariffService;
import com.genability.client.api.service.TerritoryService;
import com.genability.client.api.service.TimeOfUseService;
import com.genability.client.api.service.TypicalService;
import com.genability.client.types.Response;
import com.google.common.base.Joiner;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

public class GenabilityClient {

  private String appId;
  private String appKey;
  private String serverAddress;
  private ObjectMapper objectMapper;
  private HttpClient httpClient;
  private ListeningExecutorService executor;
  private boolean requestCompression = false;

  public GenabilityClient() {}

  public GenabilityClient(String appId, String appKey) {
    this.appId = appId;
    this.appKey = appKey;
  }

  @Inject
  public GenabilityClient(
      @AppId String appId,
      @AppKey String appKey,
      @ServerAddress String restApiServer,
      ObjectMapper objectMapper,
      HttpClient httpClient,
      ListeningExecutorService executor) {
    this.appId = appId;
    this.appKey = appKey;
    this.serverAddress = restApiServer;
    this.objectMapper = objectMapper;
    this.httpClient = httpClient;
    this.executor = executor;
  }

  public <T extends Response<R>, R> ListenableFuture<T> getAsync(String endpointPath,
      Collection<NameValuePair> queryParams,
      TypeReference<T> resultTypeReference) {
    return executor.submit(() -> get(endpointPath, queryParams, resultTypeReference));
  }
  
  public <T extends Response<R>, R> T get(String endpointPath,
      Collection<NameValuePair> queryParams,
      TypeReference<T> resultTypeReference) {
    checkNotNull(endpointPath);
    checkNotNull(queryParams);
    checkNotNull(resultTypeReference);

    String url = serverAddress + endpointPath;
    if (!queryParams.isEmpty()) {
      url += "?" + URLEncodedUtils.format(queryParams, StandardCharsets.UTF_8);
    }
    
    HttpGet getRequest = new HttpGet(url);
    return execute(getRequest, resultTypeReference);
  }
  
  private <T extends Response<R>, R> T execute(HttpRequestBase request,
      final TypeReference<T> resultTypeReference) {
    try {
      String basicAuth = Base64.encodeBase64String(
        Joiner.on(":").join(appId, appKey).getBytes(StandardCharsets.UTF_8));

      request.addHeader("Authorization", "Basic " + basicAuth);
      request.addHeader("Accept", "application/json");
      request.addHeader("Accept-Encoding", "gzip");

      return httpClient.execute(request, new ResponseHandler<T>() {
        @Override
        public T handleResponse(HttpResponse httpResponse)
            throws ClientProtocolException, IOException {
          if (httpResponse.getStatusLine().getStatusCode() != 200) {
            String responseBody = null;
            try {
              responseBody = EntityUtils.toString(httpResponse.getEntity());
            } catch (IOException ex) {}

            throw new GenabilityException(
                "Failed : HTTP error code : " + httpResponse.getStatusLine().getStatusCode(),
                responseBody);
          }

          //
          // Convert the JSON pay-load to the standard Response object.
          //
          return objectMapper.readValue(httpResponse.getEntity().getContent(), resultTypeReference);
        }
      });

    } catch (Exception e) {
      throw new GenabilityException(e);
    } finally {
      request.releaseConnection();
    }
  }

  private HttpEntity getEntity(Object obj) {
    return getEntity(new JacksonHttpEntity(obj));
  }

  private HttpEntity getEntity(HttpEntity ent) {
    if (requestCompression) {
      return new GzipCompressingEntity(ent);
    } else {
      return ent;
    }
  }

  private class JacksonHttpEntity extends AbstractHttpEntity {

    private final Object object;

    public JacksonHttpEntity(final Object object) {
      this.object = object;

      setContentType(ContentType.APPLICATION_JSON.getMimeType());
    }

    @Override
    public long getContentLength() {
      return -1;
    }

    @Override
    public boolean isRepeatable() {
      return true;
    }

    @Override
    public boolean isStreaming() {
      return false;
    }

    @Override
    public InputStream getContent() throws IOException {
      return new ByteArrayInputStream(objectMapper.writeValueAsBytes(object));
    }

    @Override
    public void writeTo(final OutputStream outstream) throws IOException {
      if (outstream == null) {
        throw new IllegalArgumentException("Output stream may not be null");
      }
      objectMapper.writeValue(outstream, object);
    }
  }
  
  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getAppKey() {
    return appKey;
  }

  public void setAppKey(String appKey) {
    this.appKey = appKey;
  }

  public String getRestApiServer() {
    return serverAddress;
  }

  public void setRestApiServer(String restApiServer) {
    this.serverAddress = restApiServer;
  }

  public ObjectMapper getMapper() {
    return objectMapper;
  }

  public void setMapper(ObjectMapper mapper) {
    this.objectMapper = mapper;
  }

  public HttpClient getHttpClient() {
    return httpClient;
  }

  public void setHttpClient(HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  public boolean getRequestCompression() {
    return requestCompression;
  }

  public void setRequestCompression(boolean requestCompression) {
    this.requestCompression = requestCompression;
  }

  protected <S extends BaseService> S initializeService(S service) {
    service.setAppId(appId);
    service.setAppKey(appKey);
    service.setRequestCompression(requestCompression);

    if (serverAddress != null) {
      service.setRestApiServer(serverAddress);
    }
    if (objectMapper != null) {
      service.setMapper(objectMapper);
    }
    if (httpClient != null) {
      service.setHttpClient(httpClient);
    }
    return service;
  }

  public AccountService getAccountService() {
    return initializeService(new AccountService());
  }

  public BulkUploadService getBulkUploadService() {
    return initializeService(new BulkUploadService());
  }

  public CalculateService getCalculateService() {
    return initializeService(new CalculateService());
  }

  public LseService getLseService() {
    return initializeService(new LseService());
  }

  public PriceService getPriceService() {
    return initializeService(new PriceService());
  }

  public ProfileService getProfileService() {
    return initializeService(new ProfileService());
  }

  public AccountAnalysisService getAccountAnalysisService() {
    return initializeService(new AccountAnalysisService());
  }

  public PropertyService getPropertyService() {
    return initializeService(new PropertyService());
  }

  public CalendarService getCalendarService() {
    return initializeService(new CalendarService());
  }

  public TypicalService getTypicalService() {
    return initializeService(new TypicalService());
  }

  public TerritoryService getTerritoryService() {
    return initializeService(new TerritoryService());
  }

  public TimeOfUseService getTimeOfUseService() {
    return initializeService(new TimeOfUseService());
  }

  public IncentiveService getIncentiveService() {
    return initializeService(new IncentiveService());
  }
}
