package com.quba_training_center.student_service.controller;

import com.quba_training_center.student_service.entity.Parent;
import com.quba_training_center.student_service.entity.Student;
import com.quba_training_center.student_service.service.StudentService;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents()
    {
//        Student student1 = new Student(
//                null,
//                "Abdullah",
//                "Rahimi",
//                null,
//                new ArrayList<>(Arrays.asList(new Parent("Wahidullah","Rahimi","Male","5095921947","1455 NE Brandi Way","wahidrahimi45@gmali.com"),
//                        new Parent("Farima","Rahimi","Female","34534545","1455 NE Brandi Way","farimarahimi44@gmali.com"))),
//                "04/09/2018",
//                "Male",
//                "ClassA"
//        );
//        if(student1.getFullName() == null)
//        {
//            student1.setFullName(student1.getFirstName() +" " + student1.getLastName());
//        }
//
//        Student student2 = new Student(
//                null,
//                "Mobashir",
//                "Bani",
//                null,
//                new ArrayList<>(Arrays.asList(new Parent("Sayed Sharif","Bani","Male","5095921947","Federal Way","sayed@gmali.com"),
//                        new Parent("Gul","Bani","Female","34534545","Federal Way","farimarahimi44@gmali.com"))),
//                "02/12/2018",
//                "Male",
//                "ClassB"
//        );
//        if(student2.getFullName() == null)
//        {
//            student2.setFullName(student2.getFirstName() +" " + student2.getLastName());
//        }
//
//        if(studentService.getStudents().size() == 0)
//        {
//            studentService.addStudent(student1);
//            studentService.addStudent(student2);
//        }
        return studentService.getStudents();
    }
    @PostMapping
    public Student addStudent(@RequestBody Student student)
    {
        return studentService.addStudent(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id)
    {
        Optional<Student> student = studentService.findStudentById(id);
        if(student.isPresent())
        {
            return new ResponseEntity<>(student.get(), HttpStatus.FOUND);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to find students by first name
    @GetMapping("/firstname/{firstName}")
    public ResponseEntity<List<Student>> getStudentsByFirstName(@PathVariable String firstName) {
        List<Student> students = studentService.findByFirstName(firstName);
        if (students.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no students found
        }
        return ResponseEntity.ok(students); // Return 200 with the list of students
    }

    @GetMapping("/lastname/{lastName}")
    public ResponseEntity<List<Student>> getStudentsByLastName(@PathVariable String lastName)
    {
        List<Student> students = studentService.findByLastName(lastName);
        if(students.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<>(students,HttpStatus.FOUND);
        }
    }

    // Endpoint to find students by both first and last name
    /*
        Sample URL:
        http://localhost:8083/api/student/fullname?firstName=Wahdullah&lastName=Rahimi
     */
    @GetMapping("/fullname")
    public ResponseEntity<List<Student>> getStudentsByFirstNameAndLastName(
            @RequestParam String firstName, @RequestParam String lastName) {
        System.out.println("FullName");
        List<Student> students = studentService.findByFirstNameAndLastName(firstName, lastName);
        if (students.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no students found
        }
        return ResponseEntity.ok(students); // Return 200 with the list of students
    }

    // Get Student by parent object / parents information
    /*
        GET: /api/student/by-parent-details?firstName=John&lastName=Doe&phoneNumber=1234567890&email=johndoe@example.com
     */
    @GetMapping("/by-parent-details")
    public ResponseEntity<List<Student>> getStudentsByParentDetails(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String phoneNumber,
            @RequestParam String email) {

        List<Student> students = studentService.findByParentDetails(firstName, lastName, phoneNumber, email);
        if (students.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no students are found
        }
        return ResponseEntity.ok(students); // Return 200 with the list of students
    }

    //http://localhost:8083/api/student/by-parent-details/firstname/Abdul Aziz
    @GetMapping("/by-parent-details/firstname/{firstName}")
    public ResponseEntity<List<Student>> getStudentsByParentFirstName(@PathVariable String firstName) {
        List<Student> students = studentService.findByParentFirstName(firstName);
        if (students.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no students found
        }
        return ResponseEntity.ok(students); // Return 200 with the list of students
    }

    //http://localhost:8083/api/student/by-parent-details/lastname/Rahimi
    @GetMapping("/by-parent-details/lastname/{lastName}")
    public ResponseEntity<List<Student>> getStudentsByParentLastName(@PathVariable String lastName) {
        List<Student> students = studentService.findByParentLastName(lastName);
        if (students.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no students found
        }
        return ResponseEntity.ok(students); // Return 200 with the list of students
    }

    // http://localhost:8083/api/student/by-parent-details/phonenumber/5095921947
    @GetMapping("/by-parent-details/phonenumber/{phoneNumber}")
    public ResponseEntity<List<Student>> getStudentsByParentPhoneNumber(@PathVariable String phoneNumber) {
        List<Student> students = studentService.findByParentPhoneNumber(phoneNumber);
        if (students.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no students found
        }
        return ResponseEntity.ok(students); // Return 200 with the list of students
    }

    //http://localhost:8083/api/student/by-parent-details/email/aziz@gmali.com
    @GetMapping("/by-parent-details/email/{email}")
    public ResponseEntity<List<Student>> getStudentsByParentEmail(@PathVariable String email) {
        List<Student> students = studentService.findByParentEmail(email);
        if (students.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 if no students found
        }
        return ResponseEntity.ok(students); // Return 200 with the list of students
    }

    // End point to add list of students to the database
    @PostMapping("/add-all")
    public ResponseEntity<List<Student>> addAllStudents(@RequestBody List<Student> students)
    {
        List<Student> savedStudents = studentService.saveAllStudents(students);
        return new ResponseEntity<>(savedStudents, HttpStatus.CREATED);
    }

}
