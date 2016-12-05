package com.genability.client.api.request;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.Fields;
import com.genability.client.types.ReadingData;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class ReadingDataRequest extends AbstractRequest {

  ReadingDataRequest() {}

  public abstract @Nullable ImmutableList<ReadingData> getReadings();
  public abstract @Nullable String getUsageProfileId();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_ReadingDataRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractRequest.Builder<Builder> {

    public abstract Builder setReadings(@Nullable ReadingData... readings);
    public abstract Builder setReadings(@Nullable ImmutableList<ReadingData> readings);
    public abstract Builder setUsageProfileId(@Nullable String usageProfileId);

    public abstract ReadingDataRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("readings", getReadings())
        .addParam("usageProfileId", getUsageProfileId())
        .build();
  }
}
