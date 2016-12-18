package com.genability.client.api.request;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.genability.client.types.DetailLevel;
import com.genability.client.types.Fields;
import com.genability.client.types.GroupBy;
import com.genability.client.types.PropertyData;
import com.genability.client.types.TariffRate;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class GetCalculatedCostRequest extends AbstractRequest {

  GetCalculatedCostRequest() {}
  
  @JsonCreator
  public static GetCalculatedCostRequest create(
      @Nullable @JsonProperty("fields") Fields fields,
      @Nullable @JsonProperty("accountId") String accountId,
      @Nullable @JsonProperty("accuracy") String accuracy,
      @Nullable @JsonProperty("billingPeriod") Boolean billingPeriod,
      @Nullable @JsonProperty("dataFactor") BigDecimal dataFactor,
      @Nullable @JsonProperty("detailLevel") DetailLevel detailLevel,
      @Nullable @JsonProperty("fromDateTime") ZonedDateTime fromDateTime,
      @Nullable @JsonProperty("groupBy") GroupBy groupBy,
      @Nullable @JsonProperty("includeDefaultProfile") Boolean includeDefaultProfile,
      @Nullable @JsonProperty("masterTariffId") Long masterTariffId,
      @Nullable @JsonProperty("minimums") Boolean minimums,
      @Nullable @JsonProperty("profileId") String profileId,
      @Nullable @JsonProperty("providerAccountId") String providerAccountId,
      @Nullable @JsonProperty("rateInputs") ImmutableList<TariffRate> rateInputs,
      @Nullable @JsonProperty("tariffEffectiveOn") ZonedDateTime tariffEffectiveOn,
      @Nullable @JsonProperty("tariffInputs") ImmutableList<PropertyData> tariffInputs,
      @Nullable @JsonProperty("tariffRateId") Long tariffRateId,
      @Nullable @JsonProperty("territoryId") Long territoryId,
      @Nullable @JsonProperty("toDateTime") ZonedDateTime toDateTime,
      @Nullable @JsonProperty("useMostRecentUsageData") Boolean useMostRecentUsageData,
      @Nullable @JsonProperty("zipCode") String zipCode) {
    return GetCalculatedCostRequest.builder()
        .setAccountId(accountId)
        .setAccuracy(accuracy)
        .setBillingPeriod(billingPeriod)
        .setDataFactor(dataFactor)
        .setDetailLevel(detailLevel)
        .setFromDateTime(fromDateTime)
        .setGroupBy(groupBy)
        .setIncludeDefaultProfile(includeDefaultProfile)
        .setMasterTariffId(masterTariffId)
        .setMinimums(minimums)
        .setProfileId(profileId)
        .setProviderAccountId(providerAccountId)
        .setRateInputs(rateInputs)
        .setTariffEffectiveOn(tariffEffectiveOn)
        .setTariffInputs(tariffInputs)
        .setTariffRateId(tariffRateId)
        .setTerritoryId(territoryId)
        .setToDateTime(toDateTime)
        .setUseMostRecentUsageData(useMostRecentUsageData)
        .setZipCode(zipCode)
        .build();
  }

  public abstract @Nullable String getAccountId();
  public abstract @Nullable String getAccuracy();
  public abstract @Nullable Boolean getBillingPeriod();
  public abstract @Nullable BigDecimal getDataFactor();
  public abstract @Nullable DetailLevel getDetailLevel();
  public abstract @Nullable ZonedDateTime getFromDateTime();
  public abstract @Nullable GroupBy getGroupBy();
  public abstract @Nullable Boolean getIncludeDefaultProfile();
  public abstract @Nullable Long getMasterTariffId();
  public abstract @Nullable Boolean getMinimums();
  public abstract @Nullable String getProfileId();
  public abstract @Nullable String getProviderAccountId();
  public abstract @Nullable ImmutableList<TariffRate> getRateInputs();
  public abstract @Nullable ZonedDateTime getTariffEffectiveOn();
  public abstract @Nullable ImmutableList<PropertyData> getTariffInputs();
  public abstract @Nullable Long getTariffRateId();
  public abstract @Nullable Long getTerritoryId();
  public abstract @Nullable ZonedDateTime getToDateTime();
  public abstract @Nullable Boolean getUseMostRecentUsageData();
  public abstract @Nullable String getZipCode();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_GetCalculatedCostRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractRequest.Builder<Builder> {

    public abstract Builder setAccountId(@Nullable String accountId);
    public abstract Builder setAccuracy(@Nullable String accuracy);
    public abstract Builder setBillingPeriod(@Nullable Boolean billingPeriod);
    public abstract Builder setDataFactor(@Nullable BigDecimal dataFactor);
    public abstract Builder setDetailLevel(@Nullable DetailLevel detailLevel);
    public abstract Builder setFromDateTime(@Nullable ZonedDateTime fromDateTime);
    public abstract Builder setGroupBy(@Nullable GroupBy groupBy);
    public abstract Builder setIncludeDefaultProfile(@Nullable Boolean includeDefaultProfile);
    public abstract Builder setMasterTariffId(@Nullable Long masterTariffId);
    public abstract Builder setMinimums(@Nullable Boolean minimums);
    public abstract Builder setProfileId(@Nullable String profileId);
    public abstract Builder setProviderAccountId(@Nullable String providerAccountId);
    public abstract Builder setRateInputs(@Nullable TariffRate... rateInputs);
    public abstract Builder setRateInputs(@Nullable ImmutableList<TariffRate> rateInputs);
    public abstract Builder setTariffEffectiveOn(@Nullable ZonedDateTime tariffEffectiveOn);
    public abstract Builder setTariffInputs(@Nullable PropertyData... tariffInputs);
    public abstract Builder setTariffInputs(@Nullable ImmutableList<PropertyData> tariffInputs);
    public abstract Builder setTariffRateId(@Nullable Long tariffRateId);
    public abstract Builder setTerritoryId(@Nullable Long territoryId);
    public abstract Builder setToDateTime(@Nullable ZonedDateTime toDateTime);
    public abstract Builder setUseMostRecentUsageData(@Nullable Boolean useMostRecentUsageData);
    public abstract Builder setZipCode(@Nullable String zipCode);

    public abstract GetCalculatedCostRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("accountId", getAccountId())
        .addParam("accuracy", getAccuracy())
        .addParam("billingPeriod", getBillingPeriod())
        .addParam("dataFactor", getDataFactor())
        .addParam("detailLevel", getDetailLevel())
        .addParam("fromDateTime", getFromDateTime())
        .addParam("groupBy", getGroupBy())
        .addParam("includeDefaultProfile", getIncludeDefaultProfile())
        .addParam("masterTariffId", getMasterTariffId())
        .addParam("minimums", getMinimums())
        .addParam("profileId", getProfileId())
        .addParam("providerAccountId", getProviderAccountId())
        .addParam("rateInputs", getRateInputs())
        .addParam("tariffEffectiveOn", getTariffEffectiveOn())
        .addParam("tariffInputs", getTariffInputs())
        .addParam("tariffRateId", getTariffRateId())
        .addParam("territoryId", getTerritoryId())
        .addParam("toDateTime", getToDateTime())
        .addParam("useMostRecentUsageData", getUseMostRecentUsageData())
        .addParam("zipCode", getZipCode())
        .build();
  }
}
