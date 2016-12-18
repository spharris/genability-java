package com.genability.client.api.request;

import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.CustomerClass;
import com.genability.client.types.Fields;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

@AutoValue
public abstract class GetIncentivesRequest extends AbstractGetNRequest {

  GetIncentivesRequest() {}

  public abstract @Nullable String getAddressString();
  public abstract @Nullable ImmutableMap<String, String> getApplicabilityParameters();
  public abstract @Nullable CustomerClass getCustomerClass();
  public abstract @Nullable ZonedDateTime getEffectiveOn();
  public abstract @Nullable ZonedDateTime getFromDate();
  public abstract @Nullable String getIncentiveType();
  public abstract @Nullable Boolean getIsExhausted();
  public abstract @Nullable Long getLseId();
  public abstract @Nullable String getProjectType();
  public abstract @Nullable String getState();
  public abstract @Nullable ZonedDateTime getToDate();
  public abstract @Nullable String getZipCode();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_GetIncentivesRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractGetNRequest.Builder<Builder> {

    public abstract Builder setAddressString(@Nullable String addressString);
    public abstract Builder setApplicabilityParameters(@Nullable ImmutableMap<String, String> applicabilityParameters);
    public abstract Builder setCustomerClass(@Nullable CustomerClass customerClass);
    public abstract Builder setEffectiveOn(@Nullable ZonedDateTime effectiveOn);
    public abstract Builder setFromDate(@Nullable ZonedDateTime fromDate);
    public abstract Builder setIncentiveType(@Nullable String incentiveType);
    public abstract Builder setIsExhausted(@Nullable Boolean isExhausted);
    public abstract Builder setLseId(@Nullable Long lseId);
    public abstract Builder setProjectType(@Nullable String projectType);
    public abstract Builder setState(@Nullable String state);
    public abstract Builder setToDate(@Nullable ZonedDateTime toDate);
    public abstract Builder setZipCode(@Nullable String zipCode);

    public abstract GetIncentivesRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("addressString", getAddressString())
        .addParam("applicabilityParameters", getApplicabilityParameters())
        .addParam("customerClass", getCustomerClass())
        .addParam("effectiveOn", getEffectiveOn())
        .addParam("fromDate", getFromDate())
        .addParam("incentiveType", getIncentiveType())
        .addParam("isExhausted", getIsExhausted())
        .addParam("lseId", getLseId())
        .addParam("projectType", getProjectType())
        .addParam("state", getState())
        .addParam("toDate", getToDate())
        .addParam("zipCode", getZipCode())
        .build();
  }
}
