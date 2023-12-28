package com.mathewzvk.studentapi.controller;

import com.mathewzvk.studentapi.dto.StudentRequest;
import com.mathewzvk.studentapi.dto.StudentResponse;
import com.mathewzvk.studentapi.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService service;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody StudentRequest theStudent){
        if(service.findStudentByEmail(theStudent.getEmail()).isPresent()){
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Email already exists.");
        }
        service.addStudent(theStudent);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Student successfully registered.");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StudentResponse>  getAllStudents(){
        return service.allStudents();
    }

    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteStudent(@PathVariable String email){
        service.deleteStudent(email);
        return "Successfully Deleted!!";
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> updateStudent(@RequestBody StudentRequest theStudent, @PathVariable Long id){
        if (service.findById(id).isPresent()) {
            service.updateStudent(id, theStudent);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Student successfully updated.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student not found.");
        }
    }

}
