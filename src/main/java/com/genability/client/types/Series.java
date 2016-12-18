package com.genability.client.types;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_Series.Builder.class)
public abstract class Series {

  public abstract @Nullable BigDecimal getCost();
  public abstract @Nullable String getDesignId();
  public abstract @Nullable String getDisplayLabel();
  public abstract @Nullable ZonedDateTime getFromDateTime();
  public abstract @Nullable String getKey();
  public abstract @Nullable BigDecimal getQty();
  public abstract @Nullable BigDecimal getRate();
  public abstract @Nullable String getScenario();
  public abstract @Nullable Integer getSeriesDuration();
  public abstract @Nullable Integer getSeriesId();
  public abstract @Nullable String getSeriesPeriod();
  public abstract @Nullable ZonedDateTime getToDateTime();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_Series.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setCost(@Nullable BigDecimal cost);
    public abstract Builder setDesignId(@Nullable String designId);
    public abstract Builder setDisplayLabel(@Nullable String displayLabel);
    public abstract Builder setFromDateTime(@Nullable ZonedDateTime fromDateTime);
    public abstract Builder setKey(@Nullable String key);
    public abstract Builder setQty(@Nullable BigDecimal qty);
    public abstract Builder setRate(@Nullable BigDecimal rate);
    public abstract Builder setScenario(@Nullable String scenario);
    public abstract Builder setSeriesDuration(@Nullable Integer seriesDuration);
    public abstract Builder setSeriesId(@Nullable Integer seriesId);
    public abstract Builder setSeriesPeriod(@Nullable String seriesPeriod);
    public abstract Builder setToDateTime(@Nullable ZonedDateTime toDateTime);

    public abstract Series build();
  }
}
