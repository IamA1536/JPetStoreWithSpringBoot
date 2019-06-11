package org.csu.teamwork.jpetstore.persistence.Mapper;


import org.csu.teamwork.jpetstore.domain.object.Item;

import java.util.List;
import java.util.Map;

public interface ItemMapper {

  void updateInventoryQuantity(Map<String, Object> param);

  int getInventoryQuantity(String itemId);

  List<Item> getItemListByProduct(String productId);

  Item getItem(String itemId);

}
