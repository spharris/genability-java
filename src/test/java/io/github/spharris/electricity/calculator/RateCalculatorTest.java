package io.github.spharris.electricity.calculator;

import static com.google.common.truth.Truth.assertThat;

import com.genability.client.types.CalculatedCostItem;
import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.testing.fieldbinder.BoundFieldModule;
import io.github.spharris.electricity.testing.TestEntities;
import io.github.spharris.electricity.util.Interval;
import java.time.Instant;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class RateCalculatorTest {
  
  @Inject private RateCalculator rateCalculator;
  
  @Before
  public void createInjector() {
    Guice.createInjector(BoundFieldModule.of(this)).injectMembers(this);
  }
  
  @Test
  public void emptyListOfPropertiesReturnsNoCostItems() {
    ImmutableList<CalculatedCostItem> result = rateCalculator.calculateRate(
      Interval.of(Instant.EPOCH, Instant.MAX),
      TestEntities.TARIFF,
      TestEntities.RATE,
      ImmutableList.of());
    
    assertThat(result).isEmpty();
  }
}
