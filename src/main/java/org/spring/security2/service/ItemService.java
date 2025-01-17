package org.spring.security2.service;



import org.spring.security2.dto.ItemDto;

import java.io.IOException;
import java.util.List;

public interface ItemService {
    void insertItem(ItemDto itemDto) throws IOException;

    List<ItemDto> itemList();

    ItemDto itemDetail(Long id);

    void updateItem(ItemDto itemDto) throws IOException;

    void itemDelete(Long id);


}
