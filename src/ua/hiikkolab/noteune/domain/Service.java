package ua.hiikkolab.noteune.domain;

import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import ua.hiikkolab.noteune.persistence.entity.Entity;

public interface Service <E extends Entity>{

  E get(UUID id);

  Set<E> getAll();

  Set<E> getAll(Predicate<E> filter);

  E add(E entity);

  boolean remove(E entity);
}
