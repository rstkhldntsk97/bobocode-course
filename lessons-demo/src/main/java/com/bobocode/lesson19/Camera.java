package com.bobocode.lesson19;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Camera {

    private Long id;

    private String name;

    @JsonProperty("full_name")
    private String fullName;

}
