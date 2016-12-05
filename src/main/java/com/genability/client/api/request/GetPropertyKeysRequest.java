package com.genability.client.api.request;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.DataType;
import com.genability.client.types.Fields;
import com.genability.client.types.Privacy;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class GetPropertyKeysRequest extends AbstractGetNRequest {

  GetPropertyKeysRequest() {}

  public abstract @Nullable DataType getDataType();
  public abstract @Nullable Long getEntityId();
  public abstract @Nullable String getEntityType();
  public abstract @Nullable Boolean getExcludeGlobal();
  public abstract @Nullable String getFamily();
  public abstract @Nullable String getKeySpace();
  public abstract @Nullable ImmutableList<Privacy> getPrivacy();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_GetPropertyKeysRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractGetNRequest.Builder<Builder> {

    public abstract Builder setDataType(@Nullable DataType dataType);
    public abstract Builder setEntityId(@Nullable Long entityId);
    public abstract Builder setEntityType(@Nullable String entityType);
    public abstract Builder setExcludeGlobal(@Nullable Boolean excludeGlobal);
    public abstract Builder setFamily(@Nullable String family);
    public abstract Builder setKeySpace(@Nullable String keySpace);
    public abstract Builder setPrivacy(@Nullable Privacy... privacy);
    public abstract Builder setPrivacy(@Nullable ImmutableList<Privacy> privacy);

    public abstract GetPropertyKeysRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("dataType", getDataType())
        .addParam("entityId", getEntityId())
        .addParam("entityType", getEntityType())
        .addParam("excludeGlobal", getExcludeGlobal())
        .addParam("family", getFamily())
        .addParam("keySpace", getKeySpace())
        .addParam("privacy", getPrivacy())
        .build();
  }
}
