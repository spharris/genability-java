package com.genability.client.types;

import static com.google.common.base.MoreObjects.firstNonNull;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
@JsonDeserialize(builder = AutoValue_TariffProperty.Builder.class)
public abstract class TariffProperty {

  public abstract @Nullable ImmutableList<Choice> getChoices();
  public abstract @Nullable String getDataType();
  public abstract @Nullable String getDescription();
  public abstract @Nullable String getDisplayName();
  public abstract @Nullable Long getEntityId();
  public abstract @Nullable String getEntityName();
  public abstract @Nullable String getEntityType();
  public abstract @Nullable String getFamily();
  public abstract @Nullable String getFormulaDetail();
  public abstract @Nullable Boolean getIsDefault();
  public abstract @Nullable String getKeyName();
  public abstract @Nullable String getKeyspace();
  public abstract @Nullable String getLookbackPeriod();
  public abstract @Nullable Long getLookbackQuantity();
  public abstract @Nullable Long getLookbackSeasonId();
  public abstract @Nullable Long getLookbackTimeOfUseId();
  public abstract @Nullable Long getLookbackintervalQuantity();
  public abstract @Nullable ImmutableList<PropertyLookup> getLookups();
  public abstract @Nullable String getMaxValue();
  public abstract @Nullable String getMinValue();
  public abstract @Nullable String getOperator();
  public abstract @Nullable String getPeriod();
  public abstract @Nullable Privacy getPrivacy();
  public abstract @Nullable TariffPropertyType getPropertyTypes();
  public abstract @Nullable String getPropertyValue();
  public abstract @Nullable String getQuantityKey();
  public abstract @Nullable String getQuantityUnit();
  public abstract @Nullable String getResourceType();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_TariffProperty.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setDataType(@Nullable String dataType);
    public abstract Builder setDescription(@Nullable String description);
    public abstract Builder setDisplayName(@Nullable String displayName);
    public abstract Builder setEntityId(@Nullable Long entityId);
    public abstract Builder setEntityName(@Nullable String entityName);
    public abstract Builder setEntityType(@Nullable String entityType);
    public abstract Builder setFamily(@Nullable String family);
    public abstract Builder setFormulaDetail(@Nullable String formulaDetail);
    public abstract Builder setIsDefault(@Nullable Boolean isDefault);
    public abstract Builder setKeyName(@Nullable String keyName);
    public abstract Builder setKeyspace(@Nullable String keyspace);
    public abstract Builder setLookbackPeriod(@Nullable String lookbackPeriod);
    public abstract Builder setLookbackQuantity(@Nullable Long lookbackQuantity);
    public abstract Builder setLookbackSeasonId(@Nullable Long lookbackSeasonId);
    public abstract Builder setLookbackTimeOfUseId(@Nullable Long lookbackTimeOfUseId);
    public abstract Builder setLookbackintervalQuantity(@Nullable Long lookbackintervalQuantity);
    public abstract Builder setMaxValue(@Nullable String maxValue);
    public abstract Builder setMinValue(@Nullable String minValue);
    public abstract Builder setOperator(@Nullable String operator);
    public abstract Builder setPeriod(@Nullable String period);
    public abstract Builder setPrivacy(@Nullable Privacy privacy);
    public abstract Builder setPropertyTypes(@Nullable TariffPropertyType propertyTypes);
    public abstract Builder setPropertyValue(@Nullable String propertyValue);
    public abstract Builder setQuantityKey(@Nullable String quantityKey);
    public abstract Builder setQuantityUnit(@Nullable String quantityUnit);
    public abstract Builder setResourceType(@Nullable String resourceType);

    @JsonIgnore
    public abstract Builder setChoices(@Nullable Choice... choices);

    @JsonProperty("choices")
    public abstract Builder setChoices(@Nullable ImmutableList<Choice> choices);

    @JsonIgnore
    public abstract Builder setLookups(@Nullable PropertyLookup... lookups);

    @JsonProperty("lookups")
    public abstract Builder setLookups(@Nullable ImmutableList<PropertyLookup> lookups);

    protected abstract ImmutableList<Choice> getChoices();
    protected abstract ImmutableList<PropertyLookup> getLookups();
    protected abstract TariffProperty autoBuild();

    public TariffProperty build() {
      setChoices(firstNonNull(getChoices(), ImmutableList.of()));
      setLookups(firstNonNull(getLookups(), ImmutableList.of()));
      return autoBuild();
    }
  }
}
