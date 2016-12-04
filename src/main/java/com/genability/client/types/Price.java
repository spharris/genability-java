package com.genability.client.types;

import static com.google.common.base.MoreObjects.firstNonNull;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
@JsonDeserialize(builder = AutoValue_Price.Builder.class)
public abstract class Price {

  public abstract @Nullable ChargeType getChargeType();
  public abstract @Nullable String getCurrency();
  public abstract @Nullable DateTime getFromDateTime();
  public abstract @Nullable ImmutableList<PriceChange> getPriceChanges();
  public abstract @Nullable String getQuantityKey();
  public abstract @Nullable BigDecimal getRateAmount();
  public abstract @Nullable ImmutableList<TariffRateChangePeriod> getRateChangePeriods();
  public abstract @Nullable BigDecimal getRelativePriceIndex();
  public abstract @Nullable Long getTariffId();
  public abstract @Nullable DateTime getToDateTime();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_Price.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setChargeType(@Nullable ChargeType chargeType);
    public abstract Builder setCurrency(@Nullable String currency);
    public abstract Builder setFromDateTime(@Nullable DateTime fromDateTime);
    public abstract Builder setQuantityKey(@Nullable String quantityKey);
    public abstract Builder setRateAmount(@Nullable BigDecimal rateAmount);
    public abstract Builder setRelativePriceIndex(@Nullable BigDecimal relativePriceIndex);
    public abstract Builder setTariffId(@Nullable Long tariffId);
    public abstract Builder setToDateTime(@Nullable DateTime toDateTime);

    @JsonIgnore
    public abstract Builder setPriceChanges(@Nullable PriceChange... priceChanges);

    @JsonProperty("priceChanges")
    public abstract Builder setPriceChanges(@Nullable ImmutableList<PriceChange> priceChanges);

    @JsonIgnore
    public abstract Builder setRateChangePeriods(@Nullable TariffRateChangePeriod... rateChangePeriods);

    @JsonProperty("rateChangePeriods")
    public abstract Builder setRateChangePeriods(@Nullable ImmutableList<TariffRateChangePeriod> rateChangePeriods);

    protected abstract ImmutableList<PriceChange> getPriceChanges();
    protected abstract ImmutableList<TariffRateChangePeriod> getRateChangePeriods();
    protected abstract Price autoBuild();

    public Price build() {
      setPriceChanges(firstNonNull(getPriceChanges(), ImmutableList.of()));
      setRateChangePeriods(firstNonNull(getRateChangePeriods(), ImmutableList.of()));
      return autoBuild();
    }
  }
}
