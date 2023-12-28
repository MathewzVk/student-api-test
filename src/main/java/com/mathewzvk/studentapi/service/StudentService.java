package com.mathewzvk.studentapi.service;

import com.mathewzvk.studentapi.dto.StudentRequest;
import com.mathewzvk.studentapi.dto.StudentResponse;
import com.mathewzvk.studentapi.model.Student;
import com.mathewzvk.studentapi.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repository;

    public void addStudent(StudentRequest studentRequest){
        Student student = Student.builder()
                .name(studentRequest.getName())
                .email(studentRequest.getEmail())
                .password(studentRequest.getPassword())
                .build();

        repository.save(student);
    }

    public Optional<StudentResponse> findStudentByEmail(String email){
        return repository.findByEmail(email).map(
                student -> new StudentResponse
                        (student.getId(),student.getName(),student.getEmail(),student.getPassword()));
    }

    public List<StudentResponse> allStudents(){
        return repository.findAll().stream().map(this::mapToStudentResponse).toList();
    }

    public Optional<StudentResponse> findById(Long id){
        return repository.findById(id).map(
                student -> new StudentResponse
                        (student.getId(),student.getName(),student.getEmail(),student.getPassword()));

    }

    private StudentResponse mapToStudentResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .password(student.getPassword())
                .build();
    }

    public void deleteStudent(String email){
        Optional<Student> student = repository.findByEmail(email);
        student.ifPresent(repository::delete);
    }

    public void updateStudent(Long studentId, StudentRequest studentRequest) {
        Optional<Student> existingStudentOptional = repository.findById(studentId);

        if (existingStudentOptional.isPresent()) {
            Student existingStudent = existingStudentOptional.get();
            existingStudent.setName(studentRequest.getName());
            existingStudent.setEmail(studentRequest.getEmail());
            existingStudent.setPassword(studentRequest.getPassword());

            repository.save(existingStudent);
        } else {
            throw new NoSuchElementException("Student not found with ID: " + studentId);
        }
    }

}
