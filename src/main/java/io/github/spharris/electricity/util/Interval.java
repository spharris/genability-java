package io.github.spharris.electricity.util;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Optional;

/**
 * Immutable class representing a time interval. Intervals are "closed/open" on their start and
 * end times, respectively.
 */
@AutoValue
public abstract class Interval {

  public abstract Instant getStart();
  public abstract Instant getEnd();
  
  public static Interval of(ZonedDateTime start, ZonedDateTime end) {
    return new AutoValue_Interval(start.toInstant(), end.toInstant());
  }
  
  public static Interval of(Instant start, Instant end) {
    checkArgument(!end.isBefore(start));

    return new AutoValue_Interval(start, end);
  }
  
  public Duration getDuration() {
    return Duration.between(getStart(), getEnd());
  }
  
  public boolean contains(ZonedDateTime time) {
    return contains(time.toInstant());
  }
  
  public boolean contains(Instant instant) {
    return getStart().compareTo(instant) <= 0 && instant.compareTo(getEnd()) < 0;
  }
  
  public boolean overlaps(Interval other) {
    return other.getEnd().isAfter(getStart()) && other.getStart().isBefore(getEnd());
  }
  
  public Optional<Interval> overlap(Interval other) {
    if (!overlaps(other)) {
      return Optional.empty();
    }
    
    return Optional.of(Interval.of(
      Collections.max(ImmutableList.of(getStart(), other.getStart())),
      Collections.min(ImmutableList.of(getEnd(), other.getEnd()))));
  }
  
  public boolean abuts(Interval other) {
    return other.getStart().equals(getEnd()) || other.getEnd().equals(getStart());
  }
  
  @Override
  public String toString() {
    return getStart().toString() + " - " + getEnd().toString();
  }
}
