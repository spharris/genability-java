package io.github.spharris.electricity.services;

import com.genability.client.api.request.GetCalculatedCostRequest;
import com.genability.client.types.CalculatedCost;

public class TariffCalculatorService {
  
  TariffCalculatorService() {}
  
  public CalculatedCost calculateTariffCost(GetCalculatedCostRequest request) {
    return CalculatedCost.builder().build();
  }
}
