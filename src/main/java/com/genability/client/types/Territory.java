package com.genability.client.types;

import static com.google.common.base.MoreObjects.firstNonNull;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.genability.client.util.EnumUtil;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

@AutoValue
@JsonDeserialize(builder = AutoValue_Territory.Builder.class)
public abstract class Territory {

  @JsonProperty("itemTypes")
  public String getItemTypesAsString() {
    return EnumUtil.enumListString(getItemTypes());
  }
  
  public abstract @Nullable Coordinates getCenterPoint();
  public abstract @Nullable Boolean getDeregCandi();
  public abstract @Nullable Boolean getDeregRes();
  public abstract @Nullable ImmutableSet<TerritoryType> getItemTypes();
  public abstract @Nullable ImmutableList<TerritoryItem> getItems();
  public abstract @Nullable Long getLseId();
  public abstract @Nullable String getLseName();
  public abstract @Nullable Long getParentTerritoryId();
  public abstract @Nullable Long getTerritoryId();
  public abstract @Nullable String getTerritoryName();
  public abstract @Nullable String getUsageType();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_Territory.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setCenterPoint(@Nullable Coordinates centerPoint);
    public abstract Builder setDeregCandi(@Nullable Boolean deregCandi);
    public abstract Builder setDeregRes(@Nullable Boolean deregRes);
    public abstract Builder setLseId(@Nullable Long lseId);
    public abstract Builder setLseName(@Nullable String lseName);
    public abstract Builder setParentTerritoryId(@Nullable Long parentTerritoryId);
    public abstract Builder setTerritoryId(@Nullable Long territoryId);
    public abstract Builder setTerritoryName(@Nullable String territoryName);
    public abstract Builder setUsageType(@Nullable String usageType);

    @JsonIgnore
    public abstract Builder setItemTypes(@Nullable TerritoryType... itemTypes);

    @JsonIgnore
    public abstract Builder setItemTypes(@Nullable ImmutableSet<TerritoryType> itemTypes);
    
    @JsonProperty("itemTypes")
    public void setItemTypesAsString(String itemTypes) {
      setItemTypes(EnumUtil.parseEnumList(itemTypes, TerritoryType.class));
    }

    @JsonIgnore
    public abstract Builder setItems(@Nullable TerritoryItem... items);

    @JsonProperty("items")
    public abstract Builder setItems(@Nullable ImmutableList<TerritoryItem> items);

    protected abstract ImmutableSet<TerritoryType> getItemTypes();
    protected abstract ImmutableList<TerritoryItem> getItems();
    protected abstract Territory autoBuild();

    public Territory build() {
      setItemTypes(firstNonNull(getItemTypes(), ImmutableSet.of()));
      setItems(firstNonNull(getItems(), ImmutableList.of()));
      return autoBuild();
    }
  }
}
