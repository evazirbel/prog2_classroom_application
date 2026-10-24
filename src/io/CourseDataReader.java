package io;

import logic.Course;
import java.util.*;

public interface CourseDataReader {
    Optional<Course> readCourseData();
}

