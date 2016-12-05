package io.github.spharris.electricity.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.genability.client.api.request.AccountAnalysisRequest;
import com.genability.client.api.request.BaselineRequest;
import com.genability.client.api.request.BulkUploadRequest;
import com.genability.client.api.request.DeleteAccountRequest;
import com.genability.client.api.request.DeleteProfileRequest;
import com.genability.client.api.request.GetAccountRatesRequest;
import com.genability.client.api.request.GetAccountRequest;
import com.genability.client.api.request.GetAccountTariffsRequest;
import com.genability.client.api.request.GetAccountsRequest;
import com.genability.client.api.request.GetCalculatedCostRequest;
import com.genability.client.api.request.GetCalculationInputsRequest;
import com.genability.client.api.request.GetCalendarDatesRequest;
import com.genability.client.api.request.GetCalendarRequest;
import com.genability.client.api.request.GetCalendarsRequest;
import com.genability.client.api.request.GetIncentiveApplicabilitiesRequest;
import com.genability.client.api.request.GetIncentivesRequest;
import com.genability.client.api.request.GetLseRequest;
import com.genability.client.api.request.GetLsesRequest;
import com.genability.client.api.request.GetPriceRequest;
import com.genability.client.api.request.GetProfileRequest;
import com.genability.client.api.request.GetProfilesRequest;
import com.genability.client.api.request.GetPropertyKeyRequest;
import com.genability.client.api.request.GetPropertyKeysRequest;
import com.genability.client.api.request.GetPropertyLookupsRequest;
import com.genability.client.api.request.GetTerritoriesRequest;
import com.genability.client.api.request.GetTerritoryRequest;
import com.genability.client.api.request.GetTimeOfUseGroupsRequest;
import com.genability.client.api.request.GetTimeOfUseIntervalsRequest;
import com.genability.client.api.request.ReadingDataRequest;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

public class RequestGenerator {
 
  private static final ImmutableMap<Class<?>, Class<?>> PRIMITIVE_MAP =
      ImmutableMap.<Class<?>, Class<?>>builder()
      .put(int.class, Integer.class)
      .put(long.class, Long.class)
      .put(boolean.class, Boolean.class)
      .put(double.class, Double.class)
      .build();
  
  private static final ImmutableList<Class<?>> CLASSES = ImmutableList.of(
    AccountAnalysisRequest.class,
    BaselineRequest.class,
    BulkUploadRequest.class,
    DeleteAccountRequest.class,
    DeleteProfileRequest.class,
    GetAccountRatesRequest.class,
    GetAccountRequest.class,
    GetAccountsRequest.class,
    GetAccountTariffsRequest.class,
    GetCalculatedCostRequest.class,
    GetCalculationInputsRequest.class,
    GetCalendarDatesRequest.class,
    GetCalendarRequest.class,
    GetCalendarsRequest.class,
    GetIncentiveApplicabilitiesRequest.class,
    GetIncentivesRequest.class,
    GetLseRequest.class,
    GetLsesRequest.class,
    GetPriceRequest.class,
    GetProfileRequest.class,
    GetProfilesRequest.class,
    GetPropertyKeyRequest.class,
    GetPropertyKeysRequest.class,
    GetPropertyLookupsRequest.class,
    GetTerritoriesRequest.class,
    GetTerritoryRequest.class,
    GetTimeOfUseGroupsRequest.class,
    GetTimeOfUseIntervalsRequest.class,
    ReadingDataRequest.class);
    
    
  
  public static void main(String[] args) {
    for (Class<?> klazz : CLASSES) {
      String sn = klazz.getSimpleName();
      String outfile = String.format("src/main/java/com/genability/client/api/request/generated/%s.java", klazz.getSimpleName());
      System.out.println(outfile);
      File f = new File(outfile);
      
      try (PrintStream out = new PrintStream(f)) {
        out.println("package com.genability.client.api.request.generated;");
        out.println("");
        out.println("import static com.google.common.base.MoreObjects.firstNonNull;");
        out.println("");
        out.println("import com.genability.client.types.Fields;");
        out.println("import org.apache.http.NameValuePair;");
        out.println("");
        out.println("@AutoValue");
        out.printf("public abstract class %s extends %s2 {\n\n", sn, klazz.getSuperclass().getSimpleName());
        out.printf("  %s() {}\n\n", sn);
        fields(klazz).stream()
            .sorted((left, right) -> left.getName().compareTo(right.getName()))
            .forEachOrdered((field) -> {
              out.printf("  public abstract @Nullable %s get%s();\n", 
                formatParameterValue(field.getType(), field.getGenericType()),
                cap(field.getName()));
            });
        out.println("\n  public abstract Builder toBuilder();");
        out.println("  public static Builder builder() {");
        out.printf("    return new AutoValue_%s.Builder()\n", sn);
        out.println("        .setFields(Fields.EXT);");
        out.println("  }\n");
        out.println("  @AutoValue.Builder");
        out.printf("  public abstract static class Builder extends %s2.Builder<Builder> {\n\n", klazz.getSuperclass().getSimpleName());
        fields(klazz).stream()
            .sorted((left, right) -> left.getName().compareTo(right.getName()))
            .forEachOrdered((field) -> {              
              // Still print map fields
              if (!isListField(field)) {
                out.printf("    public abstract Builder set%s(@Nullable %s %s);\n",
                  cap(field.getName()),
                  formatParameterValue(field.getType(), field.getGenericType()),
                  trim(field.getName()));
              } else {
                Class<?> param = null;
                if (field.getType().isArray()) {
                  param = field.getType().getComponentType();
                } else {
                  param = Iterables.getOnlyElement(extractTypeParameters(field.getGenericType()));
                }
                out.printf("    public abstract Builder set%s(@Nullable %s... %s);\n",
                  cap(field.getName()),
                  param.getSimpleName(),
                  trim(field.getName()));
                out.printf("    public abstract Builder set%s(@Nullable %s %s);\n",
                  cap(field.getName()),
                  formatParameterValue(field.getType(), field.getGenericType()),
                  trim(field.getName()));
              }
            });
       
        out.printf("\n    public abstract %s build();\n", sn);
        out.println("  }\n");
        out.println("");
        out.println("  @Override");
        out.println("  @JsonIgnore");
        out.println("  public ImmutableList<NameValuePair> getQueryParams() {");
        out.println("    return getBaseQueryParams()");
        fields(klazz).stream()
            .sorted((left, right) -> left.getName().compareTo(right.getName()))
            .forEachOrdered((field) -> {
              out.printf("        .addParam(\"%s\", get%s())\n",
                trim(field.getName()),
                cap(field.getName()));
            });
        out.println("        .build();");
        out.println("  }");
        out.println("}");
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    }
  }
  
  private static boolean isListField(Field field) {
    return List.class.isAssignableFrom(field.getType())
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
