package io.github.spharris.electricity.actions;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.genability.client.api.request.GetCalculatedCostRequest;
import com.genability.client.types.CalculatedCost;
import com.genability.client.types.PropertyData;
import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.testing.fieldbinder.Bind;
import com.google.inject.testing.fieldbinder.BoundFieldModule;
import io.github.spharris.electricity.calculator.TariffCalculator;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorActionTest {
  
  private static final ZonedDateTime START_TIME = LocalDate.of(2015, 1, 1)
      .atStartOfDay(ZoneId.of("America/Los_Angeles"));
  
  @Rule public ExpectedException thrown = ExpectedException.none();
  
  @Inject private CalculatorAction action;
  
  @Bind @Mock private TariffCalculator calculator;
  
  @Before
  public void createInjector() {
    Guice.createInjector(BoundFieldModule.of(this)).injectMembers(this);
  }
  
  @Before
  public void returnCalculatedCost() throws Exception {
    when(calculator.calculateTariffCost(isA(GetCalculatedCostRequest.class)))
        .thenReturn(CalculatedCost.builder().build());
  }
  
  @Test
  public void requiresFromDateTime() throws Exception {
    thrown.expect(BadRequestException.class);
    
    action.getCalculation(GetCalculatedCostRequest.builder()
      .setMasterTariffId(1L)
      .setToDateTime(START_TIME)
      .build());
  }
  
  @Test
  public void requiresToDateTime() throws Exception {
    thrown.expect(BadRequestException.class);
    
    action.getCalculation(GetCalculatedCostRequest.builder()
      .setMasterTariffId(1L)
      .setFromDateTime(START_TIME)
      .build());
  }
  
  @Test
  public void requiresFromBeforeStart() throws Exception {
    thrown.expect(BadRequestException.class);
    
    action.getCalculation(GetCalculatedCostRequest.builder()
      .setMasterTariffId(1L)
      .setFromDateTime(START_TIME)
      .setToDateTime(START_TIME)
      .build());
  }
  
  @Test
  public void requiresMasterTariffId() throws Exception {
    thrown.expect(BadRequestException.class);
    
    action.getCalculation(GetCalculatedCostRequest.builder()
      .setFromDateTime(START_TIME)
      .setToDateTime(START_TIME)
      .build());
  }
  
  @Test
  public void normalizesRequestInputs() throws Exception {
    GetCalculatedCostRequest request = GetCalculatedCostRequest.builder()
      .setMasterTariffId(1L)
      .setFromDateTime(START_TIME)
      .setToDateTime(START_TIME.plusDays(1L))
      .build(); 
    
    action.getCalculation(request);
    
    verify(calculator).calculateTariffCost(request.toBuilder()
      .setTariffInputs(ImmutableList.of())
      .setRateInputs(ImmutableList.of())
      .build());
  }
  
  @Test
  public void returnsResponse() throws Exception {
    // No exception thrown = passing test
    action.getCalculation(GetCalculatedCostRequest.builder()
      .setMasterTariffId(1L)
      .setFromDateTime(START_TIME)
      .setToDateTime(START_TIME.plusDays(1L))
      .build());
  }
  
  @Test
  public void requiresKeyName() throws Exception {
    thrown.expect(BadRequestException.class);
    
    action.getCalculation(GetCalculatedCostRequest.builder()
      .setMasterTariffId(1L)
      .setFromDateTime(START_TIME)
      .setToDateTime(START_TIME.plusDays(1L))
      .setTariffInputs(PropertyData.builder().setDataValue("data-value").build())
      .build());
  }
  
  @Test
  public void requiresDataValue() throws Exception {
    thrown.expect(BadRequestException.class);
    
    action.getCalculation(GetCalculatedCostRequest.builder()
      .setMasterTariffId(1L)
      .setFromDateTime(START_TIME)
      .setToDateTime(START_TIME.plusDays(1L))
      .setTariffInputs(PropertyData.builder().setKeyName("key-name").build())
      .build());
  }
  
  @Test
  public void setsPropertyDates() throws Exception {
    PropertyData pd = PropertyData.builder()
        .setKeyName("key-name")
        .setDataValue("data-value")
        .build();
    
    GetCalculatedCostRequest request = GetCalculatedCostRequest.builder()
      .setMasterTariffId(1L)
      .setFromDateTime(START_TIME)
      .setToDateTime(START_TIME.plusDays(1L))
      .setTariffInputs(pd)
      .setRateInputs(ImmutableList.of())
      .build();
    
    action.getCalculation(request);
    
    verify(calculator).calculateTariffCost(request.toBuilder()
      .setTariffInputs(pd.toBuilder()
        .setFromDateTime(START_TIME)
        .setToDateTime(START_TIME.plusDays(1L))
        .build())
      .build());
  }
}
