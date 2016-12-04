package com.genability.client.util;

import java.lang.reflect.Array;
import java.util.StringTokenizer;

import com.google.common.base.Joiner;

public class EnumUtil {

  @SuppressWarnings("unchecked")
  public static <E extends Enum<E>> E[] parseEnumList(String list, Class<E> clazz) {
    if (list == null) {
      return null;
    }
    final StringTokenizer tok = new StringTokenizer(list, ",");
    final int count = tok.countTokens();
    final E[] chargeTypes = (E[]) Array.newInstance(clazz, count);
    int i = 0;
    while (tok.hasMoreTokens()) {
      String name = tok.nextToken();
      E value = Enum.valueOf(clazz, name);
      chargeTypes[i++] = value;
    }
    return chargeTypes;
  }

  public static <E extends Enum<E>> String enumListString(Iterable<E> list) {
    if (list == null || !list.iterator().hasNext()) {
      return null;
    }
    
    return Joiner.on(",").join(list);
  }
  
  public static <E extends Enum<E>> String enumListString(E[] list) {
    if (list == null) {
      return null;
    }

    return Joiner.on(",").join(list);
  }

}
