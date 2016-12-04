package com.genability.client.types.generated;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_BaselineMeasure.Builder.class)
public abstract class BaselineMeasure {

  public abstract @Nullable Integer getI();
  public abstract @Nullable BigDecimal getV();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_BaselineMeasure.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setI(@Nullable Integer i);
    public abstract Builder setV(@Nullable BigDecimal v);

    public abstract BaselineMeasure build();
  }
}
