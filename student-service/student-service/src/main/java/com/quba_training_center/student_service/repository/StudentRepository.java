package com.quba_training_center.student_service.repository;

import com.quba_training_center.student_service.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<List<Student>> findByFirstName(String firstName);

    Optional<List<Student>> findByLastName(String firstName);
    // You can also combine them
    Optional<List<Student>> findByFirstNameAndLastName(String firstName, String lastName);

    // Query Student by parent details
    @Query("SELECT s FROM Student s JOIN s.parents p WHERE p.firstName = :firstName AND p.lastName = :lastName AND p.phoneNumber = :phoneNumber AND p.email = :email")
    List<Student> findByParentDetails(String firstName, String lastName, String phoneNumber, String email);

    /*
        Method Naming Convention: Spring Data JPA allows you to create query methods by following the
        naming convention. Here, findByParentsFirstName will query students based on the firstName of their Parent.
        Plural parents: Since parents is a collection in the Student entity, the method naming reflects this
        by using parents.
     */
    List<Student> findByParentsFirstName(String firstName);

    List<Student> findByParentsLastName(String lastName);

    List<Student> findByParentsPhoneNumber(String phoneNumber);

    //@Query("SELECT s FROM Student s JOIN s.parents p WHERE p.email = :email")// since there could be 2 parent emails
    List<Student> findByParentsEmail(String email);
}
