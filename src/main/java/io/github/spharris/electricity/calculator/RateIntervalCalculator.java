package io.github.spharris.electricity.calculator;

import com.genability.client.types.TariffRate;
import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.Range;
import io.github.spharris.electricity.util.RangeUtil;
import java.time.Instant;
import javax.inject.Inject;

/**
 * Class that calculates the time intervals over which a particular rate is active given the inputs
 * to a calculation.
 */
class RateIntervalCalculator {

  @Inject
  RateIntervalCalculator() {}

  ImmutableRangeSet<Instant> getActiveIntervalsForPeriod(TariffRate rate,
      Range<Instant> calculationInterval,
      CalculationContext context) {
    Range<Instant> rateRange = RangeUtil.rangeFor(rate.getFromDateTime(), rate.getToDateTime());
    
    return ImmutableRangeSet.of(rateRange.intersection(calculationInterval));
  }
}
