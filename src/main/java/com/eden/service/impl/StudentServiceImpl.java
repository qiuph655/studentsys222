package com.eden.service.impl;

import com.eden.entity.Student;
import com.eden.repository.StudentRepository;
import com.eden.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
@Service
@Transactional
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    //构造器注入
    public StudentServiceImpl(StudentRepository studentRepository){
        this.studentRepository=studentRepository;
    }
    @Override
    public List<Student> list() {
        return studentRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public Student getById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public boolean add(Student student) {
        student.setId(null);
        studentRepository.save(student);
        return true;
    }

    @Override
    public boolean update(Student student) {
        if (student.getId() == null) return false;
        Student existing = studentRepository.findById(student.getId()).orElse(null);
        if (existing == null) return false;
        existing.setName(student.getName());
        existing.setAge(student.getAge());
        existing.setGender(student.getGender());
        existing.setPhone(student.getPhone());
        return true;
    }

    @Override
    public boolean delete(Long id) {
        if (!studentRepository.existsById(id)) return false;
        studentRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Student> search(String name) {
        return studentRepository.findByNameContaining(name);
    }

    @Override
    public List<Student> page(int page, int size) {
        return studentRepository.findAll(PageRequest.of(Math.max(0, page - 1), size,
                Sort.by(Sort.Direction.DESC, "id"))).getContent();
    }

    @Override
    public int count() {
        return Math.toIntExact(studentRepository.count());
    }

    @Override
    public boolean batchDelete(List<Long> ids) {
        return !ids.isEmpty() && studentRepository.deleteByIdIn(ids)>0;
    }
}
