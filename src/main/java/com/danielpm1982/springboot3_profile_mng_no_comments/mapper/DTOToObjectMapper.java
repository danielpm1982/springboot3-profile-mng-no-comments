package com.danielpm1982.springboot3_profile_mng_no_comments.mapper;

import com.danielpm1982.springboot3_profile_mng_no_comments.dto.ObjectDTO;
import org.springframework.stereotype.Component;

@Component
public interface DTOToObjectMapper<S extends ObjectDTO, T> {

    public S objectToObjectDTO(T object);

    public T objectDTOToObject(S objectDTO);
}
