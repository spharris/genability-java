package com.genability.client.types;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class PagedList<T> {

  public abstract @Nullable ImmutableList<T> getList();
  public abstract @Nullable PaginationInfo getPaginationInfo();
  
  public abstract Builder<T> toBuilder();
  public static <T> Builder<T> builder() {
    return new AutoValue_PagedList.Builder<T>();
  }

  @JsonCreator
  public static <T> PagedList<T> create(
      @JsonProperty("list") ImmutableList<T> list,
      @JsonProperty("paginationInfo") PaginationInfo paginationInfo) {
    return new AutoValue_PagedList.Builder<T>()
        .setList(list)
        .setPaginationInfo(paginationInfo)
        .build();
  }
  
  @AutoValue.Builder
  public abstract static class Builder<T> {
    public abstract Builder<T> setList(@Nullable ImmutableList<T> list);
    public abstract Builder<T> setPaginationInfo(@Nullable PaginationInfo paginationInfo);
    
    public abstract PagedList<T> build();
  }
}
