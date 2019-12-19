package xyz.chengzi.ooad.service

import xyz.chengzi.ooad.entity.Contest
import xyz.chengzi.ooad.entity.Discussion
import xyz.chengzi.ooad.entity.Problem
import xyz.chengzi.ooad.entity.Submission
import xyz.chengzi.ooad.repository.JpaRepository
import xyz.chengzi.ooad.repository.submission.SubmissionRepository
import xyz.chengzi.ooad.repository.user.UserRepository
import javax.persistence.EntityManager
import javax.persistence.Persistence

class RepositoryService(persistenceUnitName: String) {
    private val entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName)!!

    fun <T> createRepository(clazz: Class<T>): JpaRepository<T> {
        return JpaRepository<T>(entityManagerFactory.createEntityManager(), clazz)
    }

    fun createContestRepository(): JpaRepository<Contest> {
        return createRepository(Contest::class.java)
    }

    fun createProblemRepository(): JpaRepository<Problem> {
        return createRepository(Problem::class.java)
    }

    fun createDiscussionRepository(): JpaRepository<Discussion> {
        return createRepository(Discussion::class.java)
    }

    fun createSubmissionRepository(): SubmissionRepository {
        return SubmissionRepository(entityManagerFactory.createEntityManager())
    }

    fun createUserRepository(): UserRepository {
        return UserRepository(entityManagerFactory.createEntityManager())
    }

    fun close() {
        entityManagerFactory.close()
    }
}
