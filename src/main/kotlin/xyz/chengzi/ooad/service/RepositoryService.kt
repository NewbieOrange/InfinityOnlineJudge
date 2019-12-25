package xyz.chengzi.ooad.service

import xyz.chengzi.ooad.entity.*
import xyz.chengzi.ooad.repository.JpaRepository
import javax.persistence.EntityManager
import javax.persistence.Persistence

class RepositoryService(persistenceUnitName: String) {
    private val entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName)!!

    inline fun <reified T> createRepository(): JpaRepository<T> {
        return JpaRepository(createEntityManager(), T::class.java)
    }

    fun createContestRepository(): JpaRepository<Contest> {
        return createRepository()
    }

    fun createProblemRepository(): JpaRepository<Problem> {
        return createRepository()
    }

    fun createDiscussionThreadRepository(): JpaRepository<DiscussionThread> {
        return createRepository()
    }

    fun createDiscussionCommentRepository(): JpaRepository<DiscussionComment> {
        return createRepository()
    }

    fun createSubmissionRepository(): JpaRepository<Submission> {
        return createRepository()
    }

    fun createUserRepository(): JpaRepository<User> {
        return createRepository()
    }

    fun createEntityManager(): EntityManager {
        return entityManagerFactory.createEntityManager()
    }

    fun close() {
        entityManagerFactory.close()
    }
}
