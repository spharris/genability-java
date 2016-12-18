package com.genability.client.api.request;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import org.apache.http.NameValuePair;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.genability.client.types.Fields;
import com.genability.client.types.PropertyData;
import com.genability.client.types.TariffRate;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

@AutoValue
public abstract class AccountAnalysisRequest extends AbstractRequest {

  /*
   * This special timezone is used to indicate that a particular ZonedDateTime should be serialized as a
   * date only (i.e. exclude the time portion of the DateTime)
   */
  private static final ZoneId DATE_ONLY_TIMEZONE = ZoneOffset.MAX;

  AccountAnalysisRequest() {}

  public abstract @Nullable String getAccountId();

  @JsonSerialize(using = DateTimeSerializer.class)
  public abstract @Nullable ZonedDateTime getFromDateTime();

  public abstract @Nullable Boolean getPopulateCosts();
  public abstract @Nullable ImmutableList<PropertyData> getPropertyInputs();
  public abstract @Nullable String getProviderAccountId();
  public abstract @Nullable ImmutableList<TariffRate> getRateInputs();

  @JsonSerialize(using = DateTimeSerializer.class)
  public abstract @Nullable ZonedDateTime getToDateTime();

  public abstract @Nullable Boolean getUseIntelligentBaselining();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_AccountAnalysisRequest.Builder()
        .setFields(Fields.EXT);
  }

  @AutoValue.Builder
  public abstract static class Builder extends AbstractRequest.Builder<Builder> {

    @JsonProperty public abstract Builder setFromDateTime(@Nullable ZonedDateTime fromDateTime);
    @JsonProperty public abstract Builder setToDateTime(@Nullable ZonedDateTime toDateTime);

    public abstract Builder setAccountId(@Nullable String accountId);
    public abstract Builder setPopulateCosts(@Nullable Boolean populateCosts);
    public abstract Builder setPropertyInputs(@Nullable PropertyData... propertyInputs);
    public abstract Builder setPropertyInputs(@Nullable ImmutableList<PropertyData> propertyInputs);
    public abstract Builder setProviderAccountId(@Nullable String providerAccountId);
    public abstract Builder setRateInputs(@Nullable TariffRate... rateInputs);
    public abstract Builder setRateInputs(@Nullable ImmutableList<TariffRate> rateInputs);
    public abstract Builder setUseIntelligentBaselining(@Nullable Boolean useIntelligentBaselining);

    /**
     * This method is used to set the fromDateTime as a date only. The resulting request will include
     * the following: "fromDateTime":"YYYY-MM-DD"
     * 
     * @param year The year.
     * @param month The month.
     * @param day The day.
     */
    @JsonIgnore
    public Builder setFromDateTime(int year, int month, int day) {
      setFromDateTime(LocalDate.of(year, month, day));
      return this;
    }

    /**
     * This method is used to set the fromDateTime as a date only. The resulting request will include
     * the following: "fromDateTime":"YYYY-MM-DD"
     * 
     * @param date The date.
     */
    @JsonIgnore
    public Builder setFromDateTime(LocalDate date) {
      setFromDateTime(convertLocalDate(date));
      return this;
    }

    /**
     * This method is used to set the toDateTime as a date only. The resulting request will include
     * the following: "toDateTime":"YYYY-MM-DD"
     * 
     * @param year The year.
     * @param month The month.
     * @param day The day.
     */
    @JsonIgnore
    public Builder setToDateTime(int year, int month, int day) {
      setToDateTime(LocalDate.of(year, month, day));
      return this;
    }

    /**
     * This method is used to set the toDateTime as a date only. The resulting request will include
     * the following: "toDateTime":"YYYY-MM-DD"
     * 
     * @param date The date.
     */
    @JsonIgnore
    public Builder setToDateTime(LocalDate date) {
      setToDateTime(convertLocalDate(date));
      return this;
    }

    public abstract AccountAnalysisRequest build();

    private ZonedDateTime convertLocalDate(LocalDate date) {
      ZonedDateTime dt = date.atStartOfDay(DATE_ONLY_TIMEZONE);
      return dt;
    }


  }

  /*
   * This class is used to serialize ZonedDateTime objects. In the special case where the timeZone is set
   * to AccountAnalysisRequest.DATE_ONLY_TIMEZONE, the result will contain the date only
   */
  protected static class DateTimeSerializer extends JsonSerializer<ZonedDateTime> {

    @Override
    public void serialize(ZonedDateTime value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException, JsonProcessingException {

      if (DATE_ONLY_TIMEZONE.equals(value.getZone())) {
        jgen.writeObject(value.toLocalDate());
      } else {
        jgen.writeObject(value);
      }

    }
  }


  @Override
  @JsonIgnore
  public ImmutableList<NameValuePair> getQueryParams() {
    return getBaseQueryParams()
        .addParam("accountId", getAccountId())
        .addParam("fromDateTime", getFromDateTime())
        .addParam("populateCosts", getPopulateCosts())
        .addParam("propertyInputs", getPropertyInputs())
        .addParam("providerAccountId", getProviderAccountId())
        .addParam("rateInputs", getRateInputs())
        .addParam("toDateTime", getToDateTime())
        .addParam("useIntelligentBaselining", getUseIntelligentBaselining())
        .build();
  }
}
