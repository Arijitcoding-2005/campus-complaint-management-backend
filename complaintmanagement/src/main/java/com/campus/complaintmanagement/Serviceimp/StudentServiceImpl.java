package com.campus.complaintmanagement.Serviceimp;

import com.campus.complaintmanagement.entity.Student;
import com.campus.complaintmanagement.repository.StudentRepository;
import com.campus.complaintmanagement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

//    // ✅ MANUAL CONSTRUCTOR
//    public StudentServiceImpl(StudentRepository studentRepository) {
//        this.studentRepository = studentRepository;
//    }
//@Override
//public Student registerStudent(Student student) {
//    student.setPassword(
//            passwordEncoder.encode(student.getPassword())
//    );
//    return studentRepository.save(student);
//}


    @Override
    public Student createStudent(Student student) {
        student.setPassword(
            passwordEncoder.encode(student.getPassword())
   );
   return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }


    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student updateStudent(Long id, Student updatedStudent) {

        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        existingStudent.setName(updatedStudent.getName());
        existingStudent.setEmail(updatedStudent.getEmail());
        existingStudent.setDepartment(updatedStudent.getDepartment());
        existingStudent.setRegno(updatedStudent.getRegno());

        return studentRepository.save(existingStudent);
    }

}

