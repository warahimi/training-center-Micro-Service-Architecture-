package com.quba_training_center.student_service.utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quba_training_center.student_service.entity.Student;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class StudentReader {

    public List<Student> readStudentsFromFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Load the file from the classpath
        InputStream inputStream = new ClassPathResource(filePath).getInputStream();

        // Read JSON file and convert to list of Student objects
        List<Student> students = objectMapper.readValue(inputStream, new TypeReference<List<Student>>() {});
        return students;
    }
}