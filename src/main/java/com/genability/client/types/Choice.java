package com.genability.client.types;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_Choice.Builder.class)
public abstract class Choice {

  public abstract @Nullable String getDataValue();
  public abstract @Nullable String getDisplayValue();
  public abstract @Nullable BigDecimal getLikelihood();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_Choice.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setDataValue(@Nullable String dataValue);
    public abstract Builder setDisplayValue(@Nullable String displayValue);
    public abstract Builder setLikelihood(@Nullable BigDecimal likelihood);

    public abstract Choice build();
  }
}
