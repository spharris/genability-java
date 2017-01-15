package io.github.spharris.electricity.calculator;

import com.genability.client.api.request.GetCalculatedCostRequest;
import com.genability.client.api.request.GetTariffsRequest;
import com.genability.client.api.service.TariffService;
import com.genability.client.types.CalculatedCost;
import com.genability.client.types.CalculatedCostItem;
import com.genability.client.types.Tariff;
import com.google.common.collect.ImmutableList;
import io.github.spharris.electricity.util.Interval;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import javax.inject.Inject;

public class TariffCalculator {
  
  private static final Period BILLING_PERIOD = Period.ofMonths(1);
  
  private final TariffService tariffService;
  private final RateCalculator rateCalculator;
  
  @Inject
  TariffCalculator(TariffService tariffService, RateCalculator rateCalculator) {
    this.tariffService = tariffService;
    this.rateCalculator = rateCalculator;
  }
  
  public CalculatedCost calculateTariffCost(GetCalculatedCostRequest calcRequest)
      throws ExecutionException, InterruptedException {
    GetTariffsRequest tariffsRequest = GetTariffsRequest.builder()
        .setMasterTariffId(calcRequest.getMasterTariffId())
        .setPopulateRates(true)
        .setPopulateProperties(true)
        .setFromDateTime(calcRequest.getFromDateTime().minusDays(1))
        .setToDateTime(calcRequest.getToDateTime().plusDays(1))
        .build();
    
    ImmutableList<Interval> billingPeriods = getBilingPeriods(calcRequest);
    ImmutableList<Tariff> tariffs = tariffService.getTariffs(tariffsRequest).get().getResults();
    ImmutableList<CalculatedCostItem> costs = ImmutableList.copyOf(billingPeriods.stream()
      .flatMap(interval ->
        tariffs.stream()
          .filter(tariff -> {
            ZonedDateTime effectiveDate =
                tariff.getEffectiveDate().atStartOfDay(tariff.getTimeZone());
            ZonedDateTime endDate = tariff.getEndDate() != null 
                ? tariff.getEndDate().atStartOfDay(tariff.getTimeZone())
                : calcRequest.getToDateTime().withZoneSameInstant(tariff.getTimeZone());

            return Interval.of(effectiveDate, endDate).overlaps(interval); 
          })
          .flatMap(tariff ->
            tariff.getRates().stream()
                .map(rate -> 
                  rateCalculator
                        .calculateRate(interval, tariff, rate, calcRequest.getTariffInputs()))
                .flatMap(l -> l.stream())
          )
      )
      .collect(Collectors.toList()));
    
    return CalculatedCost.builder()
        .setItems(costs)
        .build();
  }
  
  private static ImmutableList<Interval> getBilingPeriods(GetCalculatedCostRequest request) {
    ImmutableList.Builder<Interval> builder = ImmutableList.builder();
    if (Objects.equals(request.getBillingPeriod(), true)) {
      builder.add(Interval.of(request.getFromDateTime(), request.getToDateTime()));
    } else {
      ZonedDateTime start = request.getFromDateTime();
      while (start.isBefore(request.getToDateTime())) {
        ZonedDateTime end = Collections.min(ImmutableList.of(start.plus(BILLING_PERIOD),
          request.getToDateTime()));
        builder.add(Interval.of(start, end));
        
        start = end;
      }
    }
    
    return builder.build();
  }
}
 