package com.eden.service;

import com.eden.entity.Student;

import java.util.List;

public interface StudentService {
    //查询全部
    List<Student> list();
    //id查询
    Student getById(Long id);
    //增加
    boolean add(Student student);
    //修改
    boolean update(Student student);
    //删除
    boolean delete(Long id);
    //name查询
    List<Student> search(String name);
    //分页
    List<Student> page(int page,int size);
    //数量
    int count();
    //批量删除
    boolean batchDelete(List<Long> ids);
}
