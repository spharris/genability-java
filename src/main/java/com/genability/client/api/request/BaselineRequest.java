package com.genability.client.api.request;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.CustomerClass;
import com.genability.client.types.Fields;
import com.genability.client.types.ServiceType;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class BaselineRequest extends AbstractRequest {

  BaselineRequest() {}

  public abstract @Nullable String getAddressString();
  public abstract @Nullable Long getBuildingArea();
  public abstract @Nullable String getBuildingType();
  public abstract @Nullable String getBuildingVintage();
  public abstract @Nullable String getCountry();
  public abstract @Nullable CustomerClass getCustomerClass();
  public abstract @Nullable Boolean getExcludeMeasures();
  public abstract @Nullable String getGroupBy();
  public abstract @Nullable Double getLatitude();
  public abstract @Nullable Double getLongitude();
  public abstract @Nullable String getMeasuresUnit();
  public abstract @Nullable ServiceType getServiceType();
  public abstract @Nullable String getZipCode();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_BaselineRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractRequest.Builder<Builder> {

    public abstract Builder setAddressString(@Nullable String addressString);
    public abstract Builder setBuildingArea(@Nullable Long buildingArea);
    public abstract Builder setBuildingType(@Nullable String buildingType);
    public abstract Builder setBuildingVintage(@Nullable String buildingVintage);
    public abstract Builder setCountry(@Nullable String country);
    public abstract Builder setCustomerClass(@Nullable CustomerClass customerClass);
    public abstract Builder setExcludeMeasures(@Nullable Boolean excludeMeasures);
    public abstract Builder setGroupBy(@Nullable String groupBy);
    public abstract Builder setLatitude(@Nullable Double latitude);
    public abstract Builder setLongitude(@Nullable Double longitude);
    public abstract Builder setMeasuresUnit(@Nullable String measuresUnit);
    public abstract Builder setServiceType(@Nullable ServiceType serviceType);
    public abstract Builder setZipCode(@Nullable String zipCode);

    public abstract BaselineRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("addressString", getAddressString())
        .addParam("buildingArea", getBuildingArea())
        .addParam("buildingType", getBuildingType())
        .addParam("buildingVintage", getBuildingVintage())
        .addParam("country", getCountry())
        .addParam("customerClass", getCustomerClass())
        .addParam("excludeMeasures", getExcludeMeasures())
        .addParam("groupBy", getGroupBy())
        .addParam("latitude", getLatitude())
        .addParam("longitude", getLongitude())
        .addParam("measuresUnit", getMeasuresUnit())
        .addParam("serviceType", getServiceType())
        .addParam("zipCode", getZipCode())
        .build();
  }
}
