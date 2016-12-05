package com.genability.client.api.request;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.ClipBy;
import com.genability.client.types.Fields;
import com.genability.client.types.GroupBy;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class GetProfileRequest extends AbstractGetNRequest {

  GetProfileRequest() {}

  public abstract @Nullable ClipBy getClipBy();
  public abstract @Nullable Long getDemandInterval();
  public abstract @Nullable Boolean getDeriveConsumption();
  public abstract @Nullable Boolean getDeriveDemand();
  public abstract @Nullable DateTime getFromDateTime();
  public abstract @Nullable GroupBy getGroupBy();
  public abstract @Nullable Boolean getPopulateBaseline();
  public abstract @Nullable Boolean getPopulateReadings();
  public abstract @Nullable String getProfileId();
  public abstract @Nullable String getProviderProfileId();
  public abstract @Nullable DateTime getToDateTime();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_GetProfileRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractGetNRequest.Builder<Builder> {

    public abstract Builder setClipBy(@Nullable ClipBy clipBy);
    public abstract Builder setDemandInterval(@Nullable Long demandInterval);
    public abstract Builder setDeriveConsumption(@Nullable Boolean deriveConsumption);
    public abstract Builder setDeriveDemand(@Nullable Boolean deriveDemand);
    public abstract Builder setFromDateTime(@Nullable DateTime fromDateTime);
    public abstract Builder setGroupBy(@Nullable GroupBy groupBy);
    public abstract Builder setPopulateBaseline(@Nullable Boolean populateBaseline);
    public abstract Builder setPopulateReadings(@Nullable Boolean populateReadings);
    public abstract Builder setProfileId(@Nullable String profileId);
    public abstract Builder setProviderProfileId(@Nullable String providerProfileId);
    public abstract Builder setToDateTime(@Nullable DateTime toDateTime);

    public abstract GetProfileRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("clipBy", getClipBy())
        .addParam("demandInterval", getDemandInterval())
        .addParam("deriveConsumption", getDeriveConsumption())
        .addParam("deriveDemand", getDeriveDemand())
        .addParam("fromDateTime", getFromDateTime())
        .addParam("groupBy", getGroupBy())
        .addParam("populateBaseline", getPopulateBaseline())
        .addParam("populateReadings", getPopulateReadings())
        .addParam("providerProfileId", getProviderProfileId())
        .addParam("toDateTime", getToDateTime())
        .build();
  }
}
