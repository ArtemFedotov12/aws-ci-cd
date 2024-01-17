package com.start.springlearningdemo.repository;

import com.start.springlearningdemo.entity.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ItemRepositoryIT extends BaseRepositoryIT {

    @Test
    void test() {
        Item item = new Item();
        item.setName("some1");
        itemRepository.save(item);
        List<Item> all = itemRepository.findAll();
        Assertions.assertEquals(1, all.size());
    }


}
