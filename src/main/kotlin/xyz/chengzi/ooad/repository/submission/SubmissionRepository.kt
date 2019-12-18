package xyz.chengzi.ooad.repository.submission

import xyz.chengzi.ooad.embeddable.SubmissionStatus
import xyz.chengzi.ooad.entity.Problem
import xyz.chengzi.ooad.entity.Submission
import xyz.chengzi.ooad.entity.User
import xyz.chengzi.ooad.repository.EmptyGroups
import xyz.chengzi.ooad.repository.JpaRepository
import xyz.chengzi.ooad.repository.entity.SinceIdSpecification
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory

class SubmissionRepository(entityManager: EntityManager) : JpaRepository<Submission>(entityManager, Submission::class.java) {
}
