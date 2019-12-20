package xyz.chengzi.ooad.service

import xyz.chengzi.ooad.entity.*
import xyz.chengzi.ooad.repository.JpaRepository
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence

class RepositoryService(persistenceUnitName: String) {
    private val entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName)!!

    inline fun <reified T> createRepository(): JpaRepository<T> {
        return JpaRepository(`access$entityManagerFactory`.createEntityManager(), T::class.java)
    }

    fun createContestRepository(): JpaRepository<Contest> {
        return createRepository()
    }

    fun createProblemRepository(): JpaRepository<Problem> {
        return createRepository()
    }

    fun createDiscussionRepository(): JpaRepository<Discussion> {
        return createRepository()
    }

    fun createSubmissionRepository(): JpaRepository<Submission> {
        return createRepository()
    }

    fun createUserRepository(): JpaRepository<User> {
        return createRepository()
    }

    fun close() {
        entityManagerFactory.close()
    }

    @PublishedApi
    internal val `access$entityManagerFactory`: EntityManagerFactory
        get() = entityManagerFactory
}
