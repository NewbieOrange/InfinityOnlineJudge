package xyz.chengzi.ooad.repository;

import xyz.chengzi.ooad.exception.EntityAlreadyExistsException;
import xyz.chengzi.ooad.exception.EntityNotFoundException;

import javax.annotation.Nonnull;
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

    @Nonnull
    @Override
    public T findById(int id) throws EntityNotFoundException {
        EntityManager manager = localManager.get();
        T item = manager.find(tClass, id);
        if (item == null) {
            throw new EntityNotFoundException(id);
        }
        return item;
    }

    @Nonnull
    @Override
    public T find(@Nonnull Specification<T> specification) throws EntityNotFoundException {
        EntityManager manager = localManager.get();
        JpqlSpecification<T> jpqlSpecification = (JpqlSpecification<T>) specification;
        manager.getTransaction().begin();
        List<T> resultList = jpqlSpecification
                .prepare(manager.createQuery(
                        "SELECT e FROM " + tClass.getSimpleName() + " e WHERE " + jpqlSpecification.toJpqlQuery(),
                        tClass))
                .setMaxResults(1).getResultList();
        manager.getTransaction().commit();
        if (resultList.isEmpty()) {
            throw new EntityNotFoundException(specification);
        }
        return resultList.iterator().next();
    }

    @Nonnull
    @Override
    public List<T> findAll(@Nonnull Specification<T> specification) {
        EntityManager manager = localManager.get();
        JpqlSpecification<T> jpqlSpecification = (JpqlSpecification<T>) specification;
        manager.getTransaction().begin();
        List<T> resultList = jpqlSpecification
                .prepare(manager.createQuery(
                        "SELECT e FROM " + tClass.getSimpleName() + " e WHERE " + jpqlSpecification.toJpqlQuery(),
                        tClass))
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
