package com.genability.client.api.request;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.CustomerClass;
import com.genability.client.types.Fields;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class GetIncentiveApplicabilitiesRequest extends AbstractRequest {

  GetIncentiveApplicabilitiesRequest() {}

  public abstract @Nullable String getAddressString();
  public abstract @Nullable CustomerClass getCustomerClass();
  public abstract @Nullable DateTime getEffectiveOn();
  public abstract @Nullable DateTime getFromDate();
  public abstract @Nullable String getIncentiveType();
  public abstract @Nullable Boolean getIsExhausted();
  public abstract @Nullable Long getLseId();
  public abstract @Nullable String getProjectType();
  public abstract @Nullable String getState();
  public abstract @Nullable DateTime getToDate();
  public abstract @Nullable String getZipCode();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_GetIncentiveApplicabilitiesRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractRequest.Builder<Builder> {

    public abstract Builder setAddressString(@Nullable String addressString);
    public abstract Builder setCustomerClass(@Nullable CustomerClass customerClass);
    public abstract Builder setEffectiveOn(@Nullable DateTime effectiveOn);
    public abstract Builder setFromDate(@Nullable DateTime fromDate);
    public abstract Builder setIncentiveType(@Nullable String incentiveType);
    public abstract Builder setIsExhausted(@Nullable Boolean isExhausted);
    public abstract Builder setLseId(@Nullable Long lseId);
    public abstract Builder setProjectType(@Nullable String projectType);
    public abstract Builder setState(@Nullable String state);
    public abstract Builder setToDate(@Nullable DateTime toDate);
    public abstract Builder setZipCode(@Nullable String zipCode);

    public abstract GetIncentiveApplicabilitiesRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("addressString", getAddressString())
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
