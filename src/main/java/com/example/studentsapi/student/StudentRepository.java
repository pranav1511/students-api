package com.example.studentsapi.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s WHERE active IN ?1 AND admissionYear <= ?2 AND admissionYear >= ?3")
    Page<Student> findStudents(List<Boolean> activeList, Year admissionYearBefore, Year admissionYearAfter, Pageable pageable);

    @Query("SELECT s FROM Student s WHERE studentClass IN ?1 AND active IN ?2 AND admissionYear <= ?3 AND admissionYear >= ?4")
    Page<Student> findStudentsInClass(List<Integer> classes, List<Boolean> activeList, Year admissionYearBefore, Year admissionYearAfter, Pageable pageable);
}
