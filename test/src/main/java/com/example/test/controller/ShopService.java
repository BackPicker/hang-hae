package com.example.test.controller;


import com.example.test.domain.Item;
import com.example.test.domain.dto.RequestItemDto;
import com.example.test.domain.dto.ResponseItemDto;
import com.example.test.domain.msg.ResponseMessage;
import com.example.test.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ShopService {

    private final ItemRepository itemRepository;

    public ResponseItemDto addShop(RequestItemDto itemDto) {
        Item item = new Item(itemDto);
        itemRepository.save(item);
        return new ResponseItemDto(item);
    }

    public List<ResponseItemDto> getAllShops() {
        return itemRepository.findAllDto();
    }

    @Transactional
    public ResponseItemDto updateShop(Long id, RequestItemDto itemDto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("오류 발생"));
        item.updateItem(itemDto);
        return new ResponseItemDto(item);

    }

    public ResponseMessage deleteShop(Long id) {
        itemRepository.deleteById(id);
        return new ResponseMessage("삭제 완료");
    }
}
