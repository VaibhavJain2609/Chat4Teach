package org.hugevictory.hugevictory.controller;

import org.hugevictory.hugevictory.model.Student;
import org.hugevictory.hugevictory.repository.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class TeacherController
{
	@Autowired
	private StudentService studentService;
	
	@GetMapping(value = "/teacher")
	public ModelAndView getTeacherPage(ModelAndView modelAndView) {
		Student student = new Student();
		UUID uuid = UUID.randomUUID();
		student.setUUID(uuid.toString());
		List<Student> students = studentService.getAllStudents();
		modelAndView.addObject("students", students);
		modelAndView.addObject("student", student);
		modelAndView.setViewName("teacher");
		return modelAndView;
	}
}
	
	

	