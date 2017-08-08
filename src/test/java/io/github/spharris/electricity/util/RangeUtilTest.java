package io.github.spharris.electricity.util;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.Range;
import java.time.Instant;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class RangeUtilTest {

  @Rule public ExpectedException thrown = ExpectedException.none();
  
  @Test
  public void nullDatesCoversAllValues() {
    Instant first = null;
    Instant second = null;
    Range<Instant> range = RangeUtil.rangeFor(first, second);
    
    assertThat(range.contains(Instant.MIN)).isTrue();
    assertThat(range.contains(Instant.MAX)).isTrue();
  }
  
  @Test
  public void firstDateCoversAllGreaterThanOrEqualTo() {
    Instant now = Instant.now();
    Range<Instant> range = RangeUtil.rangeFor(now, null);
    
    assertThat(range.contains(Instant.MAX)).isTrue();
    assertThat(range.contains(Instant.MIN)).isFalse();
    assertThat(range.contains(now)).isTrue();
    assertThat(range.contains(now.minusNanos(1L))).isFalse();
  }
  
  @Test
  public void secondDateCoversAllLessThan() {
    Instant now = Instant.now();
    Range<Instant> range = RangeUtil.rangeFor(null, now);
    
    assertThat(range.contains(Instant.MAX)).isFalse();
    assertThat(range.contains(Instant.MIN)).isTrue();
    assertThat(range.contains(now)).isFalse();
    assertThat(range.contains(now.minusNanos(1L))).isTrue();
  }
  
  @Test
  public void requiresFirstLessEqualSecond() {
    thrown.expect(IllegalArgumentException.class);
    RangeUtil.rangeFor(Instant.MAX, Instant.MIN);
  }
  
  @Test
  public void doesNotIncludeEnd() {
    Range<Instant> range = RangeUtil.rangeFor(Instant.MIN, Instant.MAX);
    
    assertThat(range.contains(Instant.MAX)).isFalse();
    assertThat(range.contains(Instant.MIN)).isTrue();
  }
}
