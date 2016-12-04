package com.genability.client.types.generated;

import static com.google.common.base.MoreObjects.firstNonNull;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

@AutoValue
@JsonDeserialize(builder = AutoValue_Profile.Builder.class)
public abstract class Profile {

  public abstract @Nullable String getAccountId();
  public abstract @Nullable ImmutableList<BaselineMeasure> getBaselineMeasures();
  public abstract @Nullable Integer getDataStatus();
  public abstract @Nullable String getDescription();
  public abstract @Nullable ImmutableList<IntervalInfo> getIntervals();
  public abstract @Nullable Boolean getIsDefault();
  public abstract @Nullable String getMeterId();
  public abstract @Nullable String getProfileId();
  public abstract @Nullable String getProfileName();
  public abstract @Nullable ImmutableMap<String, PropertyData> getProperties();
  public abstract @Nullable String getProviderAccountId();
  public abstract @Nullable String getProviderProfileId();
  public abstract @Nullable ImmutableList<ReadingData> getReadingData();
  public abstract @Nullable ImmutableList<ReadingDataSummary> getReadingDataSummaries();
  public abstract @Nullable String getServiceTypes();
  public abstract @Nullable Source getSource();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_Profile.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setAccountId(@Nullable String accountId);
    public abstract Builder setDataStatus(@Nullable Integer dataStatus);
    public abstract Builder setDescription(@Nullable String description);
    public abstract Builder setIsDefault(@Nullable Boolean isDefault);
    public abstract Builder setMeterId(@Nullable String meterId);
    public abstract Builder setProfileId(@Nullable String profileId);
    public abstract Builder setProfileName(@Nullable String profileName);
    public abstract Builder setProperties(@Nullable ImmutableMap<String, PropertyData> properties);
    public abstract Builder setProviderAccountId(@Nullable String providerAccountId);
    public abstract Builder setProviderProfileId(@Nullable String providerProfileId);
    public abstract Builder setServiceTypes(@Nullable String serviceTypes);
    public abstract Builder setSource(@Nullable Source source);

    @JsonIgnore
    public abstract Builder setBaselineMeasures(@Nullable BaselineMeasure... baselineMeasures);

    @JsonProperty("baselineMeasures")
    public abstract Builder setBaselineMeasures(@Nullable ImmutableList<BaselineMeasure> baselineMeasures);

    @JsonIgnore
    public abstract Builder setIntervals(@Nullable IntervalInfo... intervals);

    @JsonProperty("intervals")
    public abstract Builder setIntervals(@Nullable ImmutableList<IntervalInfo> intervals);

    @JsonIgnore
    public abstract Builder setReadingData(@Nullable ReadingData... readingData);

    @JsonProperty("readingData")
    public abstract Builder setReadingData(@Nullable ImmutableList<ReadingData> readingData);

    @JsonIgnore
    public abstract Builder setReadingDataSummaries(@Nullable ReadingDataSummary... readingDataSummaries);

    @JsonProperty("readingDataSummaries")
    public abstract Builder setReadingDataSummaries(@Nullable ImmutableList<ReadingDataSummary> readingDataSummaries);

    protected abstract ImmutableList<BaselineMeasure> getBaselineMeasures();
    protected abstract ImmutableList<IntervalInfo> getIntervals();
    protected abstract ImmutableMap<String, PropertyData> getProperties();
    protected abstract ImmutableList<ReadingData> getReadingData();
    protected abstract ImmutableList<ReadingDataSummary> getReadingDataSummaries();
    protected abstract Profile autoBuild();

    public Profile build() {
      setBaselineMeasures(firstNonNull(getBaselineMeasures(), ImmutableList.of()));
      setIntervals(firstNonNull(getIntervals(), ImmutableList.of()));
      setProperties(firstNonNull(getProperties(), ImmutableMap.of()));
      setReadingData(firstNonNull(getReadingData(), ImmutableList.of()));
      setReadingDataSummaries(firstNonNull(getReadingDataSummaries(), ImmutableList.of()));
      return autoBuild();
    }
  }
}
