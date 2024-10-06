package com.finfit.finfit.controller;

import com.finfit.finfit.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    public List<Student> students= new ArrayList<>(List.of(
            new Student("Saksham",21,50),
            new Student("Stu",23,51)
    ));

    @GetMapping("/students")
    public List<Student> getStudents(){
        return students;
    }

    @PostMapping("/students")
    public Student addStudents(@RequestBody Student student){
        students.add(student);
        return student;
    }
}
