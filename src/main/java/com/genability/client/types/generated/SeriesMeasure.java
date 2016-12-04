package com.genability.client.types.generated;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_SeriesMeasure.Builder.class)
public abstract class SeriesMeasure {

  public abstract @Nullable BigDecimal getCost();
  public abstract @Nullable Long getDuration();
  public abstract @Nullable DateTime getFromDateTime();
  public abstract @Nullable String getPeriod();
  public abstract @Nullable BigDecimal getQty();
  public abstract @Nullable BigDecimal getRate();
  public abstract @Nullable Integer getSeriesId();
  public abstract @Nullable DateTime getToDateTime();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_SeriesMeasure.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setCost(@Nullable BigDecimal cost);
    public abstract Builder setDuration(@Nullable Long duration);
    public abstract Builder setFromDateTime(@Nullable DateTime fromDateTime);
    public abstract Builder setPeriod(@Nullable String period);
    public abstract Builder setQty(@Nullable BigDecimal qty);
    public abstract Builder setRate(@Nullable BigDecimal rate);
    public abstract Builder setSeriesId(@Nullable Integer seriesId);
    public abstract Builder setToDateTime(@Nullable DateTime toDateTime);

    public abstract SeriesMeasure build();
  }
}
