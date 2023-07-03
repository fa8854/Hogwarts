package ru.hogwarts.school.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    private final Map<Long, Faculty> facultis = new HashMap<>();

    private long idGenerator = 1;

    public Faculty create(Faculty faculty) {
        faculty.setId(idGenerator++);
        facultis.put(idGenerator, faculty);
        return faculty;
    }

    public Faculty update(long id, Faculty faculty) {
        if (facultis.containsKey(id)){
            Faculty oldFaculty = facultis.get(id);
            oldFaculty.setName(faculty.getName());
            oldFaculty.setColor(faculty.getColor());
            facultis.replace(id, oldFaculty);
            return oldFaculty;
        }else{

            throw new FacultyNotFoundException(id);
        }
    }

    public Faculty delete (long id){
        if (facultis.containsKey(id)){
            Faculty oldFaculty = facultis.get(id);
            return facultis.remove(id);
        }else{
        throw new FacultyNotFoundException(id);
        }
    }

    public Faculty get (long id){
        if (facultis.containsKey(id)){
            Faculty oldFaculty = facultis.get(id);
            return facultis.get(id);
        }else{
            throw new FacultyNotFoundException(id);
        }
    }


    public List<Faculty> findAll(@Nullable String color) {
        return Optional.ofNullable(color)
                .map(c->
                        facultis.values().stream()
                                .filter(faculty -> faculty.getColor().equals(c))
                )
                .orElseGet(()-> facultis.values().stream())
                .collect(Collectors.toList());

    }
}
