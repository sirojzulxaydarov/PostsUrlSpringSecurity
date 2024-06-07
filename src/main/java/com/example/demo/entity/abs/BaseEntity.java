package com.example.demo.entity.abs;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
