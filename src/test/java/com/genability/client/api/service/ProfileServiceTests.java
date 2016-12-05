package com.genability.client.api.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.genability.client.api.request.DeleteProfileRequest;
import com.genability.client.api.request.GetProfileRequest;
import com.genability.client.api.request.GetProfilesRequest;
import com.genability.client.api.request.ReadingDataRequest;
import com.genability.client.types.Account;
import com.genability.client.types.Baseline;
import com.genability.client.types.ClipBy;
import com.genability.client.types.GroupBy;
import com.genability.client.types.Profile;
import com.genability.client.types.ReadingData;
import com.genability.client.types.Response;
import com.genability.client.types.Source;
import com.google.common.collect.ImmutableList;

@RunWith(JUnit4.class)
public class ProfileServiceTests extends BaseServiceTests {

  private static ProfileService profileService = genabilityClient.getProfileService();

  @Test
  public void testGetProfile() {

    Profile newProfile = createProfile();
    GetProfileRequest request = GetProfileRequest.builder()
        .setProfileId(newProfile.getProfileId())
        .setGroupBy(GroupBy.MONTH)
        .setClipBy(ClipBy.OUTER)
        .setPopulateReadings(true)
        .build();
    callGetProfile("Test get one profile", request);

    cleanup(newProfile.getAccountId());
  }

  @Test
  public void testGetProfiles() {

    Profile newProfile = createProfile();
    GetProfilesRequest request = GetProfilesRequest.builder()
        .setAccountId(newProfile.getAccountId())
        .build();
    callGetProfiles("Test get all profiles", request);

    cleanup(newProfile.getAccountId());
  }

  @Test
  public void testAddProfileWithBaseline() {
    Account account = createAccount();
    try {
      Baseline solarBaseline = getSolarBaselineFor92704();
      Profile theProfile = Profile.builder()
          .setAccountId(account.getAccountId())
          .setBaselineMeasures(solarBaseline.getMeasures())
          .setServiceTypes("SOLAR_PV")
          .setSource(Source.builder()
            .setSourceId("BaselineModel")
            .build())
          .build();

      Response<Profile> response = profileService.addProfile(theProfile);
      Profile returnedProfile = response.getResults().get(0);

      assertEquals("Did not successfully add the Baseline Profile", Response.STATUS_SUCCESS,
          response.getStatus());
      assertEquals("Source not set correctly", "BaselineModel",
          returnedProfile.getSource().getSourceId());
      assertNotNull("BaselineMeasures was null", returnedProfile.getBaselineMeasures());
      assertEquals("Didn't have the correct number of measures", 8760,
          returnedProfile.getBaselineMeasures().size());

    } finally {
      cleanup(account.getAccountId());
    }
  }

  @Test
  public void testPopulateBaselineParameter() {
    Account account = createAccount();
    try {
      Baseline solarBaseline = getSolarBaselineFor92704();
      Profile theProfile = Profile.builder()
          .setAccountId(account.getAccountId())
          .setBaselineMeasures(solarBaseline.getMeasures())
          .setServiceTypes("SOLAR_PV")
          .setSource(Source.builder()
            .setSourceId("BaselineModel")
            .build())
          .build();

      Response<Profile> addProfileResponse = profileService.addProfile(theProfile);
      Profile addedProfile = addProfileResponse.getResults().get(0);

      GetProfileRequest request = GetProfileRequest.builder()
          .setProfileId(addedProfile.getProfileId())
          .setPopulateBaseline(true)
          .build();
      Response<Profile> getProfileResponse = profileService.getProfile(request);

      Profile retrievedProfile = getProfileResponse.getResults().get(0);

      assertNotNull("No profile was returned", retrievedProfile);
      assertNotNull("No baseline measures were returned", retrievedProfile.getBaselineMeasures());
      assertEquals("Wrong number of baseline measures", 8760,
          retrievedProfile.getBaselineMeasures().size());
    } finally {
      cleanup(account.getAccountId());
    }
  }

  @Test
  public void testAddProfile() {

    Account account = createAccount();
    Profile profile = Profile.builder()
        .setAccountId(account.getAccountId())
        .build();
    Response<Profile> newProfile = profileService.addProfile(profile);

    assertNotNull("new Profile is null", newProfile);
    assertEquals("bad status", newProfile.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("bad count", newProfile.getCount() > 0);

    cleanup(account.getAccountId());
  }

  @Test
  public void testDeleteProfileByProfileId() {
    Profile newProfile = createProfile();
    try {
      String profileId = newProfile.getProfileId();

      GetProfileRequest request = GetProfileRequest.builder()
          .setProfileId(profileId)
          .build();
      Profile returnedProfile = callGetProfile("testDeleteProfileByProfileId first get", request);
      assertEquals("profileId mismatch on first get", profileId, returnedProfile.getProfileId());

      callDeleteProfile(profileId);

      GetProfileRequest request2 = GetProfileRequest.builder()
          .setProfileId(profileId)
          .build();
      try {
        profileService.getProfile(request2);
        fail("second get (after delete) should 404");
      } catch (GenabilityException e) {
        // XXX should handle HTTP codes cleanly in the exception
        // 2015-04-16: Error code on server side is now incorrect.
        if (!"Failed : HTTP error code : 400".equals(e.getMessage())) {
          throw e;
        }
      }
    } finally {
      cleanup(newProfile.getAccountId());
    }
  }

  @Test
  public void testAddUpdateReadings() {

    Account account = createAccount();
    Profile profile = Profile.builder()
        .setAccountId(account.getAccountId())
        .build();
    Response<Profile> results = profileService.addProfile(profile);

    assertNotNull("new Profile is null", results);
    assertEquals("bad status", results.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("bad count", results.getCount() > 0);

    profile = results.getResults().get(0);

    List<ReadingData> readings = new ArrayList<ReadingData>();

    // add two months of readings
    DateTime fromDateTime1 = new DateTime(2014, 1, 1, 1, 0, 0, 0, DateTimeZone.forID("US/Pacific"));
    DateTime toDateTime1 = new DateTime(2014, 2, 1, 1, 0, 0, 0, DateTimeZone.forID("US/Pacific"));
    ReadingData readingData1 = ReadingData.builder()
        .setQuantityUnit("kWh")
        .setFromDateTime(fromDateTime1)
        .setToDateTime(toDateTime1)
        .setQuantityValue(new BigDecimal("1000"))
        .build();
    readings.add(readingData1);

    DateTime fromDateTime2 = new DateTime(2014, 2, 1, 1, 0, 0, 0, DateTimeZone.forID("US/Pacific"));
    DateTime toDateTime2 = new DateTime(2014, 3, 1, 1, 0, 0, 0, DateTimeZone.forID("US/Pacific"));
    ReadingData readingData2 = ReadingData.builder()
        .setQuantityUnit("kWh")
        .setFromDateTime(fromDateTime2)
        .setToDateTime(toDateTime2)
        .setQuantityValue(new BigDecimal("900"))
        .build();
    readings.add(readingData2);

    ReadingDataRequest request = ReadingDataRequest.builder()
        .setUsageProfileId(profile.getProfileId())
        .setReadings(ImmutableList.copyOf(readings))
        .build();

    // add readings to profile
    Response<ReadingData> addReadingResults = profileService.addReadings(request);
    assertNotNull("new Profile is null", addReadingResults);
    assertEquals("bad status", addReadingResults.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("bad count", addReadingResults.getCount() < 2);

    // getProfile with readings / ensure readings are there
    GetProfileRequest profileRequest = GetProfileRequest.builder()
        .setProfileId(profile.getProfileId())
        .setPopulateReadings(true)
        .build();

    profile = callGetProfile("Test get one profile", profileRequest);

    cleanup(account.getAccountId());
  }

  @Test
  public void testGetProfileGroupedByHour() {

    Account account = createAccount();
    Profile profile = Profile.builder()
        .setAccountId(account.getAccountId())
        .build();
    Response<Profile> results = profileService.addProfile(profile);

    assertNotNull("new Profile is null", results);
    assertEquals("bad status", results.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("bad count", results.getCount() > 0);

    profile = results.getResults().get(0);

    List<ReadingData> readings = new ArrayList<ReadingData>();

    // Create one year of readings
    DateTime fromDateTime1 =
        new DateTime(2014, 1, 1, 1, 0, 0, 0, DateTimeZone.forID("America/Los_Angeles"));
    DateTime toDateTime1 =
        new DateTime(2015, 1, 1, 1, 0, 0, 0, DateTimeZone.forID("America/Los_Angeles"));
    ReadingData readingData1 = ReadingData.builder()
        .setQuantityUnit("kWh")
        .setFromDateTime(fromDateTime1)
        .setToDateTime(toDateTime1)
        .setQuantityValue(new BigDecimal("3650"))
        .build();
    readings.add(readingData1);
    



    ReadingDataRequest request = ReadingDataRequest.builder()
        .setUsageProfileId(profile.getProfileId())
        .setReadings(ImmutableList.copyOf(readings))
        .build();

    // add readings to profile
    Response<ReadingData> addReadingResults = profileService.addReadings(request);
    assertNotNull("new Profile is null", addReadingResults);
    assertEquals("bad status", addReadingResults.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("bad count", addReadingResults.getCount() < 2);

    // getProfile by date range
    GetProfileRequest profileRequest = GetProfileRequest.builder()
        .setProfileId(profile.getProfileId())
        .setFromDateTime(
          new DateTime(2014, 6, 1, 1, 0, 0, 0, DateTimeZone.forID("America/Los_Angeles")))
        .setToDateTime(
          new DateTime(2014, 7, 1, 1, 0, 0, 0, DateTimeZone.forID("America/Los_Angeles")))
        .setPopulateReadings(Boolean.TRUE)
        .setGroupBy(GroupBy.DAY)
        .setPageCount(100)
        .build();

    profile = callGetProfile("Test get one profile", profileRequest);

    assertNotNull("new Profile intervals is null", profile.getIntervals());
    // assertTrue("interval1 is not equal",
    // profile.getIntervals().getList().get(0).getkWh().getQuantityAmount()
    // .equals(new BigDecimal("1.34408602150537650000")));

    cleanup(account.getAccountId());
  }

  @Test
  public void testAddUpdateReadingsDST() {

    Account account = createAccount();
    Profile profile = Profile.builder()
        .setAccountId(account.getAccountId())
        .build();
    Response<Profile> results = profileService.addProfile(profile);

    assertNotNull("new Profile is null", results);
    assertEquals("bad status", results.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("bad count", results.getCount() > 0);

    profile = results.getResults().get(0);

    List<ReadingData> readings = new ArrayList<ReadingData>();

    // add two months of readings
    DateTime fromDateTime1 = new DateTime(2014, 6, 1, 0, 0, 0, 0, DateTimeZone.forID("US/Pacific"));
    DateTime toDateTime1 = new DateTime(2014, 7, 1, 0, 0, 0, 0, DateTimeZone.forID("US/Pacific"));

    ReadingData readingData1 = ReadingData.builder()
        .setQuantityUnit("kWh")
        .setFromDateTime(fromDateTime1)
        .setToDateTime(toDateTime1)
        .setQuantityValue(new BigDecimal("1000"))
        .build();
    readings.add(readingData1);

    ReadingDataRequest request = ReadingDataRequest.builder()
        .setUsageProfileId(profile.getProfileId())
        .setReadings(ImmutableList.copyOf(readings))
        .build();

    // add readings to profile
    Response<ReadingData> addReadingResults = profileService.addReadings(request);
    assertNotNull("new Profile is null", addReadingResults);
    assertEquals("bad status", addReadingResults.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("bad count", addReadingResults.getCount() != 1);

    //
    // TODO - add paginated readings and intervals lists to profile type
    //

    // getProfile with readings / ensure readings are there
    // GetProfileRequest profileRequest = new GetProfileRequest();
    // profileRequest.setProfileId(profile.getProfileId());
    // profileRequest.setPopulateReadings(true);
    // profile = callGetProfile("Test get one profile", profileRequest);

    // assertNotNull("new Profile Readings is null", profile.getReadings());
    // assertTrue("reading1 is not equal",
    // profile.getReadings().getList().get(0).getQuantityValue().equals(new
    // BigDecimal("1000")));

    cleanup(account.getAccountId());
  }

  @Test
  public void testAddReadingsDSTRunCalc() {

    Account account = createAccount();
    Profile profile = Profile.builder()
        .setAccountId(account.getAccountId())
        .build();
    Response<Profile> results = profileService.addProfile(profile);

    assertNotNull("new Profile is null", results);
    assertEquals("bad status", results.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("bad count", results.getCount() > 0);

    profile = results.getResults().get(0);

    List<ReadingData> readings = new ArrayList<ReadingData>();

    // add two months of readings
    ReadingData readingData1 = ReadingData.builder()
        .setQuantityUnit("kWh")
        .setFromDateTime(
          new DateTime(2013, 5, 29, 10, 45, 0, 0, DateTimeZone.forID("America/New_York")))
        .setToDateTime(
          new DateTime(2013, 5, 29, 10, 50, 0, 0, DateTimeZone.forID("America/New_York")))
        .setQuantityValue(new BigDecimal("30.1"))
        .build();
    readings.add(readingData1);

    ReadingData readingData2 = ReadingData.builder()
        .setQuantityUnit("kWh")
        .setFromDateTime(
          new DateTime(2013, 5, 29, 10, 50, 0, 0, DateTimeZone.forID("America/New_York")))
        .setToDateTime(
          new DateTime(2013, 5, 29, 10, 55, 0, 0, DateTimeZone.forID("America/New_York")))
        .setQuantityValue(new BigDecimal("30.2"))
        .build();
    readings.add(readingData2);

    ReadingData readingData3 = ReadingData.builder()
        .setQuantityUnit("kWh")
        .setFromDateTime(
          new DateTime(2013, 5, 29, 10, 55, 0, 0, DateTimeZone.forID("America/New_York")))
        .setToDateTime(
          new DateTime(2013, 5, 29, 11, 00, 0, 0, DateTimeZone.forID("America/New_York")))
        .setQuantityValue(new BigDecimal("30.3"))
        .build();
    readings.add(readingData3);

    ReadingDataRequest request = ReadingDataRequest.builder()
        .setUsageProfileId(profile.getProfileId())
        .setReadings(ImmutableList.copyOf(readings))
        .build();

    // add readings to profile
    Response<ReadingData> addReadingResults = profileService.addReadings(request);
    assertNotNull("new Profile is null", addReadingResults);
    assertEquals("bad status", addReadingResults.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("bad count", addReadingResults.getCount() != 1);

    //
    // TODO - add paginated readings and intervals lists to profile type
    //

    // getProfile with readings / ensure readings are there
    // GetProfileRequest profileRequest = new GetProfileRequest();
    // profileRequest.setProfileId(profile.getProfileId());
    // profileRequest.setPopulateReadings(true);
    // profile = callGetProfile("Test get one profile", profileRequest);

    // assertNotNull("new Profile Readings is null", profile.getReadings());
    // assertTrue("reading1 is not equal",
    // profile.getReadings().getList().get(0).getQuantityValue().equals(new
    // BigDecimal("1000")));

    cleanup(account.getAccountId());
  }

  /*
   * Helper functions
   */

  public void callDeleteProfile(String profileId) {
    DeleteProfileRequest request = DeleteProfileRequest.builder()
        .setProfileId(profileId)
        .build();
    Response<Profile> restResponse = profileService.deleteProfile(request);

    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("bad count", restResponse.getCount() == 0);
    assertNull("shouldn't have results", restResponse.getResults());
  }

  public Profile callGetProfile(String testCase, GetProfileRequest request) {
    Response<Profile> restResponse = profileService.getProfile(request);

    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("bad count", restResponse.getCount() > 0);

    Profile profile = restResponse.getResults().get(0);
    return profile;

  }

  public void callGetProfiles(String testCase, GetProfilesRequest request) {
    Response<Profile> restResponse = profileService.getProfiles(request);

    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
  }
}
