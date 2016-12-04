package com.genability.client.types.generated;

import static com.google.common.base.MoreObjects.firstNonNull;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.genability.client.types.Baseline.BuildingType;
import com.genability.client.types.ServiceType;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

@AutoValue
@JsonDeserialize(builder = AutoValue_Baseline.Builder.class)
public abstract class Baseline {

  public abstract @Nullable String getBaselineId();
  public abstract @Nullable BuildingType getBuildingType();
  public abstract @Nullable Territory getClimateZone();
  public abstract @Nullable ImmutableMap<String, BigDecimal> getFactors();
  public abstract @Nullable Coordinates getLocation();
  public abstract @Nullable Integer getMeasureDuration();
  public abstract @Nullable String getMeasureUnit();
  public abstract @Nullable String getMeasureValue();
  public abstract @Nullable ImmutableList<BaselineMeasure> getMeasures();
  public abstract @Nullable String getName();
  public abstract @Nullable ImmutableList<PropertyData> getProperties();
  public abstract @Nullable ServiceType getServiceType();
  public abstract @Nullable String getSourceId();
  public abstract @Nullable Integer getStartDay();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_Baseline.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setBaselineId(@Nullable String baselineId);
    public abstract Builder setBuildingType(@Nullable BuildingType buildingType);
    public abstract Builder setClimateZone(@Nullable Territory climateZone);
    public abstract Builder setFactors(@Nullable ImmutableMap<String, BigDecimal> factors);
    public abstract Builder setLocation(@Nullable Coordinates location);
    public abstract Builder setMeasureDuration(@Nullable Integer measureDuration);
    public abstract Builder setMeasureUnit(@Nullable String measureUnit);
    public abstract Builder setMeasureValue(@Nullable String measureValue);
    public abstract Builder setName(@Nullable String name);
    public abstract Builder setServiceType(@Nullable ServiceType serviceType);
    public abstract Builder setSourceId(@Nullable String sourceId);
    public abstract Builder setStartDay(@Nullable Integer startDay);

    @JsonIgnore
    public abstract Builder setMeasures(@Nullable BaselineMeasure... measures);

    @JsonProperty("measures")
    public abstract Builder setMeasures(@Nullable ImmutableList<BaselineMeasure> measures);

    @JsonIgnore
    public abstract Builder setProperties(@Nullable PropertyData... properties);

    @JsonProperty("properties")
    public abstract Builder setProperties(@Nullable ImmutableList<PropertyData> properties);

    protected abstract ImmutableMap<String, BigDecimal> getFactors();
    protected abstract ImmutableList<BaselineMeasure> getMeasures();
    protected abstract ImmutableList<PropertyData> getProperties();
    protected abstract Baseline autoBuild();

    public Baseline build() {
      setFactors(firstNonNull(getFactors(), ImmutableMap.of()));
      setMeasures(firstNonNull(getMeasures(), ImmutableList.of()));
      setProperties(firstNonNull(getProperties(), ImmutableList.of()));
      return autoBuild();
    }
  }
}
