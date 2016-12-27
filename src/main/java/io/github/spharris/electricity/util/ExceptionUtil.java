package io.github.spharris.electricity.util;

import javax.ws.rs.BadRequestException;

public final class ExceptionUtil {
  
  private ExceptionUtil() {}
  
  public static void checkBadRequest(boolean expression) {
    if (!expression) {
      throw new BadRequestException();
    }
  }
}
