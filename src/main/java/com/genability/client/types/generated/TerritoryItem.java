package com.genability.client.types.generated;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_TerritoryItem.Builder.class)
public abstract class TerritoryItem {


  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_TerritoryItem.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {


    public abstract TerritoryItem build();
  }
}
