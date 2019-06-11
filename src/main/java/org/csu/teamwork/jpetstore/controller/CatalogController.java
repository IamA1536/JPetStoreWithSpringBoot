package org.csu.teamwork.jpetstore.controller;

import org.csu.teamwork.jpetstore.domain.object.Category;
import org.csu.teamwork.jpetstore.domain.object.Item;
import org.csu.teamwork.jpetstore.domain.object.Product;
import org.csu.teamwork.jpetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @email 1694522669@qq.com
 * @author: A
 * @date: 2019/6/11 1:30
 */
@Controller
@SessionAttributes({"product", "cart", "account"})
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @GetMapping("/main")
    public String viewMain() {
        return "catalog/main";
    }

    @GetMapping("/category")
    public String viewCatalog(@RequestParam("categoryId") String categoryId, Model model) throws Exception {
        if (categoryId != null) {
            Category category = catalogService.getCategory(categoryId);
            List<Product> productList = catalogService.getProductListByCategory(categoryId);
            model.addAttribute("category", category);
            model.addAttribute("productList", productList);
        }
        return "catalog/category";
    }

    @GetMapping("/product")
//    @ModelAttribute("xx") 表示将前端传递过来的参数按照名称注入到对应的对象中，“xx”只是表示放到ModelMap中的key值
//    简单点说，ModelAttribute可以一次性把一堆参数建一个类对象，然后绑定到key对应名称的对象里。
//    这里使用@RequestParam也可以
    public String product(@RequestParam("productId") String productId, HttpSession session, Model model) throws Exception {

        Product product = catalogService.getProduct(productId);
        List<Item> itemList = catalogService.getItemListByProduct(productId);
        session.setAttribute("product", product);
        model.addAttribute("itemList", itemList);
        model.addAttribute("product", product);
        return "catalog/product";
    }

    @PostMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) throws Exception {
        if (keyword.trim().equals("")) {
            return "catalog/main";
        }
        List<Product> productList = catalogService.searchProductList(keyword);
        model.addAttribute("productList", productList);
        return "catalog/searchProduct";
    }

}
