package com.genability.client.types;

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

  /**
   * This allows you access to the Series that you're interested in. E.g.
   *
   *    Series monthlyPreSolarUtilitySeries =
   *        accountAnalysis.getSeriesByParameters("before", "MONTH", null);
   *    // check that monthlyPreSolarUtilitySeries != null, then proceed
   * 
   * Note that the key parameter is not used at this time.
   * 
   * @param scenario The scenario.
   * @param period The period.
   * @param key The key.
   * @return The return value.
   */
  @JsonIgnore
  public Series getSeriesByParameters(String scenario, String period, String key) {
    if (getSeries() == null) {
      return null;
    }

    if (scenario == null || period == null) {
      return null;
    }

    if (key != null && key.isEmpty()) {
      key = null;
    }

    for (Series s : getSeries()) {
      if (s.getScenario().equalsIgnoreCase(scenario)
          && s.getSeriesPeriod().equalsIgnoreCase(period)
          && ((key == null && s.getKey() == null) 
              || (key != null && key.equalsIgnoreCase(s.getKey()))))
        return s;
    }

    return null;
  }

  /**
   * This allows you access to the SeriesData that you're interested in. E.g.
   *
   *    Series monthlyPreSolarUtilitySeries =
   *        accountAnalysis.getSeriesByParameters("before", "MONTH", null);
   *    if (monthlyPreSolarUtilitySeries == null) throw SomeException();
   *
   *    Integer seriesId = monthlyPreSolarUtilitySeries.getSeriesId();
   *    List&lt;SeriesMeasure&gt; monthlyPreSolarUtilitySeriesData =
   *        accountAnalysis.getSeriesDataBySeriesId(seriesId);
   *    // check that monthlyPreSolarUtilitySeriesData != null, then proceed
   *    
   * @param seriesId The seriesId.
   * @return The return value.
   */
  @JsonIgnore
  public ImmutableList<SeriesMeasure> getSeriesDataBySeriesId(Integer seriesId) {
    if (getSeriesData() == null) {
      return null;
    }

    ImmutableList.Builder<SeriesMeasure> filteredSeriesData = ImmutableList.builder();
    for (SeriesMeasure sm : getSeriesData()) {
      if (seriesId.equals(sm.getSeriesId())) {
        filteredSeriesData.add(sm);
      }
    }

    return filteredSeriesData.build();
  }

  @JsonIgnore
  public CalculatedCost getSeriesCostsByParameters(String scenario, String period, String key) {
    if (getSeriesCosts() == null) {
      return null;
    }

    Series s = getSeriesByParameters(scenario, period, key);
    if (s == null) {
      return null;
    }

    return getSeriesCosts().get(s.getSeriesId());
  }

  @JsonIgnore
  public CalculatedCost getSeriesCostsBySeriesId(int seriesId) {
    if (getSeriesCosts() == null) {
      // if the populateCosts parameter wasn't populated
      return null;
    } else {
      return getSeriesCosts().get(seriesId);
    }
  }
  
  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setDataStatus(@Nullable Integer dataStatus);
    public abstract Builder setDesignId(@Nullable String designId);
    public abstract Builder setSeriesCosts(
        @Nullable ImmutableMap<Integer, CalculatedCost> seriesCosts);
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
