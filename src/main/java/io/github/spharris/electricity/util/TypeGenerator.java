package io.github.spharris.electricity.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.genability.client.types.TariffProperty;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

/** Generates AutoValue-compatible classes from Genability Java client classes */
public class TypeGenerator {
  
  private static final ImmutableMap<Class<?>, Class<?>> PRIMITIVE_MAP =
      ImmutableMap.<Class<?>, Class<?>>builder()
      .put(int.class, Integer.class)
      .put(long.class, Long.class)
      .put(boolean.class, Boolean.class)
      .put(double.class, Double.class)
      .build();
  
  private static final ImmutableList<Class<?>> CLASSES = ImmutableList.of(
    /*Account.class,
    AccountAnalysis.class,
    Address.class,
    Baseline.class,
    BaselineMeasure.class,
    CalculatedCost.class,
    CalculatedCostItem.class,
    Calendar.class,
    CalendarEvent.class,
    CalendarEventDate.class,
    Choice.class,
    Coordinates.class,
    Incentive.class,
    IncentiveApplicability.class,
    IntervalInfo.class,
    Lse.class,
    Measure.class,
    Price.class,
    PriceChange.class,
    Profile.class,
    PropertyChoice.class,
    PropertyData.class,
    PropertyKey.class,
    PropertyLookup.class,
    PropertyLookupStats.class,
    ReadingData.class,
    ReadingDataSummary.class,
    //Response.class,
    Scenario.class,
    Season.class,
    SeasonGroup.class,
    Series.class,
    SeriesMeasure.class,
    Source.class,
    TariffRateChangePeriod.class,
    Territory.class,
    TerritoryItem.class,
    TerritoryLse.class,
    TimeOfUse.class,
    TimeOfUseGroup.class,
    TimeOfUseInterval.class,
    TimeOfUsePeriod.class*/TariffProperty.class);
    
    
  
  public static void main(String[] args) {
    for (Class<?> klazz : CLASSES) {
      String sn = klazz.getSimpleName();
      String outfile = String.format("src/main/java/com/genability/client/types/generated/%s.java", klazz.getSimpleName());
      System.out.println(outfile);
      File f = new File(outfile);
      
      List<Field> collectionFields = new ArrayList<>();
      try (PrintStream out = new PrintStream(f)) {
        out.println("package com.genability.client.types.generated;");
        out.println("");
        out.println("import static com.google.common.base.MoreObjects.firstNonNull;");
        out.println("");
        out.println("@AutoValue");
        out.printf("@JsonDeserialize(builder = AutoValue_%s.Builder.class)\n", sn);
        out.printf("public abstract class %s {\n\n", sn);
        fields(klazz).stream()
            .sorted((left, right) -> left.getName().compareTo(right.getName()))
            .forEachOrdered((field) -> {
              out.printf("  public abstract @Nullable %s get%s();\n", 
                formatParameterValue(field.getType(), field.getGenericType()),
                cap(field.getName()));
            });
        out.println("\n  public abstract Builder toBuilder();");
        out.println("  public static Builder builder() {");
        out.printf("    return new AutoValue_%s.Builder();\n", sn);
        out.println("  }\n");
        out.println("  @AutoValue.Builder");
        out.println("  @JsonPOJOBuilder(buildMethodName = \"build\", withPrefix = \"set\")");
        out.println("  public abstract static class Builder {\n");
        fields(klazz).stream()
            .sorted((left, right) -> left.getName().compareTo(right.getName()))
            .forEachOrdered((field) -> {
              if (isCollectionField(field)) {
                collectionFields.add(field);
              }
              
              // Still print map fields
              if (!isListField(field)) {
                out.printf("    public abstract Builder set%s(@Nullable %s %s);\n",
                  cap(field.getName()),
                  formatParameterValue(field.getType(), field.getGenericType()),
                  trim(field.getName()));
              }
            });
        if (collectionFields.isEmpty()) {
          out.printf("\n    public abstract %s build();\n", sn);
        } else {
          out.println("");
          for (Field field : collectionFields) {
            // Print array and collection setters.
            if (isListField(field)) {
              Class<?> param = null;
              if (field.getType().isArray()) {
                param = field.getType().getComponentType();
              } else {
                param = Iterables.getOnlyElement(extractTypeParameters(field.getGenericType()));
              }
               out.println("    @JsonIgnore");
              out.printf("    public abstract Builder set%s(@Nullable %s... %s);\n\n",
                cap(field.getName()),
                param.getSimpleName(),
                trim(field.getName()));
              out.printf("    @JsonProperty(\"%s\")\n", field.getName());
              out.printf("    public abstract Builder set%s(@Nullable %s %s);\n\n",
                  cap(field.getName()),
                  formatParameterValue(field.getType(), field.getGenericType()),
                  trim(field.getName()));
            }
          }
          
          // Make sure collection values are never null
          for (Field field : collectionFields) {
            out.printf("    protected abstract %s get%s();\n",
              formatParameterValue(field.getType(), field.getGenericType()),
              cap(field.getName()));
          }
          
          out.printf("    protected abstract %s autoBuild();\n\n", sn);
          out.printf("    public %s build() {\n", sn);
          for (Field field : collectionFields) {
            out.printf("      set%s(firstNonNull(get%s(), %s));\n",
              cap(field.getName()),
              cap(field.getName()),
              emptyCollection(field.getType(), field.getGenericType()));
          }
          out.println("      return autoBuild();");
          out.println("    }");
        }
        out.println("  }");
        out.println("}");
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    }
  }
  
  private static String emptyCollection(Class<?> klazz, Type type) {
    if (List.class.isAssignableFrom(klazz)) {
      Class<?> typeParam = Iterables.getOnlyElement(extractTypeParameters(type));
      if (typeParam.isEnum()) {
        return "ImmutableSet.of()";
      } else {
        return "ImmutableList.of()";
      }
    } else if (Map.class.isAssignableFrom(klazz)) {
      return "ImmutableMap.of()";
    } else if (klazz.isArray()) {
      Class<?> typeParam = klazz.getComponentType();
      if (typeParam.isEnum()) {
        return "ImmutableSet.of()";
      } else {
        return "ImmutableList.of()";
      }
    }
    
    throw new IllegalArgumentException("Unexpected type: " + klazz);
  }
  
  private static boolean isListField(Field field) {
    return List.class.isAssignableFrom(field.getType())
        || field.getType().isArray();
  }
  
  private static boolean isCollectionField(Field field) {
    return List.class.isAssignableFrom(field.getType())
        || Map.class.isAssignableFrom(field.getType())
        || field.getType().isArray();
  }
  
  private static String formatParameterValue(Class<?> klazz, Type type) {
    if (List.class.isAssignableFrom(klazz)) {
      Class<?> typeParam = Iterables.getOnlyElement(extractTypeParameters(type));
      if (typeParam.isEnum()) {
        return String.format("ImmutableSet<%s>", typeParam.getSimpleName());
      } else {
        return String.format("ImmutableList<%s>", typeParam.getSimpleName());
      }
    } else if (Map.class.isAssignableFrom(klazz)) {
      return String.format("ImmutableMap<%s>", Joiner.on(", ").join(
        extractTypeParameters(type).stream()
            .map(Class::getSimpleName)
            .collect(Collectors.toList())));
    } else if (klazz.isPrimitive()) {
      return PRIMITIVE_MAP.get(klazz).getSimpleName();
    } else if (klazz.isArray()) {
      return String.format("ImmutableList<%s>", klazz.getComponentType().getSimpleName());
    } else {
      return klazz.getSimpleName();
    }
  }
  
  private static ImmutableList<Class<?>> extractTypeParameters(Type type) {
    Type[] arguments = ((ParameterizedType) type).getActualTypeArguments();

    ImmutableList.Builder<Class<?>> builder = ImmutableList.builder();
    try {
      for (Type t : arguments) {
        Class<?> paramClass = Class.forName(t.getTypeName());
        builder.add(paramClass);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    
    return builder.build();
  }
  
  private static List<Field> fields(Class<?> klazz) {
    return Arrays.stream(klazz.getDeclaredFields())
        .filter((field) -> !Modifier.isStatic(field.getModifiers()))
        .collect(Collectors.toList());
  }
  
  private static String cap(String str) {
    return Character.toUpperCase(str.charAt(0)) + trim(str.substring(1));
  }
  
  private static String trim(String str) {
    return str.replace("_", "");
  }
}
