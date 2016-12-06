package com.genability.client.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genability.client.api.request.BaselineRequest;
import com.genability.client.api.request.DeleteAccountRequest;
import com.genability.client.api.service.AccountService;
import com.genability.client.api.service.ProfileService;
import com.genability.client.api.service.TypicalService;
import com.genability.client.types.Account;
import com.genability.client.types.Baseline;
import com.genability.client.types.Profile;
import com.genability.client.types.PropertyData;
import com.genability.client.types.ReadingData;
import com.genability.client.types.Response;
import com.genability.client.types.Tariff;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/** Utility for loading test data */
public class DataLoaderUtil {

  private final AccountService accountService;
  private final ProfileService profileService;
  private final TypicalService typicalService;
  private final ObjectMapper mapper;
  
  @Inject
  DataLoaderUtil(AccountService accountService, ProfileService profileService,
      TypicalService typicalService, ObjectMapper mapper) {
    this.accountService = accountService;
    this.profileService = profileService;
    this.typicalService = typicalService;
    this.mapper = mapper;
  }
  
  public Account createAccount() {
    Account addAccount = Account.builder()
        .setAccountName("Java Client Lib Test Add Account - CAN DELETE")
        .setProviderAccountId("TEST-" + UUID.randomUUID())
        .setProperties(ImmutableMap.<String, PropertyData>builder()
          .put("territoryId", PropertyData.builder()
            .setKeyName("territoryId")
            .setDataValue("3538")
            .build())
          .put("zipCode", PropertyData.builder()
            .setKeyName("zipCode")
            .setDataValue("94115")
            .build())
          .build())
        .setTariffs(Tariff.builder()
          .setMasterTariffId(512L)
          .build())
        .build();

    try {
      Response<Account> restResponse = accountService.addAccount(addAccount).get();

      assertNotNull("new account response is null", restResponse);
      assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
      assertTrue("bad count", restResponse.getCount() > 0);

      Account newAccount = null;
      for (Account account : restResponse.getResults()) {
        newAccount = account;
        assertNotNull("accountId null", account.getAccountId());
      }

      return newAccount;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public Profile createProfile() throws Exception {

    Account account = createAccount();
    Profile addProfile = Profile.builder()
        .setAccountId(account.getAccountId())
        .setProviderProfileId("TEST-" + UUID.randomUUID())
        .build();
    Response<Profile> restResponse = profileService.addProfile(addProfile).get();

    assertNotNull("new Profile response is null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("bad count", restResponse.getCount() > 0);

    Profile newProfile = null;
    for (Profile profile : restResponse.getResults()) {
      newProfile = profile;
      assertNotNull("profileId null", profile.getProfileId());
    }
    return newProfile;
  }

  public Profile createProfileWithReadings(List<ReadingData> readings) throws Exception {

    Account account = createAccount();
    Profile addProfile = Profile.builder()
        .setAccountId(account.getAccountId())
        .setReadingData(ImmutableList.copyOf(readings))
        .build();

    Response<Profile> restResponse = profileService.addProfile(addProfile).get();

    assertNotNull("new Profile response is null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("bad count", restResponse.getCount() > 0);

    Profile newProfile = null;
    for (Profile profile : restResponse.getResults()) {
      newProfile = profile;
      assertNotNull("profileId null", profile.getProfileId());
    }
    return newProfile;
  }

  // Delete the accounts we create to keep things clean; this also cleans up profiles
  // on the account
  public void cleanup(String accountId) {
    DeleteAccountRequest deleteAccountRequest = DeleteAccountRequest.builder()
        .setHardDelete(Boolean.TRUE)
        .setAccountId(accountId)
        .build();
    
    try {
      Response<Account> deleteResponse = accountService.deleteAccount(deleteAccountRequest).get();
      assertEquals("bad status", deleteResponse.getStatus(), Response.STATUS_SUCCESS);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  // get a baseline. will be used to upload along with a profile
  public Baseline getSolarBaselineFor92704() throws Exception {
    BaselineRequest request = BaselineRequest.builder()
        .setZipCode("92704")
        .build();

    Response<Baseline> response = typicalService.getSolarBaseline(request).get();

    if (response.getStatus().equals(Response.STATUS_SUCCESS)) {
      return response.getResults().get(0);
    } else {
      return null;
    }
  }

  public <T> T loadJsonFixture(String fileName, TypeReference<T> resultTypeReference) {
    try {
      InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
      return mapper.readValue(is, resultTypeReference);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
