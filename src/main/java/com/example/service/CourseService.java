package com.example.service;

import com.example.dto.Course;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final List<Course> courses = new ArrayList<>();

    {
        courses.add(new Course(1, "Java", 100.0));
        courses.add(new Course(2, "Python", 200.0));
        courses.add(new Course(3, "JavaScript", 300.0));
    }

    // Create a new course
    public void addCourse(Course course) {
        courses.add(course);
    }

    // Retrieve all courses
    public List<Course> getAllCourses() {
        return courses;
    }

    // Retrieve a course by id
    public Optional<Course> getCourseById(int id) {
        return courses.stream()
                .filter(course -> course.id() == id)
                .findFirst();
    }

    // Update a course
    public boolean updateCourse(int id, Course newCourse) {
        return getCourseById(id).map(existingCourse -> {
            courses.remove(existingCourse);
            courses.add(newCourse);
            return true;
        }).orElse(false);
    }

    // Delete a course by id
    public boolean deleteCourse(int id) {
        return courses
                .removeIf(course -> course.id() == id);
    }
}