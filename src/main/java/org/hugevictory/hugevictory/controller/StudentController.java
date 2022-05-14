package org.hugevictory.hugevictory.controller;

import java.util.List;

import java.util.Optional;
import org.hugevictory.hugevictory.model.Student;
import org.hugevictory.hugevictory.repository.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class StudentController
{
	@Autowired
	private StudentService studentService;
	
	@RequestMapping(value = "/students")
	public List<Student> getAllStudents()
	{
		return studentService.getAllStudents();
	}
	
	@RequestMapping(value="/students/{id}")
	public Optional<Student> getStudent(@PathVariable int id)
	{
		return studentService.getStudent(id);
	}
	
	@RequestMapping(value="/students/new", method=RequestMethod.POST)
	public String addStudent(@ModelAttribute("student") Student student, BindingResult result) {
		studentService.addStudent(student);
		return "redirect:/teacher";
	}
	
	@RequestMapping(value="/students/{id}", method = RequestMethod.PUT)
    public String updateStudent(@RequestBody Student student, @PathVariable int id)
    {
    	return studentService.updateStudent(id, student);
    }
    
    @RequestMapping(value="/students/delete/{id}", method = RequestMethod.GET)
    public String deleteStudent(@PathVariable int id, BindingResult result, Model model)
    {
		studentService.deleteStudent(id);
		return "redirect:/teacher";
    }
	
}
	
	

	