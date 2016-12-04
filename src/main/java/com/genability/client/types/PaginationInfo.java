package com.genability.client.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = AutoValue_PaginationInfo.Builder.class)
public abstract class PaginationInfo {

  public abstract int getTotalCount();
  public abstract int getPageCount();
  public abstract int getPageStart();
  
  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_PaginationInfo.Builder();
  }
  
  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {
   
    public abstract Builder setTotalCount(int totalCount);
    public abstract Builder setPageCount(int pageCount);
    public abstract Builder setPageStart(int pageStart);
    
    public abstract PaginationInfo build();
  }
}
