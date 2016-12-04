package com.genability.client.types.generated;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.genability.client.types.ChargeType;
import com.genability.client.types.RateUnit;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_TariffRateChangePeriod.Builder.class)
public abstract class TariffRateChangePeriod {

  public abstract @Nullable String getChangeName();
  public abstract @Nullable String getChargePeriod();
  public abstract @Nullable ChargeType getChargeType();
  public abstract @Nullable DateTime getFromDateTime();
  public abstract @Nullable String getQuantityKey();
  public abstract @Nullable BigDecimal getRateAmount();
  public abstract @Nullable String getRateName();
  public abstract @Nullable RateUnit getRateUnit();
  public abstract @Nullable Long getTariffRateId();
  public abstract @Nullable DateTime getToDateTime();
  public abstract @Nullable Long getTouGroupId();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_TariffRateChangePeriod.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setChangeName(@Nullable String changeName);
    public abstract Builder setChargePeriod(@Nullable String chargePeriod);
    public abstract Builder setChargeType(@Nullable ChargeType chargeType);
    public abstract Builder setFromDateTime(@Nullable DateTime fromDateTime);
    public abstract Builder setQuantityKey(@Nullable String quantityKey);
    public abstract Builder setRateAmount(@Nullable BigDecimal rateAmount);
    public abstract Builder setRateName(@Nullable String rateName);
    public abstract Builder setRateUnit(@Nullable RateUnit rateUnit);
    public abstract Builder setTariffRateId(@Nullable Long tariffRateId);
    public abstract Builder setToDateTime(@Nullable DateTime toDateTime);
    public abstract Builder setTouGroupId(@Nullable Long touGroupId);

    public abstract TariffRateChangePeriod build();
  }
}
