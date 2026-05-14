package com.example.schoolmanager.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.schoolmanager.model.Student;

@Repository
public interface StudentRepository
        extends JpaRepository<Student, Integer> {

    // =========================
    // Search By Name
    // =========================
    List<Student> findByNameContainingIgnoreCase(
            String name);

    // =========================
    // Find By Email
    // =========================
    Optional<Student> findByEmail(
            String email);

    // =========================
    // Check Email Exists
    // =========================
    boolean existsByEmail(
            String email);

}

