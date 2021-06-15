package com.kamila.food.client.model;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Problem {

    private Integer status;
    private OffsetDateTime timestamp;
    private String userMessage;
    private List<Object> objects = new ArrayList<>();

    // Classe interna
    @Data
    public static class Object {

        private String name;
        private String userMessage;

    }

}
