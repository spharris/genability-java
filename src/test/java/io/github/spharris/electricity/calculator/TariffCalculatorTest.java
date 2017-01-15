package io.github.spharris.electricity.calculator;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.genability.client.api.request.GetCalculatedCostRequest;
import com.genability.client.api.request.GetTariffsRequest;
import com.genability.client.api.service.TariffService;
import com.genability.client.types.CalculatedCost;
import com.genability.client.types.CalculatedCostItem;
import com.genability.client.types.Response;
import com.genability.client.types.Tariff;
import com.genability.client.types.TariffRate;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.Futures;
import com.google.inject.Guice;
import com.google.inject.testing.fieldbinder.Bind;
import com.google.inject.testing.fieldbinder.BoundFieldModule;
import io.github.spharris.electricity.testing.TestEntities;
import io.github.spharris.electricity.util.Interval;
import java.time.LocalDate;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TariffCalculatorTest {

  @Inject private TariffCalculator calculator;
  
  @Bind @Mock private RateCalculator rateCalculator;
  @Bind @Mock private TariffService tariffService;
  
  @Before
  public void createInjector() {
    Guice.createInjector(BoundFieldModule.of(this)).injectMembers(this);
  }
  
  @Before
  public void returnTariff() {
    when(tariffService.getTariffs(isA(GetTariffsRequest.class))).thenReturn(
      Futures.immediateFuture(Response.of(TestEntities.TARIFF)));
  }
  
  @Before
  public void returnCalculatedCost() {
    when(rateCalculator.calculateRate(isA(Interval.class), isA(Tariff.class), isA(TariffRate.class),
      any())).thenReturn(ImmutableList.of());
  }
  
  @Test
  public void separatesBillingPeriods() throws Exception {
    GetCalculatedCostRequest request = GetCalculatedCostRequest.builder()
        .setMasterTariffId(1L)
        .setFromDateTime(LocalDate.of(2015, 1, 1).atStartOfDay(TestEntities.ZONE_ID))
        .setToDateTime(LocalDate.of(2016, 1, 1).atStartOfDay(TestEntities.ZONE_ID))
        .setTariffInputs(ImmutableList.of())
        .build();
    
    calculator.calculateTariffCost(request);
    
    for (int i = 0; i < 12; i++) {
      verify(rateCalculator).calculateRate(
        Interval.of(
          request.getFromDateTime().plusMonths(i),
          request.getFromDateTime().plusMonths(i + 1)),
        TestEntities.TARIFF,
        TestEntities.RATE,
        ImmutableList.of());
    }
  }
  
  @Test
  public void respectsBillingPeriodOption() throws Exception {
    GetCalculatedCostRequest request = GetCalculatedCostRequest.builder()
        .setMasterTariffId(1L)
        .setFromDateTime(LocalDate.of(2015, 1, 1).atStartOfDay(TestEntities.ZONE_ID))
        .setToDateTime(LocalDate.of(2016, 1, 1).atStartOfDay(TestEntities.ZONE_ID))
        .setTariffInputs(ImmutableList.of())
        .setBillingPeriod(true)
        .build();
    
    calculator.calculateTariffCost(request);
    
    verify(rateCalculator).calculateRate(
      Interval.of(
        request.getFromDateTime(),
        request.getToDateTime()),
      TestEntities.TARIFF,
      TestEntities.RATE,
      ImmutableList.of());
    
    verifyNoMoreInteractions(rateCalculator);
  }
  
  @Test
  public void unwrapsCalculatedCostItems() throws Exception {
    when(rateCalculator.calculateRate(isA(Interval.class), isA(Tariff.class), isA(TariffRate.class),
      any())).thenReturn(ImmutableList.of(
        CalculatedCostItem.builder().build(),
        CalculatedCostItem.builder().build()));
    
    when(tariffService.getTariffs(isA(GetTariffsRequest.class))).thenReturn(
      Futures.immediateFuture(Response.of(TestEntities.TARIFF.toBuilder()
        .setRates(TestEntities.RATE, TestEntities.RATE)
        .build())));

    GetCalculatedCostRequest request = GetCalculatedCostRequest.builder()
        .setMasterTariffId(1L)
        .setFromDateTime(LocalDate.of(2015, 1, 1).atStartOfDay(TestEntities.ZONE_ID))
        .setToDateTime(LocalDate.of(2016, 1, 1).atStartOfDay(TestEntities.ZONE_ID))
        .setTariffInputs(ImmutableList.of())
        .setBillingPeriod(true)
        .build();
    
    CalculatedCost result = calculator.calculateTariffCost(request);
    
    assertThat(result.getItems()).hasSize(4);
  }
}
