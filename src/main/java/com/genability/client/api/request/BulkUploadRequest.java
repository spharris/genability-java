package com.genability.client.api.request;

import java.io.File;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.Fields;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class BulkUploadRequest extends AbstractRequest {

  BulkUploadRequest() {}

  public abstract @Nullable String getContentType();
  public abstract @Nullable File getFileData();
  public abstract @Nullable String getFileFormat();
  public abstract @Nullable String getName();
  public abstract @Nullable String getUsageProfileId();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_BulkUploadRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractRequest.Builder<Builder> {

    public abstract Builder setContentType(@Nullable String contentType);
    public abstract Builder setFileData(@Nullable File fileData);
    public abstract Builder setFileFormat(@Nullable String fileFormat);
    public abstract Builder setName(@Nullable String name);
    public abstract Builder setUsageProfileId(@Nullable String usageProfileId);

    public abstract BulkUploadRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("contentType", getContentType())
        .addParam("fileData", getFileData())
        .addParam("fileFormat", getFileFormat())
        .addParam("name", getName())
        .addParam("usageProfileId", getUsageProfileId())
        .build();
  }
}
