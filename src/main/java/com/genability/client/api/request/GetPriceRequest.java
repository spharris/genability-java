package com.genability.client.api.request;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.genability.client.types.Fields;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class GetPriceRequest extends AbstractRequest {

  GetPriceRequest() {}

  public abstract @Nullable BigDecimal getConsumptionAmount();
  public abstract @Nullable BigDecimal getDemandAmount();
  public abstract @Nullable DateTime getFromDateTime();
  public abstract @Nullable Long getMasterTariffId();
  public abstract @Nullable DateTime getToDateTime();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_GetPriceRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractRequest.Builder<Builder> {

    public abstract Builder setConsumptionAmount(@Nullable BigDecimal consumptionAmount);
    public abstract Builder setDemandAmount(@Nullable BigDecimal demandAmount);
    public abstract Builder setFromDateTime(@Nullable DateTime fromDateTime);
    public abstract Builder setMasterTariffId(@Nullable Long masterTariffId);
    public abstract Builder setToDateTime(@Nullable DateTime toDateTime);

    public abstract GetPriceRequest build();
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("consumptionAmount", getConsumptionAmount())
        .addParam("demandAmount", getDemandAmount())
        .addParam("fromDateTime", getFromDateTime())
        .addParam("masterTariffId", getMasterTariffId())
        .addParam("toDateTime", getToDateTime())
        .build();
  }
}
