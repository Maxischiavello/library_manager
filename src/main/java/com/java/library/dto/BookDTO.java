package com.java.library.dto;

import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String category;
    private Boolean availability;
}
