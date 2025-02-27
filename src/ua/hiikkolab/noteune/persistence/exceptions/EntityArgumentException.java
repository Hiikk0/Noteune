package ua.hiikkolab.noteune.persistence.exceptions;

import java.util.Set;

public class EntityArgumentException extends IllegalArgumentException {

  /**
   * Set of error messages describing the validation issues with the entity fields.
   */
  private final Set<String> errors;

  /**
   * Constructs a new {@code EntityArgumentException} with the specified list of error messages.
   *
   * @param errors the list of error messages indicating validation issues with entity fields
   */
  public EntityArgumentException(Set<String> errors) {
    this.errors = errors;
  }

  /**
   * Retrieves the set of error messages associated with this exception.
   *
   * @return the set of error messages
   */
  public Set<String> getErrors() {
    return errors;
  }
}
