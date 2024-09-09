package com.example.test.domain;

import com.example.test.domain.dto.RequestItemDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   id;  // 게시글 번호 (DB 인덱스)
    private String username;    // 작성자
    private String title;   // 게시글 제목
    private String content; // 게시글 내용
    private int    price;// 가격

    public Item(RequestItemDto itemDto) {
        this.username = itemDto.getUsername();
        this.title = itemDto.getTitle();
        this.content = itemDto.getContent();
        this.price = itemDto.getPrice();
    }

    public void updateItem(RequestItemDto itemDto) {
        this.username = itemDto.getUsername();
        this.title = itemDto.getTitle();
        this.content = itemDto.getContent();
        this.price = itemDto.getPrice();
    }


}
