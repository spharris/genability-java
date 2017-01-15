package io.github.spharris.electricity.testing;

import com.genability.client.types.Tariff;
import com.genability.client.types.TariffRate;
import java.time.LocalDate;
import java.time.ZoneId;

public final class TestEntities {
  
  private TestEntities() {}
 
  public static final ZoneId ZONE_ID = ZoneId.of("America/Los_Angeles");
  
  public static final TariffRate RATE = TariffRate.builder().build();
  
  public static final Tariff TARIFF = Tariff.builder()
      .setEffectiveDate(LocalDate.of(2015,  1,  1))
      .setTimeZone(ZONE_ID)
      .setRates(RATE)
      .build();
}
