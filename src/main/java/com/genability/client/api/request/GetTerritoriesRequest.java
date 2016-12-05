package com.genability.client.api.request;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.Fields;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class GetTerritoriesRequest extends AbstractGetNRequest {

  GetTerritoriesRequest() {}

  public abstract @Nullable String getContainsItemType();
  public abstract @Nullable String getContainsItemValue();
  public abstract @Nullable Long getLseId();
  public abstract @Nullable Long getMasterTariffId();
  public abstract @Nullable Boolean getPopulateItems();
  public abstract @Nullable Boolean getPopulateLses();
  public abstract @Nullable ImmutableList<String> getUsageTypes();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_GetTerritoriesRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractGetNRequest.Builder<Builder> {

    public abstract Builder setContainsItemType(@Nullable String containsItemType);
    public abstract Builder setContainsItemValue(@Nullable String containsItemValue);
    public abstract Builder setLseId(@Nullable Long lseId);
    public abstract Builder setMasterTariffId(@Nullable Long masterTariffId);
    public abstract Builder setPopulateItems(@Nullable Boolean populateItems);
    public abstract Builder setPopulateLses(@Nullable Boolean populateLses);
    public abstract Builder setUsageTypes(@Nullable String... usageTypes);
    public abstract Builder setUsageTypes(@Nullable ImmutableList<String> usageTypes);

    public abstract GetTerritoriesRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("containsItemType", getContainsItemType())
        .addParam("containsItemValue", getContainsItemValue())
        .addParam("lseId", getLseId())
        .addParam("masterTariffId", getMasterTariffId())
        .addParam("populateItems", getPopulateItems())
        .addParam("populateLses", getPopulateLses())
        .addParam("usageTypes", getUsageTypes())
        .build();
  }
}
