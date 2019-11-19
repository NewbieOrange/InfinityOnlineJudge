package xyz.chengzi.ooad.repository.user

import xyz.chengzi.ooad.entity.User
import xyz.chengzi.ooad.repository.JpaRepository
import javax.persistence.EntityManagerFactory

class UserRepository(entityManagerFactory: EntityManagerFactory) : JpaRepository<User>(entityManagerFactory, User::class.java) {
    fun findByUsername(username: String): User {
        return find(UsernameSpecification(username))
    }
}
