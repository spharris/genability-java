package com.genability.client.api.request;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.Fields;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class GetTerritoryRequest extends AbstractRequest {

  GetTerritoryRequest() {}

  public abstract @Nullable Boolean getPopulateItems();
  public abstract @Nullable Boolean getPopulateLses();
  public abstract @Nullable Long getTerritoryId();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_GetTerritoryRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractRequest.Builder<Builder> {

    public abstract Builder setPopulateItems(@Nullable Boolean populateItems);
    public abstract Builder setPopulateLses(@Nullable Boolean populateLses);
    public abstract Builder setTerritoryId(@Nullable Long territoryId);

    public abstract GetTerritoryRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("populateItems", getPopulateItems())
        .addParam("populateLses", getPopulateLses())
        .build();
  }
}
