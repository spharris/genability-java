package com.genability.client.types;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_Coordinates.Builder.class)
public abstract class Coordinates {

  public abstract @Nullable Double getLatitude();
  public abstract @Nullable Double getLongitude();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_Coordinates.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setLatitude(@Nullable Double latitude);
    public abstract Builder setLongitude(@Nullable Double longitude);

    public abstract Coordinates build();
  }
}
