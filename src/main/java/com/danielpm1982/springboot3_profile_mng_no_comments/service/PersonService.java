package com.danielpm1982.springboot3_profile_mng_no_comments.service;

import com.danielpm1982.springboot3_profile_mng_no_comments.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface PersonService {
    Page<Person> findAll(Pageable pageable);

    Optional<Person> findById(Long id);

    boolean existsById(Long id);

    Person saveOrUpdate(Person person);

    boolean deleteById(Long id);

    List<Person> findPersonByFirstNameOrderByUserNameAsc(String firstname);

    List<Person> findPersonByLastNameOrderByUserNameAsc(String lastName);

    List<Person> findPersonByLastNameOrderByFirstNameAsc(String lastName);

    List<Person> findPersonByFirstNameAndLastNameOrderByUserNameAsc(String firstName, String lastName);
}
