package com.example.netgruptoo.repositories;

import com.example.netgruptoo.dbos.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsernameAndPassword(String username, String password);

    @Query(value = "SELECT MAX(user_id) + 1 AS user_id FROM data.\"user\" ", nativeQuery = true)
    Long findNextId();

    User findUserByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM data.\"user\" WHERE username = :username", nativeQuery = true)
    void deleteByUsername(String username);

}
