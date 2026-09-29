package com.eden.repository;

import com.eden.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByNameContaining(String name);
    long deleteByIdIn(List<Long> ids);
}
