package io.github.spharris.electricity.calculator;

import com.genability.client.api.request.GetCalculatedCostRequest;
import com.genability.client.api.request.GetTariffsRequest;
import com.genability.client.api.service.TariffService;
import com.genability.client.types.CalculatedCost;
import com.google.common.util.concurrent.Futures;
import javax.inject.Inject;

public class TariffCalculator {
  
  private final TariffService tariffService;
  
  @Inject
  TariffCalculator(TariffService tariffService) {
    this.tariffService = tariffService;
  }
  
  public CalculatedCost calculateTariffCost(GetCalculatedCostRequest calcRequest) {
    GetTariffsRequest tariffsRequest = GetTariffsRequest.builder()
        .setMasterTariffId(calcRequest.getMasterTariffId())
        .setPopulateRates(true)
        .setPopulateProperties(true)
        .setFromDateTime(calcRequest.getFromDateTime().minusDays(1))
        .setToDateTime(calcRequest.getToDateTime().plusDays(1))
        .build();
    
    Futures.transform(tariffService.getTariffs(tariffsRequest), tariffs -> {
      System.out.println("Found " + tariffs.getResults().size() + " tariffs");
      return tariffs.getResults();
    });
    
    return CalculatedCost.builder().build();
  }
}
 