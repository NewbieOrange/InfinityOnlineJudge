package xyz.chengzi.ooad.repository;

import xyz.chengzi.ooad.exception.EntityAlreadyExistsException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.stream.Collectors;

public class JpaRepository<T> implements Repository<T>, AutoCloseable {
    private final EntityManager manager;
    private final Class<T> tClass;

    public JpaRepository(EntityManager entityManager, Class<T> tClass) {
        this.manager = entityManager;
        this.tClass = tClass;
    }

    @Override
    public void addAll(@Nonnull Iterable<T> items) {
        manager.getTransaction().begin();
        try {
            items.forEach(manager::persist);
            manager.getTransaction().commit();
        } catch (RollbackException e) {
            throw new EntityAlreadyExistsException(e);
        }
    }

    @Override
    public void update(@Nonnull T item) {
        manager.getTransaction().begin();
        manager.merge(item);
        manager.getTransaction().commit();
    }

    @Override
    public void removeAll(@Nonnull Iterable<T> items) {
        manager.getTransaction().begin();
        items.forEach(manager::remove);
        manager.getTransaction().commit();
    }

    @Nullable
    @Override
    public T findById(int id) {
        return manager.find(tClass, id);
    }

    @Nullable
    @Override
    public T find(@Nonnull Specification<T> specification) {
        List<T> resultList = findAll(specification, new EmptyOrder<>(), 1);
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    @Nonnull
    @Override
    public List<T> findAll(@Nonnull Specification<T> specification, Orders<T> orders, int maxResults) {
        manager.getTransaction().begin();

        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(tClass);
        Root<T> root = criteriaQuery.from(tClass);
        criteriaQuery.where(specification.toPredicate(root, criteriaBuilder));
        criteriaQuery.orderBy(orders.toOrders(root, criteriaBuilder));
        List<T> resultList = manager.createQuery(criteriaQuery).setMaxResults(maxResults).getResultList();

        manager.getTransaction().commit();
        return resultList;
    }

    @Override
    public void close() {
        manager.close();
    }
}
