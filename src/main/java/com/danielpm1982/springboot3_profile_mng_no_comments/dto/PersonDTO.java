package com.danielpm1982.springboot3_profile_mng_no_comments.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PERSON")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonDTO {
    @Id
    @Column("ID")
    @NotNull
    @XmlElement
    private Long id;
    @Column("FIRST_NAME")
    @NotBlank
    @XmlElement
    private String firstName;
    @Column("LAST_NAME")
    @NotBlank
    @XmlElement
    private String lastName;
    @Column("USER_NAME")
    @Size(message = "Username must have more than 6 chars and less than 19 chars",min = 6,max = 19)
    @XmlElement
    private String userName;
    @Column("PASSWORD")
    @Size(message = "Password must have exactly 8 chars",min = 8,max = 8)
    @XmlElement
    private String password;
    @Column("EMAIL")
    @Email
    @XmlElement
    private String email;
}
