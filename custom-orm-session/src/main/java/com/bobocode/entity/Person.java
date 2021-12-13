package com.bobocode.entity;

import com.bobocode.annotation.Column;
import com.bobocode.annotation.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Rostyslav Kholodnytskyi
 * @date 13/12/2021
 * @project bobocode-course
 */

@ToString
@Table(value = "person")
@Getter @Setter
public class Person {

    @Column(value = "id")
    private Object id;

    @Column(value = "first_name")
    private String firstName;

    @Column(value = "last_name")
    private String lastName;
}
