package com.danielpm1982.springboot3_profile_mng_no_comments.repository;

import com.danielpm1982.springboot3_profile_mng_no_comments.entity.Person;
import java.util.List;

public interface PersonCustomRepository {
    public Person saveOrUpdate(Person person);
    public List<Person> findPersonByFirstNameAndLastNameOrderByUserNameAsc(String firstName, String lastName);
}
