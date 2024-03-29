package org.hugevictory.hugevictory.repository;
import org.hugevictory.hugevictory.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByName(String name);
}
