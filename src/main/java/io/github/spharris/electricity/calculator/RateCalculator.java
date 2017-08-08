package io.github.spharris.electricity.calculator;

import com.genability.client.types.CalculatedCostItem;
import com.genability.client.types.PropertyData;
import com.genability.client.types.Tariff;
import com.genability.client.types.TariffRate;
import com.google.common.collect.ImmutableList;
import io.github.spharris.electricity.util.Interval;
import javax.inject.Inject;

/** Class for filtering out rates on a tariff based on the passed-in applicability properties */
class RateCalculator {
  
  private final RateApplicabilityIntervalCalculator applicabilityCalculator;
  
  @Inject
  RateCalculator(RateApplicabilityIntervalCalculator applicabilityCalculator) {
    this.applicabilityCalculator = applicabilityCalculator;
  }
  
  /**
   * Apply <code>rate</code> to <code>properties</code> over the given {@link Interval} 
   */
  public ImmutableList<CalculatedCostItem> calculateRate(Interval interval, Tariff tariff,
      TariffRate rate, ImmutableList<PropertyData> properties) {
    return ImmutableList.of();
  }
}
