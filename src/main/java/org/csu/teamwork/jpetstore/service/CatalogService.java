package org.csu.teamwork.jpetstore.service;

import org.csu.teamwork.jpetstore.domain.object.Category;
import org.csu.teamwork.jpetstore.domain.object.Item;
import org.csu.teamwork.jpetstore.domain.object.Product;
import org.csu.teamwork.jpetstore.persistence.Mapper.CategoryMapper;
import org.csu.teamwork.jpetstore.persistence.Mapper.ItemMapper;
import org.csu.teamwork.jpetstore.persistence.Mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author A
 * Created by IamA#1536 on 2018/12/5 17:42
 */
@Service
public class CatalogService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ItemMapper itemMapper;

    public CatalogService() {

    }

    public List<Category> getCategoryList() throws Exception {
        return categoryMapper.getCategoryList();
    }

    public Category getCategory(String categoryId) throws Exception {
        return categoryMapper.getCategory(categoryId);
    }

    public Product getProduct(String productId) throws Exception {
        return productMapper.getProduct(productId);
    }

    public List<Product> getProductListByCategory(String categoryId) throws Exception {
        return productMapper.getProductListByCategory(categoryId);
    }

    // TODO enable using more than one keyword
    public List<Product> searchProductList(String keyword) throws Exception {
        return productMapper.searchProductList("%" + keyword.toLowerCase() + "%");
    }

    public List<Item> getItemListByProduct(String productId) throws Exception {
        return itemMapper.getItemListByProduct(productId);
    }

    public Item getItem(String itemId) throws Exception {
        return itemMapper.getItem(itemId);
    }

    public boolean isItemInStock(String itemId) throws Exception {
        return itemMapper.getInventoryQuantity(itemId) > 0;
    }
}
