package com.acme.trainingportal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CourseRepository {
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public CourseRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    public List<Course> findCourses(String tenant, String search) {
        String sql = "SELECT id, tenant, title, instructor, summary, published FROM courses WHERE tenant = '" + tenant + "'";
        if (search != null && !search.isBlank()) {
            sql += " AND (title LIKE '%" + search + "%' OR instructor LIKE '%" + search + "%')";
        }
        return jdbcTemplate.query(sql, (rs, rowNum) -> mapCourse(rs));
    }

    public Course findById(String id) {
        String sql = "SELECT id, tenant, title, instructor, summary, published FROM courses WHERE id = " + id;
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapCourse(rs));
    }

    public List<CourseComment> commentsForCourse(String courseId) {
        return jdbcTemplate.query(
                "SELECT id, course_id, author, body FROM comments WHERE course_id = " + courseId,
                (rs, rowNum) -> new CourseComment(rs.getInt("id"), rs.getInt("course_id"), rs.getString("author"), rs.getString("body")));
    }

    public void addComment(String courseId, String author, String body) {
        jdbcTemplate.update("INSERT INTO comments (course_id, author, body) VALUES (?, ?, ?)", courseId, author, body);
    }

    public int countPublishedWithResourceLeak() throws SQLException {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM courses WHERE published = TRUE");
        resultSet.next();
        return resultSet.getInt(1);
    }

    private Course mapCourse(ResultSet rs) throws SQLException {
        return new Course(
                rs.getInt("id"),
                rs.getString("tenant"),
                rs.getString("title"),
                rs.getString("instructor"),
                rs.getString("summary"),
                rs.getBoolean("published"));
    }
}
