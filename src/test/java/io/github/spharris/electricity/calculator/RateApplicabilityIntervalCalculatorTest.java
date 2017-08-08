package io.github.spharris.electricity.calculator;

import static com.google.common.truth.Truth.assertThat;

import com.genability.client.types.TariffRate;
import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.Range;
import com.google.inject.Guice;
import io.github.spharris.electricity.util.RangeUtil;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class RateApplicabilityIntervalCalculatorTest {

  private static final Instant NOW = Instant.now();

  @Inject
  private RateApplicabilityIntervalCalculator calculator;

  @Before
  public void createInjector() {
    Guice.createInjector().injectMembers(this);
  }

  @Test
  public void noQualifiersReturnsEntirePeriod() {
    TariffRate rate = TariffRate.builder().build();
    Range<Instant> interval = RangeUtil.rangeFor(NOW, NOW.plus(Duration.ofDays(30)));

    ImmutableRangeSet<Instant> result = calculator.getActiveIntervalsForPeriod(rate, interval,
        CalculationContext.getDefaultContext());
    ImmutableRangeSet<Instant> expected = ImmutableRangeSet.of(interval);

    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void returnsIntersectionOfDatesWhenRateHasBounds() {
    Instant start = NOW.plus(Duration.ofDays(5));
    Instant end = start.plus(Duration.ofDays(1));
    TariffRate rate = TariffRate.builder()
        .setFromDateTime(ZonedDateTime.ofInstant(start, ZoneId.of("America/Los_Angeles")))
        .setToDateTime(ZonedDateTime.ofInstant(end, ZoneId.of("America/Los_Angeles")))
        .build();
    
    Range<Instant> interval = RangeUtil.rangeFor(NOW, NOW.plus(Duration.ofDays(30)));

    ImmutableRangeSet<Instant> result = calculator.getActiveIntervalsForPeriod(rate, interval,
        CalculationContext.getDefaultContext());
    ImmutableRangeSet<Instant> expected = ImmutableRangeSet.of(RangeUtil.rangeFor(start, end));

    assertThat(result).isEqualTo(expected);
  }
}
