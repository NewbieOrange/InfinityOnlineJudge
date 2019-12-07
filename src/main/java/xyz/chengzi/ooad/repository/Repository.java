package xyz.chengzi.ooad.repository;

import xyz.chengzi.ooad.exception.EntityAlreadyExistsException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
     * @return the item, null if not found.
     */
    @Nullable
    T findById(int id);

    /**
     * Find the first item specifying the specification in the repository.
     * <p>
     * If there are multiple matches, only the first one will be returned.
     *
     * @param specification the specification.
     * @return the item, null if not found.
     */
    @Nullable
    default T find(@Nonnull Specification<T> specification) {
        Iterator<T> iterator = findAll(specification).iterator();
        if (!iterator.hasNext()) {
            return null;
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
    default List<T> findAll(@Nonnull Specification<T> specification) {
        return findAll(specification, Integer.MAX_VALUE);
    }

    /**
     * Find all the items specifying the specification in the repository.
     *
     * @param specification the specification.
     * @param maxResults the maximum results size.
     * @return the list of the items (empty list if none).
     */
    @Nonnull
    List<T> findAll(@Nonnull Specification<T> specification, int maxResults);
}
