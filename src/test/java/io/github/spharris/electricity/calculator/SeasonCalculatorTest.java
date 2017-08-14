package io.github.spharris.electricity.calculator;

import static com.google.common.truth.Truth.assertThat;

import com.genability.client.types.Season;
import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.Range;
import io.github.spharris.electricity.util.RangeUtil;
import java.time.Duration;
import java.time.Instant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SeasonCalculatorTest {

  private static final SeasonCalculator CALCULATOR = new SeasonCalculator();
  
  @Test
  public void nonOverlappingIntervalReturnsEmptySet() {
    Season season = Season.builder()
        .setSeasonFromDay(1)
        .setSeasonToDay(30)
        .setSeasonFromMonth(1)
        .setSeasonToMonth(5)
        .build();
    
    Instant start = Instant.parse("2015-06-01T00:00:00Z");
    Instant end = start.plus(Duration.ofDays(1L));
    
    ImmutableRangeSet<Instant> result = 
        CALCULATOR.calculateSeasonIntervals(season, RangeUtil.rangeFor(start,  end));
    
    assertThat(result.isEmpty()).isTrue();
  }
  
  @Test
  public void fullOverlapReturnsEntirePeriod() {
    Season season = Season.builder()
        .setSeasonFromDay(1)
        .setSeasonToDay(30)
        .setSeasonFromMonth(1)
        .setSeasonToMonth(5)
        .build();
    
    Instant start = Instant.parse("2015-01-01T00:00:00Z");
    Instant end = start.plus(Duration.ofDays(1L));
    
    Range<Instant> expectedRange = RangeUtil.rangeFor(start,  end);
    ImmutableRangeSet<Instant> result = 
        CALCULATOR.calculateSeasonIntervals(season, expectedRange);
    
    ImmutableRangeSet<Instant> expected = ImmutableRangeSet.of(expectedRange);
    
    assertThat(result).isEqualTo(expected);
  }
}
