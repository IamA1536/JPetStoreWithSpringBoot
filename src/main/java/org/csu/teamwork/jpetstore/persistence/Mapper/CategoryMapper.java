package org.csu.teamwork.jpetstore.persistence.Mapper;


import org.apache.ibatis.annotations.Mapper;
import org.csu.teamwork.jpetstore.domain.object.Category;

import java.util.List;
@Mapper
public interface CategoryMapper {

  List<Category> getCategoryList();

  Category getCategory(String categoryId);

}
