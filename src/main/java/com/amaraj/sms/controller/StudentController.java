package com.amaraj.sms.controller;

import com.amaraj.sms.entity.Student;
import com.amaraj.sms.repository.StudentRepository;
import com.amaraj.sms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students";
    }


    @GetMapping("/students/new")
    public String createStudentForm(Model model) {
        //create st object to hold student form data
        Student student = new Student();
        model.addAttribute("student", student);
        return "create_student";
    }

    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        return "edit_student";
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student,
                                Model model){
        //get st from db by id
        Student existStudent = studentService.getStudentById(id);
        existStudent.setFirstName(student.getFirstName());
        existStudent.setLastName(student.getLastName());
        existStudent.setEmail(student.getEmail());
        //save updated st
        studentService.updateStudent(existStudent);
        return "redirect:/students";
    }


    //handler method to handle delete st request
    @GetMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id){
            studentService.deleteStudent(id);
            return "redirect:/students";
    }

}
