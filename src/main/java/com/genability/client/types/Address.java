package com.genability.client.types;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_Address.Builder.class)
public abstract class Address {

  public abstract @Nullable String getAddress1();
  public abstract @Nullable String getAddress2();
  public abstract @Nullable String getAddressName();
  public abstract @Nullable String getAddressString();
  public abstract @Nullable String getCity();
  public abstract @Nullable String getCountry();
  public abstract @Nullable Double getLatitude();
  public abstract @Nullable Double getLongitude();
  public abstract @Nullable String getState();
  public abstract @Nullable String getZipCode();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_Address.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setAddress1(@Nullable String address1);
    public abstract Builder setAddress2(@Nullable String address2);
    public abstract Builder setAddressName(@Nullable String addressName);
    public abstract Builder setAddressString(@Nullable String addressString);
    public abstract Builder setCity(@Nullable String city);
    public abstract Builder setCountry(@Nullable String country);
    public abstract Builder setLatitude(@Nullable Double latitude);
    public abstract Builder setLongitude(@Nullable Double longitude);
    public abstract Builder setState(@Nullable String state);
    public abstract Builder setZipCode(@Nullable String zipCode);

    public abstract Address build();
  }
}
