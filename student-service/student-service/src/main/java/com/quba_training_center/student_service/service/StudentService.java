package com.quba_training_center.student_service.service;

import com.quba_training_center.student_service.entity.Student;
import com.quba_training_center.student_service.repository.StudentRepository;
import com.quba_training_center.student_service.utilities.StudentReader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents()
    {
        /*
            if the databse is empty read the student list from the file and insert it to the databse
         */
        if(studentRepository.findAll().size()==0)
        {
            StudentReader studentReader = new StudentReader();
            try {
                List<Student> students = studentReader.readStudentsFromFile("students-list.txt");
                //students.forEach(System.out::println);
                studentRepository.saveAll(students);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return studentRepository.findAll();
    }

    public Student addStudent(Student student) {
        if(student.getFullName() == null)
        {
            student.setFullName(student.getFirstName() + " " + student.getLastName());
        }
        studentRepository.save(student);
        return student;
    }

    public Optional<Student> findStudentById(int id)
    {
        return studentRepository.findById(id);
    }
    // Find students by first name
    public List<Student> findByFirstName(String firstName) {
        Optional<List<Student>> students = studentRepository.findByFirstName(firstName);
        return students.orElseGet(List::of); // Return an empty list if no students are found
    }

    public List<Student> findByLastName(String lastName)
    {
        Optional<List<Student>> students = studentRepository.findByLastName(lastName);
        return students.orElseGet(List::of);
    }

    // Find students by both first name and last name
    public List<Student> findByFirstNameAndLastName(String firstName, String lastName) {
        Optional<List<Student>> students = studentRepository.findByFirstNameAndLastName(firstName, lastName);
        return students.orElseGet(List::of); // Return an empty list if no students are found
    }


    // Find Student by Parent information
    public List<Student> findByParentDetails(String firstName, String lastName, String phoneNumber, String email) {
        return studentRepository.findByParentDetails(firstName, lastName, phoneNumber, email);
    }

    public List<Student> findByParentFirstName(String firstName) {
        return studentRepository.findByParentsFirstName(firstName);
    }

    public List<Student> findByParentLastName(String lastName) {
        return studentRepository.findByParentsLastName(lastName);
    }

    public List<Student> findByParentPhoneNumber(String phoneNumber) {
        return studentRepository.findByParentsPhoneNumber(phoneNumber);
    }

    public List<Student> findByParentEmail(String email) {
        return studentRepository.findByParentsEmail(email);
    }

    // API to save list of students to the database
    public List<Student> saveAllStudents(List<Student> students)
    {
        for(Student student : students)
        {
            if(student.getFullName() == null)
            {
                student.setFullName(student.getFirstName() +" "+ student.getLastName());
            }
        }
        return studentRepository.saveAll(students);
    }
}
