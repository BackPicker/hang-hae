package com.example.test.controller;

import com.example.test.domain.dto.RequestItemDto;
import com.example.test.domain.dto.ResponseItemDto;
import com.example.test.domain.msg.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @ResponseBody
    @PostMapping("/post")
    public ResponseItemDto addShop(@RequestBody RequestItemDto itemDto) {
        return shopService.addShop(itemDto);
    }

    @GetMapping("/post")
    public List<ResponseItemDto> getAllShops() {
        return shopService.getAllShops();
    }

    @PutMapping("/post/{id}")
    public ResponseItemDto updateShop(@PathVariable("id") Long id, @RequestBody RequestItemDto itemDto) {
        return shopService.updateShop(id, itemDto);
    }

    @DeleteMapping("/post/{id}")
    public ResponseMessage deleteShop(@PathVariable("id") Long id) {
        return shopService.deleteShop(id);
    }


}
