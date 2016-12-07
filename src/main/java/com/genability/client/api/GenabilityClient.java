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
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.GzipCompressingEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genability.client.api.Annotations.AppId;
import com.genability.client.api.Annotations.AppKey;
import com.genability.client.api.Annotations.RequestCompression;
import com.genability.client.api.Annotations.ServerAddress;
import com.genability.client.api.request.BulkUploadRequest;
import com.genability.client.api.service.GenabilityException;
import com.genability.client.types.Response;
import com.google.common.base.Joiner;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

public final class GenabilityClient {

  private final String appId;
  private final String appKey;
  private final String serverAddress;
  private final boolean requestCompression;
  private final ObjectMapper objectMapper;
  private final HttpClient httpClient;
  private final ListeningExecutorService executor;

  @Inject
  GenabilityClient(
      @AppId String appId,
      @AppKey String appKey,
      @ServerAddress String restApiServer,
      @RequestCompression boolean requestCompression,
      ObjectMapper objectMapper,
      HttpClient httpClient,
      ListeningExecutorService executor) {
    this.appId = appId;
    this.appKey = appKey;
    this.serverAddress = restApiServer;
    this.requestCompression = requestCompression;
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
  
  public <T extends Response<R>, R> ListenableFuture<T> putAsync(String endpointPath,
      Object requestPayload,
      TypeReference<T> resultTypeReference) {
    return executor.submit(() -> put(endpointPath, requestPayload, resultTypeReference));
  }
  
  public <T extends Response<R>, R> T put(String endpointPath,
      Object requestPayload,
      TypeReference<T> resultTypeReference) {
    checkNotNull(endpointPath);
    checkNotNull(requestPayload);
    checkNotNull(resultTypeReference);

    String url = serverAddress + endpointPath;
    HttpPut putRequest = new HttpPut(url);
    putRequest.setEntity(getEntity(requestPayload));

    return execute(putRequest, resultTypeReference);
  }
  
  public <T extends Response<R>, R> ListenableFuture<T> postAsync(String endpointPath,
      Object requestPayload,
      TypeReference<T> resultTypeReference) {
    return executor.submit(() -> post(endpointPath, requestPayload, resultTypeReference));
  }
  
  public <T extends Response<R>, R> T post(String endpointPath,
      Object requestPayload,
      TypeReference<T> resultTypeReference) {
    checkNotNull(endpointPath);
    checkNotNull(requestPayload);
    checkNotNull(resultTypeReference);
    
    String url = serverAddress + endpointPath; 
    HttpPost postRequest = new HttpPost(url);
    postRequest.setEntity(getEntity(requestPayload));

    return execute(postRequest, resultTypeReference);
  }
  
  public <T extends Response<R>, R> ListenableFuture<T> deleteAsync(String endpointPath,
      Collection<NameValuePair> queryParams,
      TypeReference<T> resultTypeReference) {
    return executor.submit(() -> delete(endpointPath, queryParams, resultTypeReference));
  }
  
  public <T extends Response<R>, R> T delete(String endpointPath,
      Collection<NameValuePair> queryParams,
      TypeReference<T> resultTypeReference) {
    checkNotNull(endpointPath);
    checkNotNull(queryParams);
    checkNotNull(resultTypeReference);

    String url = serverAddress + endpointPath;
    if (!queryParams.isEmpty()) {
      url += "?" + URLEncodedUtils.format(queryParams, StandardCharsets.UTF_8);
    }

    HttpDelete deleteRequest = new HttpDelete(url);

    return execute(deleteRequest, resultTypeReference);
  }
  
  public <T extends Response<R>, R> ListenableFuture<T> fileUploadAsync(String endpointPath,
      BulkUploadRequest request,
      TypeReference<T> resultTypeReference) {
    return executor.submit(() -> fileUpload(endpointPath, request, resultTypeReference));
  }
  
  public <T extends Response<R>, R> T fileUpload(String endpointPath,
      BulkUploadRequest request,
      TypeReference<T> resultTypeReference) {
    checkNotNull(endpointPath);
    checkNotNull(request);
    checkNotNull(resultTypeReference);
    
    String url = serverAddress + endpointPath;
    HttpPost postRequest = new HttpPost(url);
    RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
    requestConfigBuilder.setSocketTimeout(300000); // 5 minutes
    postRequest.setConfig(requestConfigBuilder.build());

    MultipartEntityBuilder builder = MultipartEntityBuilder.create();
    builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

    FileBody fileBody = new FileBody(request.getFileData());
    builder.addPart("fileData", fileBody);
    builder.addTextBody("fileFormat", request.getFileFormat(), ContentType.TEXT_XML);
    postRequest.setEntity(getEntity(builder.build()));

    return execute(postRequest, resultTypeReference);
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
                String.format(
                  "Failed (HTTP %d): %s",
                  httpResponse.getStatusLine().getStatusCode(),
                  responseBody),
                responseBody);
          }

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
}
