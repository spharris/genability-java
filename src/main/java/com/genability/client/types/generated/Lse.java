package com.genability.client.types.generated;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_Lse.Builder.class)
public abstract class Lse {

  public abstract @Nullable String getCode();
  public abstract @Nullable Integer getCommercialCustomers();
  public abstract @Nullable Integer getCommercialRevenues();
  public abstract @Nullable Integer getCommercialSales();
  public abstract @Nullable String getCommercialServiceTypes();
  public abstract @Nullable Integer getIndustrialCustomers();
  public abstract @Nullable Integer getIndustrialRevenues();
  public abstract @Nullable Integer getIndustrialSales();
  public abstract @Nullable String getIndustrialServiceTypes();
  public abstract @Nullable String getLseCode();
  public abstract @Nullable Long getLseId();
  public abstract @Nullable String getName();
  public abstract @Nullable String getOfferingType();
  public abstract @Nullable String getOwnership();
  public abstract @Nullable Integer getResidentialCustomers();
  public abstract @Nullable Integer getResidentialRevenues();
  public abstract @Nullable Integer getResidentialSales();
  public abstract @Nullable String getResidentialServiceTypes();
  public abstract @Nullable String getServiceTypes();
  public abstract @Nullable Integer getTotalCustomers();
  public abstract @Nullable Integer getTotalRevenues();
  public abstract @Nullable Integer getTotalSales();
  public abstract @Nullable Integer getTransportationCustomers();
  public abstract @Nullable Integer getTransportationRevenues();
  public abstract @Nullable Integer getTransportationSales();
  public abstract @Nullable String getTransportationServiceTypes();
  public abstract @Nullable String getWebsiteHome();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_Lse.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setCode(@Nullable String code);
    public abstract Builder setCommercialCustomers(@Nullable Integer commercialCustomers);
    public abstract Builder setCommercialRevenues(@Nullable Integer commercialRevenues);
    public abstract Builder setCommercialSales(@Nullable Integer commercialSales);
    public abstract Builder setCommercialServiceTypes(@Nullable String commercialServiceTypes);
    public abstract Builder setIndustrialCustomers(@Nullable Integer industrialCustomers);
    public abstract Builder setIndustrialRevenues(@Nullable Integer industrialRevenues);
    public abstract Builder setIndustrialSales(@Nullable Integer industrialSales);
    public abstract Builder setIndustrialServiceTypes(@Nullable String industrialServiceTypes);
    public abstract Builder setLseCode(@Nullable String lseCode);
    public abstract Builder setLseId(@Nullable Long lseId);
    public abstract Builder setName(@Nullable String name);
    public abstract Builder setOfferingType(@Nullable String offeringType);
    public abstract Builder setOwnership(@Nullable String ownership);
    public abstract Builder setResidentialCustomers(@Nullable Integer residentialCustomers);
    public abstract Builder setResidentialRevenues(@Nullable Integer residentialRevenues);
    public abstract Builder setResidentialSales(@Nullable Integer residentialSales);
    public abstract Builder setResidentialServiceTypes(@Nullable String residentialServiceTypes);
    public abstract Builder setServiceTypes(@Nullable String serviceTypes);
    public abstract Builder setTotalCustomers(@Nullable Integer totalCustomers);
    public abstract Builder setTotalRevenues(@Nullable Integer totalRevenues);
    public abstract Builder setTotalSales(@Nullable Integer totalSales);
    public abstract Builder setTransportationCustomers(@Nullable Integer transportationCustomers);
    public abstract Builder setTransportationRevenues(@Nullable Integer transportationRevenues);
    public abstract Builder setTransportationSales(@Nullable Integer transportationSales);
    public abstract Builder setTransportationServiceTypes(@Nullable String transportationServiceTypes);
    public abstract Builder setWebsiteHome(@Nullable String websiteHome);

    public abstract Lse build();
  }
}
