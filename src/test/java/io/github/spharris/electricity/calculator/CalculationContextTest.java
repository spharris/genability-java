package io.github.spharris.electricity.calculator;

import static com.google.common.truth.Truth.assertThat;

import com.genability.client.types.PropertyData;
import com.genability.client.types.Tariff;
import com.google.common.collect.ImmutableList;
import java.time.Instant;
import java.util.Optional;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class CalculationContextTest {

  @Rule public ExpectedException thrown = ExpectedException.none();
  
  @Test
  public void invalidIdThrowsIae() {
    thrown.expect(IllegalArgumentException.class);
    
    CalculationContext context = CalculationContext.builder().build();
    context.getTariffById(1L);
  }
  
  @Test
  public void getsTariffById() {
    Tariff tariff = Tariff.builder().setMasterTariffId(1L).build();
    
    CalculationContext context = CalculationContext.builder()
        .setTariffs(ImmutableList.of(tariff))
        .build();
    
    Tariff result = context.getTariffById(1L);
    
    assertThat(result).isEqualTo(tariff);
  }
  
  @Test
  public void getsPropertyForInstant() {
    
  }
  
  @Test
  public void getsEmptyPropertyWhenMissing() {
    CalculationContext context = CalculationContext.getDefaultContext();
    Optional<PropertyData> data = context.getPropertyForInstant("property", Instant.now());
    
    assertThat(data.isPresent()).isFalse();
  }
}
