package org.csu.teamwork.jpetstore.controller;

import org.csu.teamwork.jpetstore.domain.account.Account;
import org.csu.teamwork.jpetstore.domain.carts.Cart;
import org.csu.teamwork.jpetstore.domain.carts.CartItem;
import org.csu.teamwork.jpetstore.domain.object.Item;
import org.csu.teamwork.jpetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

/**
 * @email 1694522669@qq.com
 * @author: A
 * @date: 2019/6/11 19:11
 */
@Controller
public class CartController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/cart")
    public String addToCart(@RequestParam("workingItemId") String workingItemId, HttpSession session, Model model) throws Exception {
        Cart cart = (Cart) session.getAttribute("cart");
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            String message = "Please sign in first!";
            session.setAttribute("message", message);
            return "common/error";
        }
        if (cart == null)
            cart = new Cart();
        if (cart.containsItemId(workingItemId)) {
            cart.incrementQuantityByItemId(workingItemId);

        } else {
            boolean isInStock = catalogService.isItemInStock(workingItemId);
            Item item = catalogService.getItem(workingItemId);
            cart.addItem(item, isInStock);
        }
        session.setAttribute("cart", cart);
        model.addAttribute("cart", cart);
        return "cart/cart";
    }

    @GetMapping("/viewCart")
    public String cart(Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            String message = "Please sign in first!";
            session.setAttribute("message", message);
            return "common/error";
        }
        if (cart == null) {
            cart = new Cart();
        }
        model.addAttribute("cart", cart);
        session.setAttribute("cart", cart);
        return "cart/cart";
    }

    @GetMapping("/removeItemFromCart")
    public String removeItemFromCart(@RequestParam("workingItemId") String workingItemId, Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        Item item = cart.removeItemById(workingItemId);
        if (item == null) {
            session.setAttribute("message", "Attempted to remove null CartItem from Cart.");
            return "common/error";
        }
        model.addAttribute("cart", cart);
        session.setAttribute("cart",cart);
        return "cart/cart";

    }

    @GetMapping("/remove")
    public String remove(@RequestParam("workingItemId") String workingItemId, HttpServletResponse response, Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");

        Item item = cart.removeItemById(workingItemId);

        response.setContentType("text/xml;charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setHeader("Cache-Control", "no-cache");
        assert out != null;
        out.println("<?xml version='1.0' encoding='" + "utf-8" + "' ?>");

        if (item == null) {
            session.setAttribute("message", "Attempted to remove null CartItem from Cart.");
            out.close();
            return "common/error";
        } else {

            out.println("<Msg>" + workingItemId + "?" + cart.getSubTotal() + "</Msg>");
            out.flush();
            out.close();
            session.setAttribute("cart", cart);

            return "cart/cart";
        }
    }

    @GetMapping("/updateCart")
    public void updateCart(@RequestParam("itemId") String itemId, @RequestParam("quantity") String quantity, HttpServletResponse response, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        Iterator<CartItem> cartItemIterator = cart.getAllCartItems();

        try {
            response.setContentType("text/xml;charset=utf-8");
            PrintWriter out = response.getWriter();
            response.setHeader("Cache-Control", "no-cache");
            out.println("<?xml version='1.0' encoding='" + "utf-8" + "' ?>");

            while (cartItemIterator.hasNext()) {
                CartItem cartItem = cartItemIterator.next();
                if (itemId.equals(cartItem.getItem().getItemId())) {
                    try {
                        int finalQuantity = Integer.parseInt(quantity);
                        cart.setQuantityByItemId(itemId, finalQuantity);


                        if (finalQuantity < 1) {
                            cartItemIterator.remove();
                            out.println("<Msg>" + itemId + "?0?" + cart.getSubTotal() + "</Msg>");
                        } else {

                            out.println("<Msg>" + itemId + "?" + cartItem.getTotal() + "?" + cart.getSubTotal() + "</Msg>");

                        }
                        out.flush();
                        out.close();
                        break;
                    } catch (Exception e) {
                        //ignore parse exceptions on purpose
                        e.printStackTrace();
                    }
                }
            }
            session.setAttribute("cart", cart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
