package com.microservice.courses.service;



import com.microservice.courses.client.StudentClient;
import com.microservice.courses.dto.StudentDTO;
import com.microservice.courses.entities.Course;
import com.microservice.courses.http.response.StudentsByCourseResponse;
import com.microservice.courses.repository.CourseRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class CourseService implements ICourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentClient studentClient;

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }

    @Override
    public StudentsByCourseResponse findStudentsByCourse(Long idCourse) {
        // Consultar el curso
        Course course = courseRepository.findById(idCourse).orElse(new Course());

        // Obtener los estudiantes
        List<StudentDTO> studentDTOList = studentClient.findAllStudentByCourse(idCourse);

        return StudentsByCourseResponse.builder()
                .courseName(course.getName())
                .teacherName(course.getTeacher())
                .studentsDtoList(studentDTOList)
                .build();
    }

}
