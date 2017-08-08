package io.github.spharris.electricity.util;

import com.google.common.collect.Range;
import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * Utility for extracting {@link Range Ranges} from pairs of {@link Instant Instants}.
 */
public final class RangeUtil {

  private RangeUtil() {}
  
  public static Range<Instant> rangeFor(Instant first, Instant second) {
    if (first == null) {
      if (second == null) {
        return Range.all();
      }
      
      return Range.lessThan(second);
    }
    
    if (second == null) {
      return Range.atLeast(first);
    }
    
    return Range.closedOpen(first,  second);
  }
  
  public static Range<Instant> rangeFor(ZonedDateTime first, ZonedDateTime second) {
    Instant firstInstant = first == null ? null : first.toInstant();
    Instant secondInstant = second == null ? null : second.toInstant();
    
    return rangeFor(firstInstant, secondInstant);
  }
}
