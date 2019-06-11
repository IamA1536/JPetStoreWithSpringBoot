package org.csu.teamwork.jpetstore.persistence.Mapper;



import org.csu.teamwork.jpetstore.domain.orders.Order;

import java.util.List;

public interface OrderMapper {

  List<Order> getOrdersByUsername(String username);

  Order getOrder(int orderId);
  
  void insertOrder(Order order);
  
  void insertOrderStatus(Order order);

}
