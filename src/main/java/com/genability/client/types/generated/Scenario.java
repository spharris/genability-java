package com.genability.client.types.generated;

import static com.google.common.base.MoreObjects.firstNonNull;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.genability.client.types.ServiceType;
import com.genability.client.types.TariffRate;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
@JsonDeserialize(builder = AutoValue_Scenario.Builder.class)
public abstract class Scenario {

  public abstract @Nullable ImmutableList<PropertyData> getAssumptions();
  public abstract @Nullable String getId();
  public abstract @Nullable ImmutableList<PropertyData> getInputs();
  public abstract @Nullable String getName();
  public abstract @Nullable ImmutableList<TariffRate> getRates();
  public abstract @Nullable ServiceType getServiceType();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_Scenario.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setId(@Nullable String id);
    public abstract Builder setName(@Nullable String name);
    public abstract Builder setServiceType(@Nullable ServiceType serviceType);

    @JsonIgnore
    public abstract Builder setAssumptions(@Nullable PropertyData... assumptions);

    @JsonProperty("assumptions")
    public abstract Builder setAssumptions(@Nullable ImmutableList<PropertyData> assumptions);

    @JsonIgnore
    public abstract Builder setInputs(@Nullable PropertyData... inputs);

    @JsonProperty("inputs")
    public abstract Builder setInputs(@Nullable ImmutableList<PropertyData> inputs);

    @JsonIgnore
    public abstract Builder setRates(@Nullable TariffRate... rates);

    @JsonProperty("rates")
    public abstract Builder setRates(@Nullable ImmutableList<TariffRate> rates);

    protected abstract ImmutableList<PropertyData> getAssumptions();
    protected abstract ImmutableList<PropertyData> getInputs();
    protected abstract ImmutableList<TariffRate> getRates();
    protected abstract Scenario autoBuild();

    public Scenario build() {
      setAssumptions(firstNonNull(getAssumptions(), ImmutableList.of()));
      setInputs(firstNonNull(getInputs(), ImmutableList.of()));
      setRates(firstNonNull(getRates(), ImmutableList.of()));
      return autoBuild();
    }
  }
}
