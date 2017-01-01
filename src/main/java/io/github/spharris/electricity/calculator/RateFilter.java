package io.github.spharris.electricity.calculator;

import com.genability.client.types.PropertyData;
import com.genability.client.types.Tariff;
import com.genability.client.types.TariffRate;
import com.google.common.collect.ImmutableList;

/** Class for filtering out rates on a tariff based on the passed-in applicability properties */
public class RateFilter {
  
  RateFilter() {}
  
  /**
   * Filter the rates on <code>tariff</code> based on the default values provided by the
   * tariff and those passed in the request.
   */
  public ImmutableList<TariffRate> filterApplicableRates(Tariff tariff,
      ImmutableList<PropertyData> properties) {
    return ImmutableList.of();
  }
}
