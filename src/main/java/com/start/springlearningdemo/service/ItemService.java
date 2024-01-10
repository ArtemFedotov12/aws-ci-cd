package com.start.springlearningdemo.service;

import com.start.springlearningdemo.entity.Item;
import java.util.List;

public interface ItemService {

  List<Item> getAllItems();

  void create(final String name);
}
