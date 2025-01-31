package ua.hiikkolab.noteune.domain.impl;

import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import ua.hiikkolab.noteune.domain.Service;
import ua.hiikkolab.noteune.domain.exception.EntityNotFoundException;
import ua.hiikkolab.noteune.persistence.entity.Entity;
import ua.hiikkolab.noteune.persistence.repository.Repository;

public class BasicService <E extends Entity> implements Service<E> {
  private final Repository<E> repository;

  public BasicService(Repository<E> repository) {
    this.repository = repository;
  }

  @Override
  public E get(UUID id) {
    return repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(
            "Ми не знайшли запис по вказаному ідентифікатору"));
  }

  @Override
  public Set<E> getAll() {
    return repository.findAll();
  }

  @Override
  public Set<E> getAll(Predicate<E> filter) {
    return repository.findAll(filter);
  }

  @Override
  public E add(E entity) {
    return repository.add(entity);
  }

  @Override
  public boolean remove(E entity) {
    return repository.remove(entity);
  }
}
