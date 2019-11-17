package xyz.chengzi.ooad.service

import xyz.chengzi.ooad.entity.Contest
import xyz.chengzi.ooad.entity.Problem
import xyz.chengzi.ooad.entity.Submission
import xyz.chengzi.ooad.entity.User
import xyz.chengzi.ooad.repository.JpaRepository
import xyz.chengzi.ooad.repository.UserRepository
import javax.persistence.Persistence

class RepositoryService(persistenceUnitName: String) {
    val entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName)
    val contestRepository = JpaRepository<Contest>(entityManagerFactory, Contest::class.java)
    val problemRepository = JpaRepository<Problem>(entityManagerFactory, Problem::class.java)
    val submissionRepository = JpaRepository<Submission>(entityManagerFactory, Submission::class.java)
    val userRepository = UserRepository(entityManagerFactory)

    fun stop() {
        entityManagerFactory.close()
    }
}