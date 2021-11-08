package com.bobocode.lesson19;

import lombok.SneakyThrows;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.stream.Collectors;

public class DemoApp {

    @SneakyThrows
    public static void main(String[] args) {
        var template = new RestTemplate();
        Root root = template.getForObject("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=15&api_key=DEMO_KEY", Root.class);
        Map<String, Long> map = root.getPhotos().stream().map(Photo::getCamera).collect(Collectors.groupingBy(Camera::getName, Collectors.counting()));
        System.out.println(map);
    }

}
