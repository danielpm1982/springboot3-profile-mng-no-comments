package com.danielpm1982.springboot3_profile_mng_no_comments.repository;

import com.danielpm1982.springboot3_profile_mng_no_comments.entity.Person;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends ListCrudRepository<Person, Long>, PagingAndSortingRepository<Person, Long>, PersonCustomRepository {
    @Query(value = "SELECT * FROM PERSON where FIRST_NAME= :firstName ORDER BY USER_NAME ASC")
    List<Person> findPersonByFirstNameOrderByUserNameAsc(@Param("firstName") String firstname);

    @Query(value = "SELECT * FROM PERSON p where p.LAST_NAME= :lastName ORDER BY p.USER_NAME ASC")
    List<Person> findPersonByLastNameOrderByUserNameAsc(@Param("lastName") String lastName);

    List<Person> findPersonByLastNameOrderByFirstNameAsc(String lastName);
}
