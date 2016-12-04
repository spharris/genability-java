package com.genability.client.types.generated;

import static com.google.common.base.MoreObjects.firstNonNull;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
@JsonDeserialize(builder = AutoValue_SeasonGroup.Builder.class)
public abstract class SeasonGroup {

  public abstract @Nullable Long getLseId();
  public abstract @Nullable Long getSeasonGroupId();
  public abstract @Nullable ImmutableList<Season> getSeasons();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_SeasonGroup.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setLseId(@Nullable Long lseId);
    public abstract Builder setSeasonGroupId(@Nullable Long seasonGroupId);

    @JsonIgnore
    public abstract Builder setSeasons(@Nullable Season... seasons);

    @JsonProperty("seasons")
    public abstract Builder setSeasons(@Nullable ImmutableList<Season> seasons);

    protected abstract ImmutableList<Season> getSeasons();
    protected abstract SeasonGroup autoBuild();

    public SeasonGroup build() {
      setSeasons(firstNonNull(getSeasons(), ImmutableList.of()));
      return autoBuild();
    }
  }
}
