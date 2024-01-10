package com.start.springlearningdemo.controller;

import com.start.springlearningdemo.controller.dto.request.ItemRequestDto;
import com.start.springlearningdemo.entity.Item;
import com.start.springlearningdemo.service.ItemService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

  private final ItemService itemService;

  public ItemController(ItemService itemService) {
    this.itemService = itemService;
  }

  @GetMapping("/items")
  public List<Item> getAllItems() {
    return itemService.getAllItems();
  }

  @PostMapping("/items")
  public void createItem(@RequestBody ItemRequestDto itemRequestDto) {
    itemService.create(itemRequestDto.getName());
  }
}
