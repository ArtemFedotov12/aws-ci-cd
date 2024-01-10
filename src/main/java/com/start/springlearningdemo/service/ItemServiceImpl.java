package com.start.springlearningdemo.service;

import com.start.springlearningdemo.entity.Item;
import com.start.springlearningdemo.repositories.ItemRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

  private final ItemRepository itemRepository;

  public ItemServiceImpl(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  @Override
  public List<Item> getAllItems() {
    return itemRepository.findAll();
  }

  @Override
  public void create(final String name) {
    final Item item = new Item();
    item.setName(name);
    itemRepository.save(item);
  }
}
