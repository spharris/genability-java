package com.genability.client.types;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class Response<T> {

  public static final String STATUS_SUCCESS = "success";
  public static final String STATUS_ERROR = "error";
  
  public abstract @Nullable String getStatus();
  public abstract @Nullable String getType();
  public abstract @Nullable ImmutableList<T> getResults();
  public abstract @Nullable Integer getPageStart();
  public abstract @Nullable Integer getPageCount();
  public abstract @Nullable Integer getCount();
  
  // Unfortunately Jackson doesn't work with parameterized builders!
  @JsonCreator
  public static <T> Response<T> create(
      @JsonProperty("status") String status,
      @JsonProperty("type") String type,
      @JsonProperty("results") ImmutableList<T> results,
      @JsonProperty("pageStart") Integer pageStart,
      @JsonProperty("pageCount") Integer pageCount,
      @JsonProperty("count") Integer count) {
    return new AutoValue_Response.Builder<T>()
        .setStatus(status)
        .setType(type)
        .setResults(results)
        .setPageStart(pageStart)
        .setPageCount(pageCount)
        .setCount(count)
        .build();
  }
  
  public abstract Builder<T> toBuilder();
  public static <T> Builder<T> builder() {
    return new AutoValue_Response.Builder<T>();
  }
  
  /** Static factory for a single element response */
  public static <T> Response<T> of(T result) {
    return Response.<T>builder()
        .setStatus(STATUS_SUCCESS)
        .setCount(1)
        .setType(formatClassName(result.getClass()))
        .setResults(ImmutableList.of(result))
        .build();
  }
  
  private static String formatClassName(Class<?> klazz) {
    if (klazz.getSimpleName().startsWith("AutoValue")) {
      return klazz.getSuperclass().getSimpleName();
    } else {
      return klazz.getSimpleName();
    }
  }
  
  @AutoValue.Builder
  public abstract static class Builder<T> {
    public abstract Builder<T> setStatus(@Nullable String status);
    public abstract Builder<T> setType(@Nullable String type);
    public abstract Builder<T> setResults(@Nullable ImmutableList<T> results);
    public abstract Builder<T> setPageStart(@Nullable Integer pageStart);
    public abstract Builder<T> setPageCount(@Nullable Integer pageCount);
    public abstract Builder<T> setCount(@Nullable Integer count);
    
    public abstract Response<T> build();
  }
}


