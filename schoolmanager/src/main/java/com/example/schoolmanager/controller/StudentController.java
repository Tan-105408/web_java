package com.example.schoolmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*; // Import dấu * cho gọn

import com.example.schoolmanager.service.StudentService;
import com.example.schoolmanager.model.Student;

@RestController
@RequestMapping("/api/students")
@CrossOrigin // cho phép frontend gọi
public class StudentController {

    @Autowired
    private StudentService service;

    // 1. API thêm 1 sinh viên (SỬA: Thêm @RequestBody để nhận JSON)
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return service.addStudent(student);
    }

    // 1.1 API thêm NHIỀU sinh viên cùng lúc (MỚI: Nhận danh sách [])
    @PostMapping("/batch")
    public List<Student> addManyStudents(@RequestBody List<Student> students) {
        for (Student s : students) {
            service.addStudent(s);
        }
        return students;
    }

    // 2. API xóa sinh viên (SỬA: Dùng @DeleteMapping cho chuẩn)
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable int id) {
        service.deleteStudent(id);
        return "Student with ID " + id + " has been deleted.";
    }

    // 3. Tìm kiếm sinh viên theo tên
    @GetMapping("/search")
    public List<Student> searchByName(@RequestParam String name) {
        return service.findByName(name);
    }

    // 4. API lấy sinh viên theo ID
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        return service.getStudentById(id);
    }

    // 5. API lấy danh sách sinh viên
    @GetMapping
    public List<Student> getAllStudents() {
        return service.getAll();
    }
    
    // 6. API cập nhật sinh viên (SỬA: Dùng @PutMapping và @RequestBody)
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable int id, @RequestBody Student studentDetails) {
        Student existingStudent = service.getStudentById(id);
        if (existingStudent != null) {
            // Cập nhật thông tin mới
            existingStudent.setName(studentDetails.getName());
            existingStudent.setEmail(studentDetails.getEmail());
            return service.addStudent(existingStudent); // Lưu lại
        }
        return null;
    }
}