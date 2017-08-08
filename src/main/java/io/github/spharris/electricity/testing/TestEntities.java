package io.github.spharris.electricity.testing;

import com.genability.client.types.ChargeType;
import com.genability.client.types.Period;
import com.genability.client.types.RateUnit;
import com.genability.client.types.Tariff;
import com.genability.client.types.TariffRate;
import com.genability.client.types.TariffRateBand;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

public final class TestEntities {
  
  private TestEntities() {}
 
  public static final ZoneId ZONE_ID = ZoneId.of("America/Los_Angeles");

  public static final BigDecimal RATE_AMOUNT = BigDecimal.ONE;
  public static final TariffRate RATE = createSimpleConsumptionRate(RATE_AMOUNT); 
  
  public static TariffRate createSimpleConsumptionRate(BigDecimal rate) {
    return TariffRate.builder()
        .setChargeType(ChargeType.CONSUMPTION_BASED)
        .setChargePeriod(Period.MONTHLY)
        .setRateBands(createRateBand(rate))
        .build();
  }
  
  public static TariffRateBand createRateBand(BigDecimal rateAmount) {
    return TariffRateBand.builder()
        .setRateAmount(rateAmount)
        .setRateUnit(RateUnit.COST_PER_UNIT)
        .setIsCredit(false)
        .build();
  }
  
  public static final Tariff TARIFF = Tariff.builder()
      .setEffectiveDate(LocalDate.of(2015,  1,  1))
      .setTimeZone(ZONE_ID)
      .setRates(RATE)
      .build();
}
