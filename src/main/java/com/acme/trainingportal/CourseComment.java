package com.acme.trainingportal;

public record CourseComment(int id, int courseId, String author, String body) {
}
