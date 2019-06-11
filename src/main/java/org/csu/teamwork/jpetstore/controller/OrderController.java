package org.csu.teamwork.jpetstore.controller;

import org.apache.ibatis.annotations.Mapper;
import org.csu.teamwork.jpetstore.domain.account.Account;
import org.csu.teamwork.jpetstore.domain.carts.Cart;
import org.csu.teamwork.jpetstore.domain.orders.Order;
import org.csu.teamwork.jpetstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @email 1694522669@qq.com
 * @author: A
 * @date: 2019/6/11 22:18
 */
@Controller
@SessionAttributes({"order", "cart", "account"})
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/viewOrderForm")
    public String viewOrderForm(HttpSession session, Model model) {

        Account account = (Account) session.getAttribute("account");
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        if (account == null) {
            String message = "Please sign in first!";
            session.setAttribute("message", message);
            return "common/error";
        } else {
            Order order = new Order();
            order.initOrder(account, cart);
            Date date = new Date();
            order.setOrderDate(new java.sql.Date(date.getTime()));
            List<String> cardType = new ArrayList<>();
            cardType.add("Visa");
            cardType.add("MasterCard");
            cardType.add("American Express");
            Calendar c = Calendar.getInstance();
            String str = c.get(Calendar.MONTH) + String.valueOf(c.get(Calendar.DATE)) + c.get(Calendar.HOUR_OF_DAY) + c.get(Calendar.MINUTE) + c.get(Calendar.SECOND);
            int orderId = Integer.valueOf(str);
            order.setOrderId(orderId);

            model.addAttribute("order", order);
            session.setAttribute("order", order);
            session.setAttribute("creditCardTypes", cardType);

            return "order/orderForm";
        }
    }

    // 查看某一个具体订单
    @GetMapping("/viewOrder")
    public String viewOrder(@RequestParam("orderId") int orderId, Model model) throws Exception {
        Order order = orderService.getOrder(orderId);

        model.addAttribute("order", order);

        return "order/finalOrder";
    }

    @PostMapping("viewConfirmOrder")
    public String viewConfirmOrder(@Valid Order order, HttpSession session, Model model) {
        session.setAttribute("order", order);
        model.addAttribute("order", order);

        if (order.isShippingAddressRequired()) {
            Order tempOrder = (Order) session.getAttribute("order");
            tempOrder.setShipAddress1(order.getShipAddress1());
            tempOrder.setShipAddress2(order.getShipAddress2());
            tempOrder.setShipToFirstName(order.getShipToFirstName());
            tempOrder.setShipToLastName(order.getShipToLastName());
            tempOrder.setShipCity(order.getShipCity());
            tempOrder.setShipCountry(order.getShipCountry());
            tempOrder.setShipState(order.getShipState());
            tempOrder.setShipZip(order.getShipZip());
            session.setAttribute("order", tempOrder);
        }
        return "order/confirmOrder";
    }

//    @PostMapping("/confirmShip")
//    public String confirmShip(@Valid Order order, HttpSession session) {
//        Order tempOrder = (Order) session.getAttribute("order");
//        tempOrder.setShipAddress1(order.getShipAddress1());
//        tempOrder.setShipAddress2(order.getShipAddress2());
//        tempOrder.setShipToFirstName(order.getShipToFirstName());
//        tempOrder.setShipToLastName(order.getShipToLastName());
//        tempOrder.setShipCity(order.getShipCity());
//        tempOrder.setShipCountry(order.getShipCountry());
//        tempOrder.setShipState(order.getShipState());
//        tempOrder.setShipZip(order.getShipZip());
//
//        session.setAttribute("order", tempOrder);
//
//
//        return "order/confirmOrder";
//    }

    @GetMapping("/confirmOrder")
    public String confirmOrder(@Valid Order order, HttpSession session, Model model) throws Exception {
        orderService.insertOrder(order);

        Cart cart = new Cart();
        model.addAttribute("cart", cart);
        session.setAttribute("cart", cart);
        return "order/finalOrder";
    }

    @GetMapping("/checkOrder")
    public String checkOrder(HttpSession session, Model model) throws Exception {
        Account account = (Account) session.getAttribute("account");
        List<Order> orderList = orderService.getOrdersByUsername(account.getUsername());

        session.setAttribute("orderList", orderList);
        model.addAttribute("orderList", orderList);
        return "order/listOrders";
    }


}
