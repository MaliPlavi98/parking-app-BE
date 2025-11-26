package com.app.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.app.parking.entity.User;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository <User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findAllByRole(Role role);

}
