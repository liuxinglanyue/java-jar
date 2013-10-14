package org.terracotta.management.resource.exceptions;

/**
 * Common REST exception class.
 *
 * @author Ludovic Orban
 */
public class ResourceRuntimeException extends RuntimeException {

  private final int statusCode;

  public ResourceRuntimeException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public ResourceRuntimeException(String message, Throwable t, int statusCode) {
    super(message, t);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }
}
