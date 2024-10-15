package com.danielpm1982.springboot3_profile_mng_no_comments.repository;

import com.danielpm1982.springboot3_profile_mng_no_comments.exception.PersonSaveOrUpdateByIdFailedException;
import com.danielpm1982.springboot3_profile_mng_no_comments.entity.Person;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class PersonCustomRepositoryImpl implements PersonCustomRepository {

    private final JdbcTemplate jdbcTemplate;
    public PersonCustomRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Person saveOrUpdate(Person person) {
        if(findById(person.getId())==null){
            String preparedStatement = "INSERT INTO PERSON(ID, FIRST_NAME, LAST_NAME, USER_NAME, PASSWORD, EMAIL) " +
                    "VALUES(?,?,?,?,?,?)";
            jdbcTemplate.update(preparedStatement, person.getId(), person.getFirstName(), person.getLastName(),
                    person.getUserName(), person.getPassword(), person.getEmail());
            if(findById(person.getId())==null){
                throw new PersonSaveOrUpdateByIdFailedException("Person ID = "+person.getId()+" did not previously exist at the Database but was not inserted !");
            }
        } else{
            String preparedStatement = "UPDATE PERSON SET FIRST_NAME=?, LAST_NAME=?, USER_NAME=?, PASSWORD=?, EMAIL=? WHERE ID=?";
            jdbcTemplate.update(preparedStatement, person.getFirstName(), person.getLastName(), person.getUserName(),
                    person.getPassword(), person.getEmail(), person.getId());
            if(!Objects.equals(findById(person.getId()),person)){
                throw new PersonSaveOrUpdateByIdFailedException("Person ID = "+person.getId()+" previously existed at the Database but was not updated !");
            }
        }
        return findById(person.getId());
    }

    RowMapper<Person> personRowMapper = new RowMapper<Person>() {
        @Override
        public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
            Person tempPerson = new Person();
            tempPerson.setId(rs.getLong(1));
            tempPerson.setFirstName(rs.getString(2));
            tempPerson.setLastName(rs.getString(3));
            tempPerson.setUserName(rs.getString(4));
            tempPerson.setPassword(rs.getString(5));
            tempPerson.setEmail(rs.getString(6));
            return tempPerson;
        }
    };

    private Person findById(Long personId){
        try {
            String preparedStatement = "SELECT * from PERSON where ID=?";
            return jdbcTemplate.queryForObject(preparedStatement, personRowMapper, personId);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Person> findPersonByFirstNameAndLastNameOrderByUserNameAsc(String firstName, String lastName) {
        String preparedStatement = "SELECT * from PERSON where FIRST_NAME=? and LAST_NAME=? ORDER BY USER_NAME ASC";
        return jdbcTemplate.query(preparedStatement, personRowMapper, firstName, lastName);
    }
}
