package com.microservice.courses.client;

import com.microservice.courses.dto.StudentDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service // se pone el puerto del gateway
public class StudentClient {
    RestTemplate restTemplate = new RestTemplate();
    @GetMapping("/search-by-course/{idCourse}")
    public List<StudentDTO> findAllStudentByCourse(@PathVariable Long idCourse){
        return restTemplate.getForObject("http://localhost:8080/api/student/search-by-course/" + idCourse, List.class);
    }
}
