package com.kaiyee0.reflection.entity;

import com.kaiyee0.reflection.annotation.JsonObject;
import com.kaiyee0.reflection.annotation.PositiveNumber;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonObject
@AllArgsConstructor
public class Person {
    private String name;

    @PositiveNumber
    private Integer age;
}
