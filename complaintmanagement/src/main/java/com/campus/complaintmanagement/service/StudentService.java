package com.campus.complaintmanagement.service;

import com.campus.complaintmanagement.entity.Student;

import java.util.List;

public interface StudentService {
    Student createStudent(Student student);

    Student getStudentById(Long id);

    List<Student> getAllStudents();

    Student updateStudent(Long id, Student updatedStudent);

}
