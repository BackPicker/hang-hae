package com.example.test.domain.dto;

import com.example.test.domain.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class ResponseItemDto {

    private Long   id;
    private String title;
    private String content;
    private int    price;
    private String username;

    public ResponseItemDto(Long id, String title, String content, int price, String username) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.price = price;
        this.username = username;
    }

    public ResponseItemDto(Long id, String title, int price, String username) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.username = username;
    }

    public ResponseItemDto(Item item) {
        this.id = item.getId();
        this.title = item.getTitle();
        this.content = item.getContent();
        this.price = item.getPrice();
        this.username = item.getUsername();
    }
}
