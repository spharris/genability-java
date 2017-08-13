package io.github.spharris.electricity.calculator;

import static com.google.common.truth.Truth.assertThat;

import com.genability.client.types.PropertyData;
import com.genability.client.types.Tariff;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CalculationContextTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void invalidIdThrowsIae() {
    thrown.expect(IllegalArgumentException.class);

    CalculationContext context = CalculationContext.builder().build();
    context.getTariffById(1L);
  }

  @Test
  public void getsTariffById() {
    Tariff tariff = Tariff.builder().setMasterTariffId(1L).build();

    CalculationContext context =
        CalculationContext.builder().setTariffs(ImmutableList.of(tariff)).build();

    Tariff result = context.getTariffById(1L);

    assertThat(result).isEqualTo(tariff);
  }

  @Test
  public void getsPropertyForInstant() {
    PropertyData property = PropertyData.builder().setKeyName("property").setDataValue("1").build();

    CalculationContext context =
        CalculationContext.builder().setInputProperties(ImmutableList.of(property)).build();

    Optional<PropertyData> data = context.getPropertyForInstant("property", Instant.now());

    assertThat(data.get()).isEqualTo(property);
  }

  @Test
  public void getsEmptyPropertyWhenMissing() {
    CalculationContext context = CalculationContext.getDefaultContext();
    Optional<PropertyData> data = context.getPropertyForInstant("property", Instant.now());

    assertThat(data.isPresent()).isFalse();
  }

  @Test
  public void returnsEmptyListWithNoProperties() {
    CalculationContext context = CalculationContext.getDefaultContext();

    Instant start = Instant.now();
    Instant end = start.plus(Duration.ofHours(1));
    ImmutableList<PropertyData> result =
        context.getPropertiesForInterval("property", Range.closedOpen(start, end));

    assertThat(result).isEmpty();
  }

  @Test
  public void returnsAllPropertiesOverlappingRange() {
    Instant start = Instant.now();
    PropertyData p1 = PropertyData.builder()
        .setFromDateTime(start.atZone(ZoneId.of("UTC")))
        .setToDateTime(start.plus(Duration.ofMinutes(30)).atZone(ZoneId.of("UTC")))
        .setKeyName("consumption")
        .setDataValue("1")
        .build();

    PropertyData p2 = PropertyData.builder().setFromDateTime(p1.getToDateTime())
        .setToDateTime(p1.getToDateTime().plusMinutes(30))
        .setKeyName("consumption")
        .setDataValue("1")
        .build();

    CalculationContext context =
        CalculationContext.builder().setInputProperties(ImmutableList.of(p1, p2)).build();

    ImmutableList<PropertyData> result = context.getPropertiesForInterval("consumption",
        Range.closedOpen(start, start.plus(Duration.ofMinutes(30))));

    assertThat(result).containsExactly(p1);
  }
  
  @Test
  public void propertiesAreReturnedInOrder() {
    Instant start = Instant.parse("2017-01-01T00:00:00Z");
    PropertyData p1 = PropertyData.builder()
        .setFromDateTime(start.atZone(ZoneId.of("UTC")))
        .setToDateTime(start.plus(Duration.ofMinutes(30)).atZone(ZoneId.of("UTC")))
        .setKeyName("consumption")
        .setDataValue("1")
        .build();

    PropertyData p2 = PropertyData.builder()
        .setFromDateTime(p1.getToDateTime())
        .setToDateTime(p1.getToDateTime().plusMinutes(30))
        .setKeyName("consumption")
        .setDataValue("2")
        .build();

    // Build context in reverse-chronological order
    CalculationContext context =
        CalculationContext.builder().setInputProperties(ImmutableList.of(p2, p1)).build();

    ImmutableList<PropertyData> result = context.getPropertiesForInterval("consumption",
        Range.closedOpen(start, start.plus(Duration.ofMinutes(31))));

    assertThat(result).containsExactly(p1, p2).inOrder();
  }
}
