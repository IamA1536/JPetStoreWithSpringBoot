package org.csu.teamwork.jpetstore.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.csu.teamwork.jpetstore.domain.extra.Sequence;
import org.csu.teamwork.jpetstore.domain.object.Item;
import org.csu.teamwork.jpetstore.domain.orders.LineItem;
import org.csu.teamwork.jpetstore.domain.orders.Order;
import org.csu.teamwork.jpetstore.persistence.*;
import org.csu.teamwork.jpetstore.persistence.Mapper.ItemMapper;
import org.csu.teamwork.jpetstore.persistence.Mapper.LineItemMapper;
import org.csu.teamwork.jpetstore.persistence.Mapper.OrderMapper;
import org.csu.teamwork.jpetstore.persistence.Mapper.SequenceMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author A
 * Created by IamA#1536 on 2018/12/12 0:36
 */
@Service
public class OrderService {

    private SqlSessionFactory sqlSessionFactory;


    public OrderService() {
    }

    public void insertOrder(Order order) throws Exception {
        sqlSessionFactory = SessionFactoryUtil.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ItemMapper itemMapper = sqlSession.getMapper(ItemMapper.class);
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
        LineItemMapper lineItemMapper = sqlSession.getMapper(LineItemMapper.class);


        for (int i = 0; i < order.getLineItems().size(); i++) {
            LineItem lineItem = order.getLineItems().get(i);
            String itemId = lineItem.getItemId();

            Integer increment = lineItem.getQuantity();
            Map<String, Object> param = new HashMap<>(2);
            param.put("itemId", itemId);
            param.put("increment", increment);
            System.out.println(increment);
            itemMapper.updateInventoryQuantity(param);
        }

        orderMapper.insertOrder(order);
        orderMapper.insertOrderStatus(order);
        for (int i = 0; i < order.getLineItems().size(); i++) {
            LineItem lineItem = order.getLineItems().get(i);
            lineItem.setOrderId(order.getOrderId());
            lineItemMapper.insertLineItem(lineItem);
        }
    }

    public Order getOrder(int orderId) throws Exception {
        sqlSessionFactory = SessionFactoryUtil.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ItemMapper itemMapper = sqlSession.getMapper(ItemMapper.class);
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
        LineItemMapper lineItemMapper = sqlSession.getMapper(LineItemMapper.class);


        Order order = orderMapper.getOrder(orderId);
        order.setLineItems(lineItemMapper.getLineItemsByOrderId(orderId));

        for (int i = 0; i < order.getLineItems().size(); i++) {
            LineItem lineItem = order.getLineItems().get(i);
            Item item = itemMapper.getItem(lineItem.getItemId());
            item.setQuantity(itemMapper.getInventoryQuantity(lineItem.getItemId()));
            lineItem.setItem(item);
        }

        return order;
    }

    public List<Order> getOrdersByUsername(String username) throws Exception {
        sqlSessionFactory = SessionFactoryUtil.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
        return orderMapper.getOrdersByUsername(username);
    }

    public int getNextId(String name) throws Exception {
        sqlSessionFactory = SessionFactoryUtil.getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        SequenceMapper sequenceMapper = sqlSession.getMapper(SequenceMapper.class);
        Sequence sequence = new Sequence(name, -1);
        sequence = sequenceMapper.getSequence(sequence);
        if (sequence == null) {
            throw new RuntimeException("Error: A null sequence was returned from the database (could not get next " + name
                    + " sequence).");
        }
        Sequence parameterObject = new Sequence(name, sequence.getNextId() + 1);
        sequenceMapper.updateSequence(parameterObject);
        return sequence.getNextId();
    }
}
