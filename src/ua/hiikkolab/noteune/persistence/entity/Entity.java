package ua.hiikkolab.noteune.persistence.entity;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

public abstract class Entity implements Serializable, Comparable<Entity> {
  protected final UUID id;
  protected transient Set<String> errors;
  protected transient boolean isValid;
  protected Entity(UUID id) {
    this.id = id;
  }
  protected Entity(UUID id, Set<String> errors) {
    this.id = id;
    this.errors = errors;
  }

  public UUID getId() {
    return id;
  }
  public Set<String> getErrors() {
    return errors;
  }
  public boolean isValid() {
    return !errors.isEmpty();
  }
  @Override
  public int compareTo(Entity o) {
    return id.compareTo(o.id);
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Entity entity = (Entity) o;
    return this.id.equals(entity.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
