package org.pt.flightbooking.adapters.exception.mappings.constants;

public enum ExceptionMappings {
  ClientSystemNotFoundShort("ClientSystem with name"),
  ClientSystemNotFound("ClientSystem with name [client_system] was not found.");

  public final String label;

  ExceptionMappings(final String label) {
    this.label = label;
  }
}