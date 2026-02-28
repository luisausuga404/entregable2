package com.cesde.pi.repository;

import com.cesde.pi.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Custom query methods
    Optional<Student> findByEmail(String email);

    boolean existsByEmail(String email);
}
