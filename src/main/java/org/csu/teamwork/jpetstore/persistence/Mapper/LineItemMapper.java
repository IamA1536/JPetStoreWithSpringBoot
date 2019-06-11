package org.csu.teamwork.jpetstore.persistence.Mapper;


import org.csu.teamwork.jpetstore.domain.orders.LineItem;

import java.util.List;

public interface LineItemMapper {

  List<LineItem> getLineItemsByOrderId(int orderId);

  void insertLineItem(LineItem lineItem);

}
