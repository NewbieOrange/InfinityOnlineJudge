package xyz.chengzi.ooad.repository;

import xyz.chengzi.ooad.exception.EntityAlreadyExistsException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.List;

public class JpaRepository<T> implements Repository<T>, AutoCloseable {
    private final ThreadLocal<EntityManager> localManager;
    private final Class<T> tClass;

    public JpaRepository(EntityManagerFactory entityManagerFactory, Class<T> tClass) {
        localManager = ThreadLocal.withInitial(entityManagerFactory::createEntityManager);
        this.tClass = tClass;
    }

    @Override
    public void addAll(@Nonnull Iterable<T> items) {
        EntityManager manager = localManager.get();
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
        EntityManager manager = localManager.get();
        manager.getTransaction().begin();
        manager.merge(item);
        manager.getTransaction().commit();
    }

    @Override
    public void removeAll(@Nonnull Iterable<T> items) {
        EntityManager manager = localManager.get();
        manager.getTransaction().begin();
        items.forEach(manager::remove);
        manager.getTransaction().commit();
    }

    @Nonnull
    @Override
    public List<T> removeAll(@Nonnull Specification<T> specification) {
        EntityManager manager = localManager.get();
        JpqlSpecification<T> jpqlSpecification = (JpqlSpecification<T>) specification;
        manager.getTransaction().begin();
        List<T> itemsToRemove = jpqlSpecification
                .prepare(manager.createQuery(
                        "SELECT e FROM " + tClass.getSimpleName() + " e WHERE " + jpqlSpecification.toJpqlQuery(),
                        tClass))
                .getResultList();
        itemsToRemove.forEach(manager::remove);
        manager.getTransaction().commit();
        return itemsToRemove;
    }

    @Nullable
    @Override
    public T findById(int id) {
        EntityManager manager = localManager.get();
        return manager.find(tClass, id);
    }

    @Nullable
    @Override
    public T find(@Nonnull Specification<T> specification) {
        List<T> resultList = findAll(specification, 1);
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    @Nonnull
    @Override
    public List<T> findAll(@Nonnull Specification<T> specification, int maxResultsSize) {
        EntityManager manager = localManager.get();
        JpqlSpecification<T> jpqlSpecification = (JpqlSpecification<T>) specification;
        manager.getTransaction().begin();
        List<T> resultList = jpqlSpecification
                .prepare(manager.createQuery(
                        "SELECT e FROM " + tClass.getSimpleName() + " e WHERE " + jpqlSpecification.toJpqlQuery(),
                        tClass))
                .setMaxResults(maxResultsSize)
                .getResultList();
        manager.getTransaction().commit();
        return resultList;
    }

    @Override
    public void close() {
        EntityManager manager = localManager.get();
        manager.close();
    }
}
