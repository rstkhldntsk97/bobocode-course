package com.bobocode.lesson19;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Photo {

    private Long id;

    private Long sol;

    private Camera camera;

    @JsonProperty("img_src")
    private String imgSrc;

    private Date earth_date;

}
