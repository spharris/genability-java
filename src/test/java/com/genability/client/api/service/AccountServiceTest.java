package com.genability.client.api.service;

import static com.google.common.collect.Iterables.getOnlyElement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.genability.client.api.request.DeleteAccountRequest;
import com.genability.client.api.request.GetAccountRatesRequest;
import com.genability.client.api.request.GetAccountRequest;
import com.genability.client.api.request.GetAccountsRequest;
import com.genability.client.testing.TestClientModule;
import com.genability.client.types.Account;
import com.genability.client.types.Address;
import com.genability.client.types.PropertyData;
import com.genability.client.types.Response;
import com.genability.client.types.Tariff;
import com.genability.client.types.TariffRate;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Guice;

@RunWith(JUnit4.class)
public class AccountServiceTest {

  @Inject private AccountService accountService;
  
  @Before
  public void createInjector() {
    Guice.createInjector(new TestClientModule()).injectMembers(this);
  }
  
  @Test
  public void testAddAccount() throws Exception {
    // test
    Account newAccount = Account.builder()
        .setAccountName("Java Client Lib Test Add Account - CAN DELETE")
        .build();

    // call add account helper method
    Response<Account> accountResponse = accountService.addAccount(newAccount).get();

    // delete account so we keep things clean
    DeleteAccountRequest dar = DeleteAccountRequest.builder()
        .setAccountId(getOnlyElement(accountResponse.getResults()).getAccountId())
        .build();
    accountService.deleteAccount(dar).get();
  }

  @Test
  public void testUpdateAccount() throws Exception {
    String org = "Test Org";

    Account unsavedAccount = Account.builder()
        .setAccountName("Java Client Lib Test Add Account - CAN DELETE")
        .build();

    // call add account helper method
    Account savedAccount = addAccount(unsavedAccount);
    try {
      savedAccount = savedAccount.toBuilder().setCustomerOrgName(org).build();

      Response<Account> restResponse = accountService.updateAccount(savedAccount).get();
      assertNotNull("restResponse null", restResponse);
      assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
      assertTrue("bad count", restResponse.getCount() == 1);

      Account updatedAccount = restResponse.getResults().get(0);
      assertEquals("Didn't update owner", updatedAccount.getCustomerOrgName(), org);
    } finally {
      // delete account so we keep things clean
      deleteAccount(savedAccount.getAccountId());
    }
  }

  @Test
  public void testUpsertWithNewAccount() throws Exception {
    String accountName = "Java Client Lib Test Add Account - CAN DELETE";
    String providerAccountId = "javaapi-test-id-01";

    Account newAccount = Account.builder()
        .setAccountName(accountName)
        .setProviderAccountId(providerAccountId)
        .build();

    Response<Account> restResponse = accountService.updateAccount(newAccount).get();

    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("bad count", restResponse.getCount() == 1);
    Account returnedAccount = null;

    try {
      returnedAccount = restResponse.getResults().get(0);
      assertEquals("providerAccountIds didn't match", newAccount.getProviderAccountId(),
          returnedAccount.getProviderAccountId());
      assertEquals("Didn't upsert accountName correctly.", returnedAccount.getAccountName(),
          accountName);
    } finally {
      // delete account so we keep things clean
      if (returnedAccount != null) {
        deleteAccount(returnedAccount.getAccountId());
      }
    }

  }

  @Test
  public void testGetAccounts() throws Exception {

    GetAccountsRequest request = GetAccountsRequest.builder().build();

    // A few examples of how to search and sort
    // request.setSortOn("customerOrgName");
    // request.setSearch("acme");
    // request.setSearchOn("customerOrgName");

    Response<Account> restResponse = accountService.getAccounts(request).get();

    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
  }

  @Test
  public void testPaginatedAccountList() throws Exception {
    int numberOfAccountsToCreate = 10;
    int pageCount = 5;
    String[] createdAccountIds = new String[numberOfAccountsToCreate];

    for (int i = 0; i < numberOfAccountsToCreate; i++) {
      Account newAccount = Account.builder()
          .setAccountName(String.format("JAVA CLIENT TEST ACCOUNT #%d - CAN DELETE", i))
          .build();

      Account addedAccount = addAccount(newAccount);
      createdAccountIds[i] = addedAccount.getAccountId();
    }

    try {
      GetAccountsRequest request = GetAccountsRequest.builder()
          .setPageCount(pageCount)
          .setSearch("JAVA CLIENT TEST ACCOUNT")
          .setSearchOn("accountName")
          .build();
      Response<Account> restResponse = accountService.getAccounts(request).get();

      int totalAccounts = restResponse.getCount();
      int accountsVisited = 0;

      while (accountsVisited < totalAccounts) {
        assertEquals("Didn't set pageCount correctly", pageCount,
            restResponse.getPageCount().intValue());
        assertEquals("Didn't page through the account list correctly.", accountsVisited,
            restResponse.getPageStart().intValue());

        accountsVisited += restResponse.getResults().size();

        request = request.toBuilder().setPageStart(
          restResponse.getPageStart() + restResponse.getPageCount()).build();
        restResponse = accountService.getAccounts(request).get();
      }

      assertEquals("Visited too many accounts.", accountsVisited, totalAccounts);
    } finally {
      // make sure that these accounts get cleaned up
      for (String accountId : createdAccountIds) {
        deleteAccount(accountId);
      }
    }
  }

  @Test
  public void testGetAccount() throws Exception {
    Account account = accountService.addAccount(
      Account.builder().build()).get().getResults()
        .get(0);
    Response<Account> restResponse = accountService.getAccount(GetAccountRequest.builder()
      .setAccountId(account.getAccountId())
      .build())
        .get();

    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
  }

  @Test
  public void testDeleteAccount() throws Exception {

     Account addAccount = Account.builder()
        .setAccountName("Java Client Lib Test Add Account - CAN DELETE")
        .build();

    // call add account helper method
    addAccount = addAccount(addAccount);

    // test delete account
    deleteAccount(addAccount.getAccountId());

  }

  @Test
  public void testGetAccountProperties() throws Exception {

    // Add account with properties
    Account addAccount = Account.builder()
        .setAccountName("Java Client Lib Test Add Account - CAN DELETE")
        .setProperties(ImmutableMap.<String, PropertyData>builder()
          .put("territoryId", PropertyData.builder()
            .setKeyName("territoryId")
            .setDataValue("123")
            .build())
          .build())
        .build();

    addAccount = addAccount(addAccount);

    // get account with properties
    GetAccountRequest request = GetAccountRequest.builder()
        .setAccountId(addAccount.getAccountId())
        .build();
    Response<PropertyData> restResponse = accountService.getAccountProperties(request).get();

    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
    assertNotNull("Properties null", restResponse.getResults());

    // ensure territoryId property is on account
    for (PropertyData propertyData : restResponse.getResults()) {
      assertEquals("territoryId", propertyData.getKeyName());
    }

    // delete account so we keep things clean
    deleteAccount(addAccount.getAccountId());

  }

  @Test
  public void testInterviewAccount() throws Exception {

    Account addAccount = Account.builder()
        .setAccountName("Java Client Lib Test Add Account - CAN DELETE")
        .setAddress(Address.builder()
           .setAddressString("94105")
           .build())
        .build();

    // call add account helper method
    addAccount = addAccount(addAccount);

    // call interview account
    Response<PropertyData> restResponse =
        accountService.interviewAccount(addAccount.getAccountId(), PropertyData.builder().build())
        .get();

    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
    assertNotNull("Properties null", restResponse.getResults());

    // ensure territoryId property is on account
    for (PropertyData propertyData : restResponse.getResults()) {
      assertEquals("customerClass", propertyData.getKeyName());
    }

  }

  @Test
  public void testGetAccountTariffs() throws Exception {

    Account addAccount = Account.builder()
        .setAccountName("Java Client Lib Test Add Account - CAN DELETE")
        .setTariffs(Tariff.builder()
          .setMasterTariffId(512L)
          .build())
        .build();

    // call add account helper method
    addAccount = addAccount(addAccount);

    // retrieve account with tariff
    Account account = getAccount(addAccount.getAccountId());

    // ensure account with tariff on account
    assertNotNull("Tariffs null", account.getTariffs());

    for (Tariff accountTariff : account.getTariffs()) {
      assertEquals(512l, accountTariff.getMasterTariffId().longValue());
    }

    // delete account so we keep things clean
    deleteAccount(account.getAccountId());

  }

  @Test
  public void testGetAccountRates() throws Exception {

    Account account = Account.builder()
        .setAccountName("Java Client Lib Test Add Account - CAN DELETE")
        .setTariffs(Tariff.builder()
          .setMasterTariffId(512L)
          .build())
        .build();

    // call add account helper method
    account = addAccount(account);

    // retrieve account rates based on added account tariff
    GetAccountRatesRequest request = GetAccountRatesRequest.builder()
        .setAccountId(account.getAccountId())
        .build();

    Response<TariffRate> restResponse = accountService.getAccountRates(request).get();

    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
    assertEquals("bad type", restResponse.getType(), TariffRate.REST_TYPE);
    assertNotNull("Rates is null", restResponse.getResults());

    for (TariffRate tariffRate : restResponse.getResults()) {
      assertNotNull("tariffRateId null", tariffRate.getTariffRateId());
    }

    // delete account so we keep things clean
    deleteAccount(account.getAccountId());

  }

  /**
   * Private helper method to call add account and test for success
   * 
   * @param addAccount The addAccount.
   * @return The return value.
   */
  private Account addAccount(Account addAccount) throws Exception {

    Response<Account> restResponse = accountService.addAccount(addAccount).get();

    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);
    assertTrue("bad count", restResponse.getCount() > 0);

    Account newAccount = null;
    for (Account account : restResponse.getResults()) {
      assertNotNull("accountId null", account.getAccountId());
      newAccount = account;
    }

    return newAccount;
  }

  /**
   * Private helper method to delete account and test for success
   * 
   * @param accountId The accountId.
   */
  private void deleteAccount(String accountId) throws Exception {

    // delete account so we keep things clean
    DeleteAccountRequest request = DeleteAccountRequest.builder()
        .setHardDelete(Boolean.TRUE)
        .setAccountId(accountId)
        .build();
    Response<Account> deleteResponse = accountService.deleteAccount(request).get();

    assertNotNull("restResponse null", deleteResponse);
    assertEquals("bad status", deleteResponse.getStatus(), Response.STATUS_SUCCESS);

  }

  private Account getAccount(String accountId) throws Exception {

    GetAccountRequest request = GetAccountRequest.builder()
        .setAccountId(accountId)
        .build();

    Response<Account> restResponse = accountService.getAccount(request).get();

    assertNotNull("restResponse null", restResponse);
    assertEquals("bad status", restResponse.getStatus(), Response.STATUS_SUCCESS);

    Account getAccount = null;
    for (Account account : restResponse.getResults()) {
      assertNotNull("accountId null", account.getAccountId());
      getAccount = account;
    }

    return getAccount;

  }
}
