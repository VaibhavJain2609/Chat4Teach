package org.hugevictory.hugevictory.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.hugevictory.hugevictory.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService 
{
	@Autowired
	public StudentRepository studentRepository;
	
	public List<Student> getAllStudents()
	{
		return studentRepository.findAll();
	}
	
	public void addStudent(Student student)
	{
		studentRepository.save(student);
	}
	
	public Optional<Student> getStudent(int id)
	{
		return studentRepository.findById(id);
	}
	
	public void updateStudent(Student student)
	{
			studentRepository.save(student);
	}
	
	public String deleteStudent(int id)
	{
		if(studentRepository.existsById(id))
		{
			studentRepository.deleteById(id);
			return "student deleted";
		}
		else
		{
			return "student not found";
		}
	}
}