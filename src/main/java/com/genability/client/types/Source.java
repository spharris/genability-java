package com.genability.client.types;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_Source.Builder.class)
public abstract class Source {

  public abstract @Nullable String getName();
  public abstract @Nullable String getSourceId();
  public abstract @Nullable String getSourceVersion();
  public abstract @Nullable String getType();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_Source.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setName(@Nullable String name);
    public abstract Builder setSourceId(@Nullable String sourceId);
    public abstract Builder setSourceVersion(@Nullable String sourceVersion);
    public abstract Builder setType(@Nullable String type);

    public abstract Source build();
  }
}
