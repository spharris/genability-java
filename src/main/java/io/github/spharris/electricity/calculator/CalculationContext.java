package io.github.spharris.electricity.calculator;

import com.genability.client.types.PropertyData;
import com.genability.client.types.Tariff;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import io.github.spharris.electricity.util.RangeUtil;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A container for information relating to the cost calculation.
 */
@AutoValue
abstract class CalculationContext {

  private static final CalculationContext DEFAULT_CONTEXT = CalculationContext.builder().build();
  
  private ImmutableMap<String, ImmutableRangeMap<Instant, PropertyData>> propertyCache;
  
  abstract ImmutableList<Tariff> getTariffs();

  abstract ImmutableList<PropertyData> getInputProperties();

  abstract Builder toBuilder();
  static Builder builder() {
    return new AutoValue_CalculationContext.Builder().setTariffs(ImmutableList.of())
        .setInputProperties(ImmutableList.of());
  }

  static CalculationContext getDefaultContext() {
    return DEFAULT_CONTEXT;
  }
  
  Tariff getTariffById(long id) {
    return getTariffs()
        .stream()
        .filter(t -> t.getMasterTariffId() == id)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Tariff ID not found in context."));
  }
  
  Optional<PropertyData> getPropertyForInstant(String keyName, Instant instant) {
    if (propertyCache == null) {
      synchronized(this) {
        initializePropertyCache();
      }
    }
    
    ImmutableRangeMap<Instant, PropertyData> valuesForKey = propertyCache.get(keyName);
    if (valuesForKey == null) {
      return Optional.empty();
    }
    
    return Optional.ofNullable(valuesForKey.get(instant));
  }
  
  private void initializePropertyCache() {
    Map<String, RangeMap<Instant, PropertyData>> builder = new HashMap<>();
    for (PropertyData pd : getInputProperties()) {
      builder.putIfAbsent(pd.getKeyName(), TreeRangeMap.create());
      builder.get(pd.getKeyName())
          .put(RangeUtil.rangeFor(pd.getFromDateTime(), pd.getToDateTime()), pd);
    }
    
    ImmutableMap.Builder<String, ImmutableRangeMap<Instant, PropertyData>> cacheBuilder =
        ImmutableMap.builder();
    builder.forEach((name, range) -> cacheBuilder.put(name, ImmutableRangeMap.copyOf(range)));
    
    propertyCache = cacheBuilder.build(); 
  }

  @AutoValue.Builder
  abstract static class Builder {

    abstract Builder setTariffs(Iterable<Tariff> tariffs);

    abstract Builder setInputProperties(Iterable<PropertyData> inputProperties);

    abstract CalculationContext build();
  }

}
