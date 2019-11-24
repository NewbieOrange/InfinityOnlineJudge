package xyz.chengzi.ooad.repository;

import xyz.chengzi.ooad.exception.EntityAlreadyExistsException;
import xyz.chengzi.ooad.exception.EntityNotFoundException;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public interface Repository<T> extends AutoCloseable {
    /**
     * Add the given item to the repository.
     *
     * @param item the item.
     * @throws EntityAlreadyExistsException the object already exists (or one with the same unique fields).
     */
    default void add(@Nonnull T item) {
        addAll(Collections.singleton(item));
    }

    /**
     * Remove the given item from the repository.
     *
     * @param item the item.
     */
    default void remove(@Nonnull T item) {
        removeAll(Collections.singleton(item));
    }

    /**
     * Find a specific item from the repository by its id.
     *
     * @param id the id of the item.
     * @return the item.
     * @throws EntityNotFoundException If no item with the given id can be found.
     */
    @Nonnull
    T findById(int id) throws EntityNotFoundException;

    /**
     * Find the first item specifying the specification in the repository.
     * <p>
     * If there are multiple matches, only the first one will be returned.
     *
     * @param specification the specification.
     * @return the item.
     * @throws EntityNotFoundException If no item with the given specification can be found.
     */
    @Nonnull
    default T find(@Nonnull Specification<T> specification) throws EntityNotFoundException {
        Iterator<T> iterator = findAll(specification).iterator();
        if (!iterator.hasNext()) {
            throw new EntityNotFoundException(specification);
        }
        return iterator.next();
    }

    /**
     * Return a collection of all the items in the repository.
     *
     * @return the collection.
     */
    @Nonnull
    default Collection<T> listAll() {
        return findAll(new EmptySpecification<>());
    }

    /**
     * Add the given items to the repository.
     * <p>
     * If any exception occur during the operation, nothing will be added.
     *
     * @param items the items.
     * @throws EntityAlreadyExistsException the object already exists (or one with the same unique fields).
     */
    void addAll(@Nonnull Iterable<T> items);

    /**
     * Update the given item into the repository.
     *
     * @param item the item.
     */
    void update(@Nonnull T item);

    /**
     * Remove the give items from the repository.
     *
     * @param items the items.
     */
    void removeAll(@Nonnull Iterable<T> items);

    /**
     * Remove all the items specifying the specification from the repository.
     *
     * @param specification the specification.
     * @return the list of removed items (empty list if none).
     */
    @Nonnull
    List<T> removeAll(@Nonnull Specification<T> specification);

    /**
     * Find all the items specifying the specification in the repository.
     *
     * @param specification the specification.
     * @return the list of the items (empty list if none).
     */
    @Nonnull
    List<T> findAll(@Nonnull Specification<T> specification);
}
