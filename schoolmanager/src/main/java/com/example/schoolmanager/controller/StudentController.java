package com.example.schoolmanager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.schoolmanager.model.Student;
import com.example.schoolmanager.service.StudentService;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    // =========================
    // 1. Create Student
    // =========================
    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody Student student) {

        if(student.getName() == null ||
           student.getName().trim().isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .body("Name is required");
        }

        if(student.getEmail() == null ||
           student.getEmail().trim().isEmpty()) {

            return ResponseEntity
                    .badRequest()
                    .body("Email is required");
        }

        Student savedStudent =
                service.addStudent(student);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedStudent);
    }

    // =========================
    // 2. Get All Students
    // =========================
    @GetMapping
    public ResponseEntity<List<Student>> findAll() {

        List<Student> students =
                service.getAll();

        return ResponseEntity.ok(students);
    }

    // =========================
    // 3. Get Student By ID
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(
            @PathVariable int id) {

        Student student =
                service.getStudentById(id);

        if(student == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Student not found");
        }

        return ResponseEntity.ok(student);
    }

    // =========================
    // 4. Update Student
    // =========================
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable int id,
            @RequestBody Student student) {

        Student existingStudent =
                service.getStudentById(id);

        if(existingStudent == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Student not found");
        }

        existingStudent.setName(
                student.getName());

        existingStudent.setEmail(
                student.getEmail());

        Student updatedStudent =
                service.addStudent(existingStudent);

        return ResponseEntity.ok(updatedStudent);
    }

    // =========================
    // 5. Search Student By Name
    // =========================
    @GetMapping("/search")
    public ResponseEntity<List<Student>> search(
            @RequestParam String name) {

        List<Student> students =
                service.findByName(name);

        return ResponseEntity.ok(students);
    }

    // =========================
    // 6. Delete Student
    // =========================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable int id) {

        Student student =
                service.getStudentById(id);

        if(student == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Student not found");
        }

        service.deleteStudent(id);

        return ResponseEntity.ok(
                "Deleted successfully");
    }

}
