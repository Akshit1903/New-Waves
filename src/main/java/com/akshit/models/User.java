package com.akshit.models;

import com.akshit.enums.UserType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Time;
import java.time.LocalTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private String id;

    private String name;

    private UserType userType;

    private Integer salary;

    private Integer monthlyLeaves;

    private LocalTime checkInTime;
}
