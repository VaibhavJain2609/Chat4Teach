package org.hugevictory.hugevictory.controller;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import org.hugevictory.hugevictory.model.Student;
import org.hugevictory.hugevictory.repository.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.text.html.Option;

@RestController
public class StudentController
{
	@Autowired
	private StudentService studentService;
	
	@RequestMapping(value = "/students", method=RequestMethod.GET)
	public List<Student> getAllStudents()
	{
		return studentService.getAllStudents();
	}

	@RequestMapping(value="/allStudents", method=RequestMethod.GET)
	public List<String> getStudents()
	{
		List<Student> students = studentService.getAllStudents();
		return students.stream().map(student -> student.getName() + (student.isStudentIsOnline()?" isOnline":" isOffline")).collect(Collectors.toList());
	}
	
	@RequestMapping(value="/students/UUID/{UUID}", method = RequestMethod.GET, produces = "application/json")
	public Student getStudentByUUID(@PathVariable String UUID){
		return studentService.getStudentByUUID(UUID);
	}
	
	@RequestMapping(value="/students/{id}", method = RequestMethod.GET, produces = "application/json")
	public Optional<Student> getStudent(@PathVariable int id)
	{
		return studentService.getStudent(id);
	}
	
	@RequestMapping(value="/students/new", method=RequestMethod.POST)
	public ModelAndView addStudent(@ModelAttribute("student") Student student, BindingResult result) {
		student.setRandomUUID();
		studentService.addStudent(student);
		return new ModelAndView("redirect:/teacher");
	}

	@RequestMapping(value="/student/edit/{id}", method = RequestMethod.GET)
	public ModelAndView editStudent(@PathVariable int id, ModelAndView modelAndView)
	{
		Optional<Student> student = studentService.getStudent(id);
		if(student.isPresent()) {
			modelAndView.addObject("student", student);
			modelAndView.setViewName("editStudent");
			return modelAndView;
		}
		return new ModelAndView("redirect:/teacher");
	}

	@RequestMapping(value="/student/edit", method = RequestMethod.POST)
	public ModelAndView editStudent(@ModelAttribute("student") Student student, BindingResult result)
	{
		Optional<Student> studentOptional = studentService.getStudent(student.getId());
		if(studentOptional.isPresent()) {
			Student studentDB = studentOptional.get();
			studentDB.setName(student.getName());
			//studentDB.setIsChatEnabled(student.isChatEnabled());
			studentService.updateStudent(studentDB);
		}
		return new ModelAndView("redirect:/teacher");
	}
    
    @RequestMapping(value="/student/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteStudent(@PathVariable int id)
    {
		studentService.deleteStudent(id);
		return new ModelAndView("redirect:/teacher");
    }
	
}
	
	

	