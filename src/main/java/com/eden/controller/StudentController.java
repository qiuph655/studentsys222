package com.eden.controller;

import com.eden.entity.Student;
import com.eden.service.StudentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final StudentService studentService;
    public StudentController(StudentService studentService){
        this.studentService=studentService;
    }
    @GetMapping("/list")
    public List<Student> list(){
        return studentService.list();
    }
    @GetMapping("/get")
    public Student get(@RequestParam Long id){
        return studentService.getById(id);
    }
    @PostMapping("/add")
    public Map<String,Object>add(@RequestBody Student student){
        Map<String,Object> res=new HashMap<>();
        if(student.getName()==null||student.getName().trim().isEmpty()){
            res.put("success",false);
            res.put("msg","姓名不能为空");
            return res;
        }
        res.put("success",studentService.add(student));
        return res;
    }
    @PostMapping("/update")
    public Map<String,Object>update(@RequestBody Student student){
        Map<String,Object> res=new HashMap<>();
        res.put("success",studentService.update(student));
        return res;
    }
    @PostMapping("/delete")
    public Map<String,Object> delete(@RequestParam Long id){
        Map<String,Object> res=new HashMap<>();
        res.put("success",studentService.delete(id));
        return res;
    }
    @GetMapping("/search")
    public List<Student> search(String name){
        return studentService.search(name);
    }
    @GetMapping("/page")
    public List<Student> page(int page,int size){
        return studentService.page(page,size);
    }
    @GetMapping("/count")
    public int count(){
        return studentService.count();
    }
    @PostMapping("/batchDelete")
    public Map<String,Object> batchDelete(@RequestBody List<Long> ids){
        Map<String,Object> res=new HashMap<>();
        res.put("success",studentService.batchDelete(ids));
        return res;
    }
    @PostMapping("/upload")
    public Map<String,Object> upload(@RequestParam("avatar") MultipartFile file)throws Exception{
        Map<String,Object> res=new HashMap<>();
        String originalName=file.getOriginalFilename();
        int dot=originalName==null ? -1 : originalName.lastIndexOf('.');
        String extension=dot<0 ? "" : originalName.substring(dot+1).toLowerCase(Locale.ROOT);
        if(file.isEmpty() || !Arrays.asList("jpg","jpeg","gif","png","bmp").contains(extension)){
            res.put("success",false);
            res.put("msg","请选择非空的 jpg、jpeg、gif、png 或 bmp 图片");
            return res;
        }
        //获取项目路径
        String path=System.getProperty("user.dir")+"/uploads/";
        //创建目录对象
        File dir=new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //生成文件名，防止重名
        String fileName=UUID.randomUUID()+"."+extension;
        //创建文件
        File dest=new File(path+fileName);
        //保存文件
        file.transferTo(dest);
        res.put("success",true);
        res.put("url","/uploads/"+fileName);
        return res;
    }



}
