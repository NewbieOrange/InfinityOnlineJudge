package xyz.chengzi.ooad.repository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public interface Repository<T> extends AutoCloseable {
    /**
     * Add the given item to the repository.
     *
     * @param item the item.
     */
    default void add(@Nonnull T item) {
        addAll(Collections.singleton(item));
    }

    /**
     * Update the given item into the repository.
     *
     * @param item the item.
     */
    default void update(@Nonnull T item) {
        updateAll(Collections.singleton(item));
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
     */
    void addAll(@Nonnull Iterable<T> items);

    /**
     * Update the given items into the repository.
     *
     * @param items the items.
     */
    void updateAll(@Nonnull Iterable<T> items);

    /**
     * Remove the give items from the repository.
     *
     * @param items the items.
     */
    void removeAll(@Nonnull Iterable<T> items);

    /**
     * Find all the items specifying the specification in the repository.
     *
     * @param specification the specification.
     * @return the list of the items (empty list if none).
     */
    @Nonnull
    default List<T> findAll(@Nonnull Specification<T> specification) {
        return findAll(specification, new EmptyOrder<>(), Integer.MAX_VALUE);
    }

    /**
     * Find all the items specifying the specification in the repository.
     *
     * @param specification the specification.
     * @param orders        the query orders.
     * @return the list of the items (empty list if none).
     */
    @Nonnull
    default List<T> findAll(@Nonnull Specification<T> specification, Orders<T> orders, int maxResults) {
        return findAll(specification, orders, new EmptyGroups<>(), false, maxResults);
    }

    /**
     * Find all the items specifying the specification in the repository.
     *
     * @param specification the specification.
     * @param maxResults    the maximum results size.
     * @return the list of the items (empty list if none).
     */
    @Nonnull
    default List<T> findAll(@Nonnull Specification<T> specification, int maxResults) {
        return findAll(specification, new EmptyOrder<>(), new EmptyGroups<>(), false, maxResults);
    }

    /**
     * Find all the items specifying the specification in the repository.
     *
     * @param specification the specification.
     * @param orders        the query orders.
     * @param groups        the query groups.
     * @param distinct      whether distinct results or not.
     * @param maxResults    the maximum results size.
     * @return the list of the items (empty list if none).
     */
    @Nonnull
    List<T> findAll(@Nonnull Specification<T> specification, Orders<T> orders, Groups<T> groups,
                    boolean distinct, int maxResults);
}
