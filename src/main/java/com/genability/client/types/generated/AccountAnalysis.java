package com.genability.client.types.generated;

import static com.google.common.base.MoreObjects.firstNonNull;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

@AutoValue
@JsonDeserialize(builder = AutoValue_AccountAnalysis.Builder.class)
public abstract class AccountAnalysis {

  public abstract @Nullable Integer getDataStatus();
  public abstract @Nullable String getDesignId();
  public abstract @Nullable ImmutableList<Scenario> getScenarios();
  public abstract @Nullable ImmutableList<Series> getSeries();
  public abstract @Nullable ImmutableMap<Integer, CalculatedCost> getSeriesCosts();
  public abstract @Nullable ImmutableList<SeriesMeasure> getSeriesData();
  public abstract @Nullable ImmutableMap<String, BigDecimal> getSummary();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_AccountAnalysis.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setDataStatus(@Nullable Integer dataStatus);
    public abstract Builder setDesignId(@Nullable String designId);
    public abstract Builder setSeriesCosts(@Nullable ImmutableMap<Integer, CalculatedCost> seriesCosts);
    public abstract Builder setSummary(@Nullable ImmutableMap<String, BigDecimal> summary);

    @JsonIgnore
    public abstract Builder setScenarios(@Nullable Scenario... scenarios);

    @JsonProperty("scenarios")
    public abstract Builder setScenarios(@Nullable ImmutableList<Scenario> scenarios);

    @JsonIgnore
    public abstract Builder setSeries(@Nullable Series... series);

    @JsonProperty("series")
    public abstract Builder setSeries(@Nullable ImmutableList<Series> series);

    @JsonIgnore
    public abstract Builder setSeriesData(@Nullable SeriesMeasure... seriesData);

    @JsonProperty("seriesData")
    public abstract Builder setSeriesData(@Nullable ImmutableList<SeriesMeasure> seriesData);

    protected abstract ImmutableList<Scenario> getScenarios();
    protected abstract ImmutableList<Series> getSeries();
    protected abstract ImmutableMap<Integer, CalculatedCost> getSeriesCosts();
    protected abstract ImmutableList<SeriesMeasure> getSeriesData();
    protected abstract ImmutableMap<String, BigDecimal> getSummary();
    protected abstract AccountAnalysis autoBuild();

    public AccountAnalysis build() {
      setScenarios(firstNonNull(getScenarios(), ImmutableList.of()));
      setSeries(firstNonNull(getSeries(), ImmutableList.of()));
      setSeriesCosts(firstNonNull(getSeriesCosts(), ImmutableMap.of()));
      setSeriesData(firstNonNull(getSeriesData(), ImmutableList.of()));
      setSummary(firstNonNull(getSummary(), ImmutableMap.of()));
      return autoBuild();
    }
  }
}
