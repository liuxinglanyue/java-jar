package org.terracotta.management.resource.exceptions;

import java.lang.reflect.InvocationTargetException;

/**
 * Misc. utility methods that work on exceptions.
 *
 * @author Ludovic Orban
 */
public class ExceptionUtils {

  public static Throwable getRootCause(Throwable t) {
    Throwable last = null;
    while (t != null && t != last) {
      last = t;
      t = t.getCause();
    }
    if (last instanceof InvocationTargetException) {
      last = ((InvocationTargetException)last).getTargetException();
    }
    return last;
  }

  public static String toJsonError(Throwable t) {
    String errorMessage;
    if (t == null) {
      errorMessage = "";
    } else {
      String message = t.getMessage();
      errorMessage = message == null ? "" : message.replace('\"', '\'');
    }

    String extraErrorMessage = "";
    Throwable rootCause = getRootCause(t);
    if (rootCause != t && rootCause != null && rootCause.getMessage() != null) {
      extraErrorMessage = rootCause.getMessage().replace('\"', '\'');
    }

    return String.format("{\"error\" : \"%s\" , \"details\" : \"%s\"}", errorMessage, extraErrorMessage);
  }

}
