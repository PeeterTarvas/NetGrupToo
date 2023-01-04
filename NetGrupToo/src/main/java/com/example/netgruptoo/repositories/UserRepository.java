package com.example.netgruptoo.repositories;

import com.example.netgruptoo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsernameAndPassword(String username, String password);

    @Query(value = "SELECT MAX(user_id) + 1 AS user_id FROM data.\"user\" ", nativeQuery = true)
    Long findNextId();

    User findUserByUsername(String username);

}
