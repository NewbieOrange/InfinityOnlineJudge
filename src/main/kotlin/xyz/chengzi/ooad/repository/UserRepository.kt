package xyz.chengzi.ooad.repository

import xyz.chengzi.ooad.entity.User
import xyz.chengzi.ooad.repository.user.UsernameSpecification
import javax.persistence.EntityManagerFactory
import javax.persistence.EntityNotFoundException

class UserRepository(entityManagerFactory: EntityManagerFactory) : JpaRepository<User>(entityManagerFactory, User::class.java) {
    fun findByUsername(username: String): User {
        return find(UsernameSpecification(username))
    }
}
