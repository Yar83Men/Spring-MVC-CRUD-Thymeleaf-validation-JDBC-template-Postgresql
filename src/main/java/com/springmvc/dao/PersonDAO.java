package com.springmvc.dao;

import com.springmvc.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        final String query = "INSERT INTO Person (age, name, email, address) VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(query, person.getAge(), person.getName(), person.getEmail(), person.getAddress());
    }

    public void update(int id, Person person) {
        final String query = "UPDATE Person SET name=?, age=?, email=?, address=? WHERE id=?";
        jdbcTemplate.update(query, person.getName(), person.getAge(), person.getEmail(), person.getAddress(), id);
    }

    public void delete(int id) {
        final String query = "DELETE FROM Person WHERE id=?";
        jdbcTemplate.update(query, id);
    }


    public Optional<Person> showByEmail(String email) {
        final String query = "SELECT * FROM Person WHERE email=?";
        return jdbcTemplate.query(query, new Object[]{email}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    // Тестируем производительность пакетной вставки
    public void testMultiUpdate() {
        List<Person> list = create1000users();

        long before = System.currentTimeMillis();
        for(Person person: list) {
            jdbcTemplate.update("INSERT INTO Person (id, name, age, email) VALUES(?,?,?,?)",
                    person.getId(), person.getName(), person.getAge(), person.getEmail());
        }
        long after = System.currentTimeMillis();

        System.out.println("Time = " + (after - before));
    }


    public void testBatchUpdate(){
        List<Person> people = create1000users();
        long before = System.currentTimeMillis();
        jdbcTemplate.batchUpdate("INSERT INTO Person (id, name, age, email) VALUES(?,?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, people.get(i).getId());
                        ps.setString(2, people.get(i).getName());
                        ps.setInt(3, people.get(i).getAge());
                        ps.setString(4, people.get(i).getEmail());
                    }

                    @Override
                    public int getBatchSize() {
                        return 0;
                    }
                });
        long after = System.currentTimeMillis();
        System.out.println("Time batch update = " + (after - before));
    }

    private static List<Person> create1000users() {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            people.add(new Person(i, "Name " + i, "test" + i + "@gmail.com", 30, "test address"));
        }
        return people;
    }
}
