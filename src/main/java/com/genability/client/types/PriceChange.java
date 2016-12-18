package com.genability.client.types;

import static com.google.common.base.MoreObjects.firstNonNull;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
@JsonDeserialize(builder = AutoValue_PriceChange.Builder.class)
public abstract class PriceChange {

  public abstract @Nullable BigDecimal getAccuracy();
  public abstract @Nullable ZonedDateTime getChangeDateTime();
  public abstract @Nullable String getChangeName();
  public abstract @Nullable BigDecimal getRateAmount();
  public abstract @Nullable ImmutableList<TariffRateChangePeriod> getRateChangePeriods();
  public abstract @Nullable BigDecimal getRelativePriceIndex();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_PriceChange.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setAccuracy(@Nullable BigDecimal accuracy);
    public abstract Builder setChangeDateTime(@Nullable ZonedDateTime changeDateTime);
    public abstract Builder setChangeName(@Nullable String changeName);
    public abstract Builder setRateAmount(@Nullable BigDecimal rateAmount);
    public abstract Builder setRelativePriceIndex(@Nullable BigDecimal relativePriceIndex);

    @JsonIgnore
    public abstract Builder setRateChangePeriods(@Nullable TariffRateChangePeriod... rateChangePeriods);

    @JsonProperty("rateChangePeriods")
    public abstract Builder setRateChangePeriods(@Nullable ImmutableList<TariffRateChangePeriod> rateChangePeriods);

    protected abstract ImmutableList<TariffRateChangePeriod> getRateChangePeriods();
    protected abstract PriceChange autoBuild();

    public PriceChange build() {
      setRateChangePeriods(firstNonNull(getRateChangePeriods(), ImmutableList.of()));
      return autoBuild();
    }
  }
}
