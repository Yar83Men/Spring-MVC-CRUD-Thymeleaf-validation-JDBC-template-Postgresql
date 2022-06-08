package com.springmvc.dao;

import com.springmvc.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index() {
        final String query = "SELECT * FROM Person";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        final String query = "SELECT * FROM Person WHERE id=?";
        return jdbcTemplate.query(query, new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void save(Person person) {
        final String query = "INSERT INTO Person (id, age, name, email) VALUES(1, ?, ?, ?)";
        jdbcTemplate.update(query, person.getAge(), person.getName(), person.getEmail());
    }

    public void update(int id, Person person) {
        final String query = "UPDATE Person SET name=?, age=?, email=? WHERE id=?";
        jdbcTemplate.update(query, person.getName(), person.getAge(), person.getEmail(), id);
    }

    public void delete(int id) {
        final String query = "DELETE FROM Person WHERE id=?";
        jdbcTemplate.update(query, id);
    }
}
