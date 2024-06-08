package com.example.demo.dto;

public record ReqRegister (

    String email,
    String password,
    String passwordRepeat,
    String firstName,
    String lastName,
    Integer age
){
}
