package com.cesde.pi.service;

import com.cesde.pi.model.Student;
import com.cesde.pi.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Create or Update
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    // Read - Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Read - Get student by ID
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    // Read - Get student by email
    public Optional<Student> getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    // Update
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        student.setFirstName(studentDetails.getFirstName());
        student.setLastName(studentDetails.getLastName());
        student.setEmail(studentDetails.getEmail());
        student.setBirthDate(studentDetails.getBirthDate());
        student.setPhone(studentDetails.getPhone());

        return studentRepository.save(student);
    }

    // Delete
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        studentRepository.delete(student);
    }

    // Check if email exists
    public boolean emailExists(String email) {
        return studentRepository.existsByEmail(email);
    }
}
