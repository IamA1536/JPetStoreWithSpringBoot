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
    public String product(@ModelAttribute("productId") String productId, HttpSession session, Model model) throws Exception {

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
