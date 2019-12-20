package xyz.chengzi.ooad.repository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaRepository<T> implements Repository<T> {
    private final EntityManager manager;
    private final Class<T> tClass;

    public JpaRepository(EntityManager entityManager, Class<T> tClass) {
        this.manager = entityManager;
        this.tClass = tClass;
    }

    @Override
    public void addAll(@Nonnull Iterable<T> items) {
        manager.getTransaction().begin();
        items.forEach(manager::persist);
        manager.getTransaction().commit();
    }

    @Override
    public void updateAll(@Nonnull Iterable<T> items) {
        manager.getTransaction().begin();
        items.forEach(manager::merge);
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

    @Nonnull
    @Override
    public List<T> findAll(@Nonnull Specification<T> specification, Orders<T> orders, Groups<T> groups,
                           boolean distinct, int maxResults) {
        manager.getTransaction().begin();

        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(tClass);
        Root<T> root = criteriaQuery.from(tClass);
        criteriaQuery.where(specification.toPredicate(root, criteriaBuilder));
        criteriaQuery.orderBy(orders.toOrders(root, criteriaBuilder));
        criteriaQuery.groupBy(groups.toGroups(root));
        criteriaQuery.distinct(distinct);
        List<T> resultList = manager.createQuery(criteriaQuery).setMaxResults(maxResults).getResultList();

        manager.getTransaction().commit();
        return resultList;
    }

    @Override
    public void close() {
        manager.close();
    }
}
