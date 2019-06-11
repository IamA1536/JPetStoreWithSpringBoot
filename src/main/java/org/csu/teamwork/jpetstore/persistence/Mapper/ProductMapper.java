package org.csu.teamwork.jpetstore.persistence.Mapper;


import org.csu.teamwork.jpetstore.domain.object.Product;

import java.util.List;

public interface ProductMapper {

  List<Product> getProductListByCategory(String categoryId);

  Product getProduct(String productId);

  List<Product> searchProductList(String keywords);

}
