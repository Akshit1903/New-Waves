package com.akshit.models;

import com.akshit.enums.UserType;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalTime;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private String id;

    private String name;

    private UserType userType;

    private Integer salary;

    private Integer monthlyLeaves;

    private LocalTime checkInTime;

    private String email;
}
