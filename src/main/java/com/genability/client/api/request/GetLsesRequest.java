package com.genability.client.api.request;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.Fields;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class GetLsesRequest extends AbstractGetNRequest {

  GetLsesRequest() {}

  public abstract @Nullable String getAccountId();
  public abstract @Nullable ImmutableList<String> getCommercialServiceTypes();
  public abstract @Nullable ImmutableList<String> getIndustrialServiceTypes();
  public abstract @Nullable ImmutableList<String> getResidentialServiceTypes();
  public abstract @Nullable ImmutableList<String> getServiceTypes();
  public abstract @Nullable ImmutableList<String> getTransportationServiceTypes();
  public abstract @Nullable String getZipCode();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_GetLsesRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractGetNRequest.Builder<Builder> {

    public abstract Builder setAccountId(@Nullable String accountId);
    public abstract Builder setCommercialServiceTypes(@Nullable String... commercialServiceTypes);
    public abstract Builder setCommercialServiceTypes(@Nullable ImmutableList<String> commercialServiceTypes);
    public abstract Builder setIndustrialServiceTypes(@Nullable String... industrialServiceTypes);
    public abstract Builder setIndustrialServiceTypes(@Nullable ImmutableList<String> industrialServiceTypes);
    public abstract Builder setResidentialServiceTypes(@Nullable String... residentialServiceTypes);
    public abstract Builder setResidentialServiceTypes(@Nullable ImmutableList<String> residentialServiceTypes);
    public abstract Builder setServiceTypes(@Nullable String... serviceTypes);
    public abstract Builder setServiceTypes(@Nullable ImmutableList<String> serviceTypes);
    public abstract Builder setTransportationServiceTypes(@Nullable String... transportationServiceTypes);
    public abstract Builder setTransportationServiceTypes(@Nullable ImmutableList<String> transportationServiceTypes);
    public abstract Builder setZipCode(@Nullable String zipCode);

    public abstract GetLsesRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("accountId", getAccountId())
        .addParam("commercialServiceTypes", getCommercialServiceTypes())
        .addParam("industrialServiceTypes", getIndustrialServiceTypes())
        .addParam("residentialServiceTypes", getResidentialServiceTypes())
        .addParam("serviceTypes", getServiceTypes())
        .addParam("transportationServiceTypes", getTransportationServiceTypes())
        .addParam("zipCode", getZipCode())
        .build();
  }
}
