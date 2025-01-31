package ua.hiikkolab.noteune.persistence.repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import ua.hiikkolab.noteune.persistence.entity.Entity;

public interface Repository <E extends Entity>{

  Optional<E> findById(UUID id);

  Set<E> findAll();

  Set<E> findAll(Predicate<E> filter);

  E add(E entity);

  boolean remove(E entity);
}
