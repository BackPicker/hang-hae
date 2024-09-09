package com.example.test.repository;

import com.example.test.domain.Item;
import com.example.test.domain.dto.ResponseItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select new com.example.test.domain.dto.ResponseItemDto(i.id, i.title,i.content, i.price, i.username) from Item  i")
    List<ResponseItemDto> findAllDto();

}
