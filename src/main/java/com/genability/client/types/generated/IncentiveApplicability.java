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

@AutoValue
@JsonDeserialize(builder = AutoValue_IncentiveApplicability.Builder.class)
public abstract class IncentiveApplicability {

  public abstract @Nullable String getApplicabilityKey();
  public abstract @Nullable ImmutableList<PropertyChoice> getChoices();
  public abstract @Nullable String getDescription();
  public abstract @Nullable String getDisplayName();
  public abstract @Nullable BigDecimal getMaxValue();
  public abstract @Nullable BigDecimal getMinValue();
  public abstract @Nullable String getOperator();
  public abstract @Nullable String getQuantityKey();
  public abstract @Nullable String getQuantityUnit();
  public abstract @Nullable String getRequiredValue();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_IncentiveApplicability.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setApplicabilityKey(@Nullable String applicabilityKey);
    public abstract Builder setDescription(@Nullable String description);
    public abstract Builder setDisplayName(@Nullable String displayName);
    public abstract Builder setMaxValue(@Nullable BigDecimal maxValue);
    public abstract Builder setMinValue(@Nullable BigDecimal minValue);
    public abstract Builder setOperator(@Nullable String operator);
    public abstract Builder setQuantityKey(@Nullable String quantityKey);
    public abstract Builder setQuantityUnit(@Nullable String quantityUnit);
    public abstract Builder setRequiredValue(@Nullable String requiredValue);

    @JsonIgnore
    public abstract Builder setChoices(@Nullable PropertyChoice... choices);

    @JsonProperty("choices")
    public abstract Builder setChoices(@Nullable ImmutableList<PropertyChoice> choices);

    protected abstract ImmutableList<PropertyChoice> getChoices();
    protected abstract IncentiveApplicability autoBuild();

    public IncentiveApplicability build() {
      setChoices(firstNonNull(getChoices(), ImmutableList.of()));
      return autoBuild();
    }
  }
}
