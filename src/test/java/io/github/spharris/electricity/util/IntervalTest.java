package io.github.spharris.electricity.util;

import static com.google.common.truth.Truth.assertThat;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class IntervalTest {

  private static final Interval INTERVAL = Interval.of(
    LocalDate.of(2015, 1, 1).atStartOfDay(ZoneId.of("America/Los_Angeles")),
    LocalDate.of(2015, 2, 1).atStartOfDay(ZoneId.of("America/Los_Angeles")));
  
  @Test
  public void containsAtStart() {
    assertThat(INTERVAL.contains(INTERVAL.getStart())).isTrue();
  }
  
  @Test
  public void doesNotContainAtEnd() {
    assertThat(INTERVAL.contains(INTERVAL.getEnd())).isFalse();
  }
  
  @Test
  public void containsDate() {
    assertThat(INTERVAL.contains(LocalDate.of(2015, 1, 15).atStartOfDay(
      ZoneId.of("America/Los_Angeles")))).isTrue();
  }
  
  @Test
  public void doesNotOverlapBefore() {
    Interval other = Interval.of(
      INTERVAL.getStart().minus(Duration.ofDays(1)),
      INTERVAL.getStart());
    
    assertThat(other.overlaps(INTERVAL)).isFalse();
  }
  
  @Test
  public void doesNotOverlapAfter() {
    Interval other = Interval.of(
      INTERVAL.getEnd(),
      INTERVAL.getEnd().plus(Duration.ofDays(1)));
    
    assertThat(other.overlaps(INTERVAL)).isFalse();
  }
  
  @Test
  public void overlaps() {
    Interval other = Interval.of(
      INTERVAL.getStart().plus(Duration.ofDays(1)),
      INTERVAL.getEnd().plus(Duration.ofDays(1)));
    
    assertThat(other.overlaps(INTERVAL)).isTrue();
  }
  
  @Test
  public void abutsBefore() {
    Interval other = Interval.of(
      INTERVAL.getStart().minus(Duration.ofDays(1)),
      INTERVAL.getStart());
    
    assertThat(other.abuts(INTERVAL)).isTrue();
  }
  
  @Test
  public void abutsAfter() {
    Interval other = Interval.of(
      INTERVAL.getEnd(),
      INTERVAL.getEnd().plus(Duration.ofDays(1)));
    
    assertThat(other.abuts(INTERVAL)).isTrue();
  }
  
  @Test
  public void overlapDoesNotAbut() {
    Interval other = Interval.of(
      INTERVAL.getStart().plus(Duration.ofDays(1)),
      INTERVAL.getEnd());
    
    assertThat(other.abuts(INTERVAL)).isFalse();
  }
  
  @Test
  public void getsOverlap() {
    Interval other = Interval.of(
      INTERVAL.getStart().plus(Duration.ofDays(1)),
      INTERVAL.getEnd().plus(Duration.ofDays(1)));
    
    assertThat(other.overlap(INTERVAL).get()).isEqualTo(
      Interval.of(other.getStart(), INTERVAL.getEnd()));
  }
  
  @Test
  public void doesNotGetMissingOverlap() {
    Interval other = Interval.of(
      INTERVAL.getEnd(),
      INTERVAL.getEnd().plus(Duration.ofDays(1)));
    
    assertThat(other.overlap(INTERVAL)).isEqualTo(Optional.empty());
  }
}
