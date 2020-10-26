package com.example.demo.model;

import com.example.demo.validator.UserLengthConstraint;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
public class User {
    @Id
    @JsonProperty("id")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    private UUID id;

    @JsonProperty("name")
    @NotNull
    @UserLengthConstraint
    private String name;

    @JsonProperty("surname")
    @NotNull
    @UserLengthConstraint
    private String surname;

    @JsonProperty("gender")
    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;

    public User(String name, String surname, Gender gender) {

        this.id = UUID.randomUUID();
        this.name = name;
        this.surname = surname;
        this.gender = gender;
    }
}
