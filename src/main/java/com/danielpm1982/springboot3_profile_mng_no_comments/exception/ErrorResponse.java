package com.danielpm1982.springboot3_profile_mng_no_comments.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private int statusCode;
    private String statusDescription;
    private String message;
}
