package com.sparta.springcore.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {

    private String title;
    private String image;
    private String link;
    private int lprice;

}