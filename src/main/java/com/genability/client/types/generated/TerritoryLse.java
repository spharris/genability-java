package com.genability.client.types.generated;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_TerritoryLse.Builder.class)
public abstract class TerritoryLse {

  public abstract @Nullable Boolean getDistribution();
  public abstract @Nullable BigDecimal getGeneralCoverage();
  public abstract @Nullable Long getLseId();
  public abstract @Nullable String getLseName();
  public abstract @Nullable BigDecimal getResidentialCoverage();
  public abstract @Nullable Boolean getSupplierGeneral();
  public abstract @Nullable Boolean getSupplierResidential();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_TerritoryLse.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setDistribution(@Nullable Boolean distribution);
    public abstract Builder setGeneralCoverage(@Nullable BigDecimal generalCoverage);
    public abstract Builder setLseId(@Nullable Long lseId);
    public abstract Builder setLseName(@Nullable String lseName);
    public abstract Builder setResidentialCoverage(@Nullable BigDecimal residentialCoverage);
    public abstract Builder setSupplierGeneral(@Nullable Boolean supplierGeneral);
    public abstract Builder setSupplierResidential(@Nullable Boolean supplierResidential);

    public abstract TerritoryLse build();
  }
}
