package xyz.chengzi.ooad.repository;

public interface Specification<T> {
    default Specification<T> and(Specification<T> specification) {
        return new AndSpecification<>(this, specification);
    }

    default Specification<T> or(Specification<T> specification) {
        return new OrSpecification<>(this, specification);
    }

    default Specification<T> with(Specification<T> specification) {
        return new WithSpecification<>(this, specification);
    }
}
