package com.danielpm1982.springboot3_profile_mng_no_comments.controller;

import com.danielpm1982.springboot3_profile_mng_no_comments.dto.PersonDTO;
import com.danielpm1982.springboot3_profile_mng_no_comments.entity.Person;
import com.danielpm1982.springboot3_profile_mng_no_comments.mapper.DTOToObjectMapper;
import com.danielpm1982.springboot3_profile_mng_no_comments.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
public class PersonController {
    private final PersonService personService;
    private final DTOToObjectMapper<PersonDTO, Person> myPersonMapper;
//    private final ObjectMapper myObjectMapper;
//    private final ObjectMapper myXmlMapper;

    public PersonController(PersonService personService, DTOToObjectMapper<PersonDTO, Person> myPersonMapper,
                            ObjectMapper myObjectMapper, @Qualifier("xmlMapper") ObjectMapper myXmlMapper
                            ){
        this.personService=personService;
        this.myPersonMapper = myPersonMapper;
//        this.myObjectMapper = myObjectMapper;
//        this.myXmlMapper = myXmlMapper;
    }

    @GetMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<Page<Person>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ){
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(personService.findAll(pageable));
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<Person> findById(@PathVariable("id") Long personId){
        Optional<Person> result = personService.findById(personId);
        return result.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(method = RequestMethod.HEAD, value = "/{id}")
    ResponseEntity<Person> existsById(@PathVariable("id") Long personId){
        boolean result = personService.existsById(personId);
        if (result){
            return ResponseEntity.ok().build();
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Person> deleteById(@PathVariable("id") Long personId) {
        boolean result = personService.deleteById(personId);
        if (result) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<Person> saveOrUpdateById(@PathVariable("id") Long personId, @Valid @RequestBody PersonDTO personDTO) {
        Person personToBeSavedOrUpdated = myPersonMapper.objectDTOToObject(personDTO);
        personToBeSavedOrUpdated.setId(personId);   // overrides any id passed at the @RequestBody json by the id passed at the URI (pathVariable)
        Person result = personService.saveOrUpdate(personToBeSavedOrUpdated);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/byFirstNameOrderByUserNameAsc/{first-name}")
    ResponseEntity<List<Person>> findPersonByFirstNameOrderByUserNameAsc(@PathVariable("first-name") String firstName){
        return ResponseEntity.ok(personService.findPersonByFirstNameOrderByUserNameAsc(firstName));
    }

    @GetMapping("/byLastNameOrderByUserNameAsc/{last-name}")
    ResponseEntity<List<Person>> findPersonByLastNameOrderByUserNameAsc(@PathVariable("last-name") String lastName){
        return ResponseEntity.ok(personService.findPersonByLastNameOrderByUserNameAsc(lastName));
    }

    @GetMapping("/byLastNameOrderByFirstNameAsc/{last-name}")
    ResponseEntity<List<Person>> findPersonByLastNameOrderByFirstNameAsc(@PathVariable("last-name") String lastName){
        return ResponseEntity.ok(personService.findPersonByLastNameOrderByFirstNameAsc(lastName));
    }

    @GetMapping("/byFirstNameAndLastNameOrderByUserNameAsc/{first-name}/{last-name}")
    ResponseEntity<List<Person>> findPersonByFirstNameAndLastNameOrderByUserNameAsc(@PathVariable("first-name") String firstName,
                                                                                    @PathVariable("last-name") String lastName) {
        return ResponseEntity.ok(personService.findPersonByFirstNameAndLastNameOrderByUserNameAsc(firstName, lastName));
    }
}
