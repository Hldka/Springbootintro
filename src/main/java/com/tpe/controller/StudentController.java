package com.tpe.controller;

import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students") // http://localhost:8080/students
public class StudentController {

    @Autowired// her katman bir sonraki katmanla iletisme gecer
    private StudentService studentService;


    // !!! Bütün öğrenciler gelsin
    @GetMapping // http://localhost:8080/students + GET
    public ResponseEntity<List<Student>> getAll(){
        List<Student> students = studentService.getAll();

        return ResponseEntity.ok(students); // 200 kodunu HTTP Status kodu olarak gönderir
    }

    // !!! Student objesi oluşturalım
    @PostMapping  // http://localhost:8080/students + POST + JSON
    public ResponseEntity<Map<String,String>> createStudent(@Valid  @RequestBody Student student) {
        // @Valid : parametreler valid mi kontrol eder, bu örenekte Student
            //objesi oluşturmak için  gönderilen fieldlar yani
            //name gibi özellikler düzgün set edilmiş mi ona bakar.
        // @RequestBody = gelen parametreyi, requestin bodysindeki bilgisi ,
            //Student objesine map edilmesini sağlıyor.
        studentService.createStudent(student);

        Map<String,String> map = new HashMap<>();
        map.put("message","Student is created successfuly");// kullanicaya bilgi
        map.put("status" ,"true");// frontend dev' e bilgi icin

        return new ResponseEntity<>(map, HttpStatus.CREATED);  // 201 !!!  kodlari farkli
    }

    // Id ile öğrenci getirelim @RequestParam ile
    @GetMapping("/query") // http://localhost:8080/students/query?id=1
    public ResponseEntity<Student> getStudent(@RequestParam("id") Long id){
        Student student =studentService.findStudent(id);
        return ResponseEntity.ok(student);
    }
    // !!! Id ile öğrenci getirelim @PathVariable ile
    @GetMapping("{id}") // http://localhost:8080/students/1
    public ResponseEntity<Student> getStudentWithPath(@PathVariable("id") Long id){
        Student student =studentService.findStudent(id);
        return ResponseEntity.ok(student);

    }
    // !!! Delete
    @DeleteMapping("/{id}") // http://localhost:8080/students/1  + DELETE
    public ResponseEntity<Map<String,String>> deleteStudent(@PathVariable("id") Long id) {

        studentService.deleteStudent(id);

        Map<String,String> map = new HashMap<>();
        map.put("message","Student is deleted successfuly");
        map.put("status" ,"true");

        return new ResponseEntity<>(map, HttpStatus.OK); // return ResponseEntity.ok(map);
    }
    // !!! Update
    @PutMapping("{id}") // http://localhost:8080/students/1  + PUT
    public ResponseEntity<Map<String,String>> updateStudent(
            @PathVariable("id") Long id, @Valid
    @RequestBody StudentDTO studentDTO) {
        studentService.updateStudent(id,studentDTO);

        Map<String,String> map = new HashMap<>();
        map.put("message","Student is updated successfuly");
        map.put("status" ,"true");

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    // !!! Pageable
    @GetMapping("/page")
    public ResponseEntity<Page<Student>> getAllWithPage(
            @RequestParam("page") int page, // hangi page gönderilecek .. 0 dan başlıyor
            @RequestParam("size") int size, // page başı kaç student olacak
            @RequestParam("sort") String prop, // sıralama hangi fielda göre yapılacak
            @RequestParam("direction") Sort.Direction direction) { // doğal sıralı mı olsun ?

        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));
        Page<Student> studentPage = studentService.getAllWithPage(pageable);
        return ResponseEntity.ok(studentPage);

    }


}
