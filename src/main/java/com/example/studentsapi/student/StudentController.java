package com.example.studentsapi.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@RequestMapping("/students")
@RestController
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping
    public Page<Student> getAllStudents(
            @RequestParam Optional<List<Integer>> classes,
            @RequestParam Optional<List<Boolean>> active,
            @RequestParam Optional<Year> admissionYearAfter,
            @RequestParam Optional<Year> admissionYearBefore,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(defaultValue = "1") Integer pageNumber) {
        return studentService.getAllStudents(classes, active, admissionYearBefore, admissionYearAfter, pageSize, pageNumber);
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        try {
            return studentService.getStudent(id);
        }
        catch (StudentNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Student Not Found", exc);
        }

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
    }

    @PatchMapping("/{id}")
    public void updateStudent(@PathVariable Long id, @RequestBody Student student) {
        try {
            studentService.updateStudent(id, student);
        }
        catch (StudentNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Student Not Found", exc);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
        }
        catch (StudentNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Student Not Found", exc);
        }
    }
}