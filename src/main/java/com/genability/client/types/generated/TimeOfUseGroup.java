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
@JsonDeserialize(builder = AutoValue_TimeOfUseGroup.Builder.class)
public abstract class TimeOfUseGroup {

  public abstract @Nullable Long getLseId();
  public abstract @Nullable ImmutableList<TimeOfUse> getTimeOfUses();
  public abstract @Nullable Long getTouGroupId();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_TimeOfUseGroup.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setLseId(@Nullable Long lseId);
    public abstract Builder setTouGroupId(@Nullable Long touGroupId);

    @JsonIgnore
    public abstract Builder setTimeOfUses(@Nullable TimeOfUse... timeOfUses);

    @JsonProperty("timeOfUses")
    public abstract Builder setTimeOfUses(@Nullable ImmutableList<TimeOfUse> timeOfUses);

    protected abstract ImmutableList<TimeOfUse> getTimeOfUses();
    protected abstract TimeOfUseGroup autoBuild();

    public TimeOfUseGroup build() {
      setTimeOfUses(firstNonNull(getTimeOfUses(), ImmutableList.of()));
      return autoBuild();
    }
  }
}
