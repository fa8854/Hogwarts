package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.exception.StudentNotFoundException;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();

    private long idGenerator = 1;

    public Student create(Student student) {
        student.setId(idGenerator++);
        students.put(idGenerator, student);
        return student;
    }

    public Student update(long id, Student student) {
        if (students.containsKey(id)) {
            Student oldStudent = students.get(id);
            oldStudent.setName(student.getName());
            oldStudent.setAge(student.getAge());
            students.replace(id, oldStudent);
            return oldStudent;
        } else {

            throw new StudentNotFoundException(id);
        }
    }

    public Student delete(long id) {
        if (students.containsKey(id)) {
            Student oldStudent = students.get(id);
            return students.remove(id);
        } else {
            throw new StudentNotFoundException(id);
        }
    }

    public Student get(long id) {
        if (students.containsKey(id)) {
            Student oldStudent = students.get(id);
            return students.get(id);
        } else {
            throw new StudentNotFoundException(id);
        }
    }

    public List<Student> findAll(Integer age) {
        return Optional.ofNullable(age)
                .map(a->
                        students.values().stream()
                                .filter(faculty -> faculty.getAge() == a)
                )
                .orElseGet(()-> students.values().stream())
                .collect(Collectors.toList());
    }

}
