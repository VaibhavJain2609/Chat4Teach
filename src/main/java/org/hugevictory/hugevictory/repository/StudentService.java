package org.hugevictory.hugevictory.repository;

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
	
	public Student getStudentByUUID(String UUID) {
		List<Student> studentList = this.getAllStudents();
		Student foundStudent = null;
		for(Student s : studentList) {
			if(s.getUUID().equals(UUID)) {
				foundStudent = s;
				break;
			}
		}
		return foundStudent;
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

    public Student getStudentByUsername(String name) {
		return studentRepository.findByName(name);
    }
}