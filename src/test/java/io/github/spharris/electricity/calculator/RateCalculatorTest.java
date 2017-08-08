package io.github.spharris.electricity.calculator;

import static com.google.common.truth.Truth.assertThat;

import com.genability.client.types.CalculatedCostItem;
import com.genability.client.types.ChargeType;
import com.genability.client.types.PropertyData;
import com.genability.client.types.RateUnit;
import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.testing.fieldbinder.BoundFieldModule;
import io.github.spharris.electricity.testing.TestEntities;
import io.github.spharris.electricity.util.Interval;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
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
  
  @Test
  public void calculatesSimpleConsumptionRate() {
    Interval interval = Interval.of(
      LocalDate.of(2015,  1,  1).atStartOfDay(TestEntities.ZONE_ID),
      LocalDate.of(2015,  2,  1).atStartOfDay(TestEntities.ZONE_ID));

    ImmutableList<CalculatedCostItem> result = rateCalculator.calculateRate(
      interval,
      TestEntities.TARIFF,
      TestEntities.RATE,
      ImmutableList.of(PropertyData.builder()
        .setKeyName("consumption")
        .setDataValue("1000")
        .build()));
    
    CalculatedCostItem expected = CalculatedCostItem.builder()
        .setFromDateTime(interval.getStart().atZone(TestEntities.ZONE_ID))
        .setToDateTime(interval.getEnd().atZone(TestEntities.ZONE_ID))
        .setQuantityKey("consumption")
        .setRateType(RateUnit.COST_PER_UNIT)
        .setRateAmount(TestEntities.RATE_AMOUNT)
        .setItemQuantity(BigDecimal.valueOf(1000))
        .setCost(TestEntities.RATE_AMOUNT.multiply(BigDecimal.valueOf(1000)))
        .setChargeType(ChargeType.CONSUMPTION_BASED)
        .build();
    
    assertThat(result).containsExactly(expected);
  }
}
