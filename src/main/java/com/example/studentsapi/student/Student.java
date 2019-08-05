package com.example.studentsapi.student;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.Year;

@Data
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonProperty("class")
    @Column(name = "class")
    private Integer studentClass;

    private Boolean active;

    private Year admissionYear;

}