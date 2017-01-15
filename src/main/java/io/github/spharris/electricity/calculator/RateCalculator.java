package io.github.spharris.electricity.calculator;

import com.genability.client.types.CalculatedCostItem;
import com.genability.client.types.PropertyData;
import com.genability.client.types.Tariff;
import com.genability.client.types.TariffRate;
import com.google.common.collect.ImmutableList;
import io.github.spharris.electricity.util.Interval;

/** Class for filtering out rates on a tariff based on the passed-in applicability properties */
class RateCalculator {
  
  RateCalculator() {}
  
  /**
   * Filter the rates on <code>tariff</code> based on the default values provided by the
   * tariff and those passed in the request.
   */
  public ImmutableList<CalculatedCostItem> calculateRate(Interval interval, Tariff tariff,
      TariffRate rate, ImmutableList<PropertyData> properties) {
    return ImmutableList.of();
  }
}
