package io.github.spharris.electricity.calculator;

import static com.google.common.base.Preconditions.checkNotNull;

import com.genability.client.types.Season;
import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.Range;
import java.time.Instant;

/**
 * Utility for calculating intervals for a season 
 */
final class SeasonCalculator {

  ImmutableRangeSet<Instant> calculateSeasonIntervals(Season season, Range<Instant> interval) {
    checkNotNull(season, "season");
    checkNotNull(interval, "interval");
  }
}
