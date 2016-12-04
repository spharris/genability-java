package com.genability.client.types.generated;

import static com.google.common.base.MoreObjects.firstNonNull;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.genability.client.types.AccountStatus;
import com.genability.client.types.AccountType;
import com.genability.client.types.CustomerClass;
import com.genability.client.types.Tariff;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

@AutoValue
@JsonDeserialize(builder = AutoValue_Account.Builder.class)
public abstract class Account {

  public abstract @Nullable String getAccountId();
  public abstract @Nullable String getAccountName();
  public abstract @Nullable Address getAddress();
  public abstract @Nullable CustomerClass getCustomerClass();
  public abstract @Nullable String getCustomerOrgId();
  public abstract @Nullable String getCustomerOrgName();
  public abstract @Nullable String getOwner();
  public abstract @Nullable ImmutableMap<String, PropertyData> getProperties();
  public abstract @Nullable String getProviderAccountId();
  public abstract @Nullable AccountStatus getStatus();
  public abstract @Nullable ImmutableList<Tariff> getTariffs();
  public abstract @Nullable AccountType getType();

  public abstract Builder toBuilder();
  public static Builder builder() {
    return new AutoValue_Account.Builder();
  }

  @AutoValue.Builder
  @JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
  public abstract static class Builder {

    public abstract Builder setAccountId(@Nullable String accountId);
    public abstract Builder setAccountName(@Nullable String accountName);
    public abstract Builder setAddress(@Nullable Address address);
    public abstract Builder setCustomerClass(@Nullable CustomerClass customerClass);
    public abstract Builder setCustomerOrgId(@Nullable String customerOrgId);
    public abstract Builder setCustomerOrgName(@Nullable String customerOrgName);
    public abstract Builder setOwner(@Nullable String owner);
    public abstract Builder setProperties(@Nullable ImmutableMap<String, PropertyData> properties);
    public abstract Builder setProviderAccountId(@Nullable String providerAccountId);
    public abstract Builder setStatus(@Nullable AccountStatus status);
    public abstract Builder setType(@Nullable AccountType type);

    @JsonIgnore
    public abstract Builder setTariffs(@Nullable Tariff... tariffs);

    @JsonProperty("tariffs")
    public abstract Builder setTariffs(@Nullable ImmutableList<Tariff> tariffs);

    protected abstract ImmutableMap<String, PropertyData> getProperties();
    protected abstract ImmutableList<Tariff> getTariffs();
    protected abstract Account autoBuild();

    public Account build() {
      setProperties(firstNonNull(getProperties(), ImmutableMap.of()));
      setTariffs(firstNonNull(getTariffs(), ImmutableList.of()));
      return autoBuild();
    }
  }
}
