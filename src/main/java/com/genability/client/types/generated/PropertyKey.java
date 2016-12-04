package com.genability.client.types.generated;

import static com.google.common.base.MoreObjects.firstNonNull;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.genability.client.types.DataType;
import com.genability.client.types.Privacy;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
@JsonDeserialize(builder = AutoValue_PropertyKey.Builder.class)
public abstract class PropertyKey {

  public abstract @Nullable ImmutableList<Choice> getChoices();
  public abstract @Nullable DataType getDataType();
  public abstract @Nullable String getDescription();
  public abstract @Nullable String getDisplayName();
  public abstract @Nullable Long getEntityId();
  public abstract @Nullable String getEntityName();
  public abstract @Nullable String getEntityType();
  public abstract @Nullable String getFamily();
  public abstract @Nullable String getFormulaDetail();
  public abstract @Nullable String getKeyName();
  public abstract @Nullable String getKeyspace();
  public abstract @Nullable Integer getLookbackIntervalQuantity();
  public abstract @Nullable String getLookbackPeriod();
  public abstract @Nullable Integer getLookbackQuantity();
  public abstract @Nullable Integer getLookbackSeasonId();
  public abstract @Nullable Long getLookbackTimeOfUseId();
  public abstract @Nullable ImmutableList<PropertyLookup> getLookups();
  public abstract @Nullable Privacy getPrivacy();
  public abstract @Nullable String getQuantityUnit();
  public abstract @Nullable String getResourceType();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_PropertyKey.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setDataType(@Nullable DataType dataType);
    public abstract Builder setDescription(@Nullable String description);
    public abstract Builder setDisplayName(@Nullable String displayName);
    public abstract Builder setEntityId(@Nullable Long entityId);
    public abstract Builder setEntityName(@Nullable String entityName);
    public abstract Builder setEntityType(@Nullable String entityType);
    public abstract Builder setFamily(@Nullable String family);
    public abstract Builder setFormulaDetail(@Nullable String formulaDetail);
    public abstract Builder setKeyName(@Nullable String keyName);
    public abstract Builder setKeyspace(@Nullable String keyspace);
    public abstract Builder setLookbackIntervalQuantity(@Nullable Integer lookbackIntervalQuantity);
    public abstract Builder setLookbackPeriod(@Nullable String lookbackPeriod);
    public abstract Builder setLookbackQuantity(@Nullable Integer lookbackQuantity);
    public abstract Builder setLookbackSeasonId(@Nullable Integer lookbackSeasonId);
    public abstract Builder setLookbackTimeOfUseId(@Nullable Long lookbackTimeOfUseId);
    public abstract Builder setPrivacy(@Nullable Privacy privacy);
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
    protected abstract PropertyKey autoBuild();

    public PropertyKey build() {
      setChoices(firstNonNull(getChoices(), ImmutableList.of()));
      setLookups(firstNonNull(getLookups(), ImmutableList.of()));
      return autoBuild();
    }
  }
}
