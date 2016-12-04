package com.genability.client.types.generated;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
@JsonDeserialize(builder = AutoValue_PagedList.Builder.class)
public abstract class PagedList<T> {

  public abstract ImmutableList<T> getList();
  public abstract PaginationInfo getPaginationInfo();
  
  public abstract Builder<T> toBuilder();
  public Builder<T> builder() {
    return new AutoValue_PagedList.Builder<T>();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder<T> {
    public abstract Builder<T> setList(ImmutableList<T> list);
    public abstract Builder<T> setPaginationInfo(PaginationInfo paginationInfo);
    
    public abstract PagedList<T> build();
  }
}
