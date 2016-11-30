package com.genability.client.api.request;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.SortOrder;

abstract class AbstractGetNRequest2 extends AbstractRequest2 {

  public abstract @Nullable Integer getPageStart();
  public abstract @Nullable Integer getPageCount();
  public abstract @Nullable String getSearch();
  public abstract @Nullable String getSearchOn();
  public abstract @Nullable Boolean getStartsWith();
  public abstract @Nullable Boolean getEndsWith();
  public abstract @Nullable Boolean getIsRegex();
  public abstract @Nullable String getSortOn();
  public abstract @Nullable SortOrder getSortOrder();

  public abstract static class Builder<T extends Builder<T>> extends AbstractRequest2.Builder<T> {
   
    public abstract T setPageStart(@Nullable Integer pageStart);
    public abstract T setPageCount(@Nullable Integer pageCount);
    public abstract T setSearch(@Nullable String search);
    public abstract T setSearchOn(@Nullable String searchOn);
    public abstract T setStartsWith(@Nullable Boolean startsWith);
    public abstract T setEndsWith(@Nullable Boolean endsWith);
    public abstract T setIsRegex(@Nullable Boolean isRegex);
    public abstract T setSortOn(@Nullable String sortOn);
    public abstract T setSortOrder(@Nullable SortOrder sortOrder);
  }

  @Override
  @JsonIgnore
  protected QueryParamsBuilder getBaseQueryParams() {
    return super.getBaseQueryParams()
        .addParam("pageStart", getPageStart())
        .addParam("pageCount", getPageCount())
        .addParam("search", getSearch())
        .addParam("searchOn", getSearchOn())
        .addParam("startsWith", getStartsWith())
        .addParam("endsWith", getEndsWith())
        .addParam("isRegex", getIsRegex())
        .addParam("sortOn", getSortOn())
        .addParam("sortOrder", getSortOrder() == null ? null : getSortOrder().getValue());
  }
}
