package com.example.studentsapi.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public Page<Student> getAllStudents(Optional<List<Integer>> classes,
                                     Optional<List<Boolean>> active,
                                     Optional<Year> admissionYearBefore,
                                     Optional<Year> admissionYearAfter,
                                     int pageSize, int pageNumber) {

        List<Integer> classesList = classes.orElse(null);
        Page<Student> students;
        if(classesList == null) {
            students = studentRepository.findStudents(
                    active.orElse(Arrays.asList(new Boolean(true), new Boolean(false))),
                    admissionYearBefore.orElse(Year.of(Year.MIN_VALUE)),
                    admissionYearAfter.orElse(Year.of(Year.MAX_VALUE)),
                    PageRequest.of(pageNumber - 1, pageSize));
        }
        else {
            students = studentRepository.findStudentsInClass(
                    classesList,
                    active.orElse(Arrays.asList(new Boolean(true), new Boolean(false))),
                    admissionYearBefore.orElse(Year.of(Year.MIN_VALUE)),
                    admissionYearAfter.orElse(Year.of(Year.MAX_VALUE)),
                    PageRequest.of(pageNumber - 1, pageSize));
        }
        return students;
    }

    public Student getStudent(Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        if(student != null) {
            return student;
        }
        else {
            throw new StudentNotFoundException();
        }
    }

    public void addStudent(Student student) {
        student.setActive(true);
        studentRepository.save(student);
    }

    public void updateStudent(Long id, Student newStudent) {
        Student student = studentRepository.findById(id).orElse(null);
        if(student != null) {
            student.setStudentClass(newStudent.getStudentClass());
            studentRepository.save(student);
        }
        else {
            throw new StudentNotFoundException();
        }
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        if(student != null) {
            student.setActive(false);
            studentRepository.save(student);
        }
        else {
            throw new StudentNotFoundException();
        }
    }
}
