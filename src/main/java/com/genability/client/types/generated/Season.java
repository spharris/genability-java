package com.genability.client.types.generated;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_Season.Builder.class)
public abstract class Season {

  public abstract @Nullable Long getLseId();
  public abstract @Nullable Integer getSeasonFromDay();
  public abstract @Nullable Integer getSeasonFromMonth();
  public abstract @Nullable Long getSeasonGroupId();
  public abstract @Nullable Long getSeasonId();
  public abstract @Nullable String getSeasonName();
  public abstract @Nullable Integer getSeasonToDay();
  public abstract @Nullable Integer getSeasonToMonth();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_Season.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setLseId(@Nullable Long lseId);
    public abstract Builder setSeasonFromDay(@Nullable Integer seasonFromDay);
    public abstract Builder setSeasonFromMonth(@Nullable Integer seasonFromMonth);
    public abstract Builder setSeasonGroupId(@Nullable Long seasonGroupId);
    public abstract Builder setSeasonId(@Nullable Long seasonId);
    public abstract Builder setSeasonName(@Nullable String seasonName);
    public abstract Builder setSeasonToDay(@Nullable Integer seasonToDay);
    public abstract Builder setSeasonToMonth(@Nullable Integer seasonToMonth);

    public abstract Season build();
  }
}
