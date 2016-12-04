package com.genability.client.types.generated;

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
import com.google.common.collect.ImmutableMap;

@AutoValue
@JsonDeserialize(builder = AutoValue_CalculatedCost.Builder.class)
public abstract class CalculatedCost {

  public abstract @Nullable BigDecimal getAccuracy();
  public abstract @Nullable ImmutableList<PropertyData> getAssumptions();
  public abstract @Nullable DateTime getFromDateTime();
  public abstract @Nullable ImmutableList<CalculatedCostItem> getItems();
  public abstract @Nullable Long getMasterTariffId();
  public abstract @Nullable ImmutableMap<String, Object> getSummary();
  public abstract @Nullable String getTariffName();
  public abstract @Nullable DateTime getToDateTime();
  public abstract @Nullable BigDecimal getTotalCost();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_CalculatedCost.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setAccuracy(@Nullable BigDecimal accuracy);
    public abstract Builder setFromDateTime(@Nullable DateTime fromDateTime);
    public abstract Builder setMasterTariffId(@Nullable Long masterTariffId);
    public abstract Builder setSummary(@Nullable ImmutableMap<String, Object> summary);
    public abstract Builder setTariffName(@Nullable String tariffName);
    public abstract Builder setToDateTime(@Nullable DateTime toDateTime);
    public abstract Builder setTotalCost(@Nullable BigDecimal totalCost);

    @JsonIgnore
    public abstract Builder setAssumptions(@Nullable PropertyData... assumptions);

    @JsonProperty("assumptions")
    public abstract Builder setAssumptions(@Nullable ImmutableList<PropertyData> assumptions);

    @JsonIgnore
    public abstract Builder setItems(@Nullable CalculatedCostItem... items);

    @JsonProperty("items")
    public abstract Builder setItems(@Nullable ImmutableList<CalculatedCostItem> items);

    protected abstract ImmutableList<PropertyData> getAssumptions();
    protected abstract ImmutableList<CalculatedCostItem> getItems();
    protected abstract ImmutableMap<String, Object> getSummary();
    protected abstract CalculatedCost autoBuild();

    public CalculatedCost build() {
      setAssumptions(firstNonNull(getAssumptions(), ImmutableList.of()));
      setItems(firstNonNull(getItems(), ImmutableList.of()));
      setSummary(firstNonNull(getSummary(), ImmutableMap.of()));
      return autoBuild();
    }
  }
}
