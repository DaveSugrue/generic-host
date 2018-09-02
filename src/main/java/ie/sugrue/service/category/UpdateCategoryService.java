package ie.sugrue.service.category;

import ie.sugrue.domain.Category;
import ie.sugrue.domain.ResponseWrapper;

public interface UpdateCategoryService {

	ResponseWrapper updateCategory(ResponseWrapper resp, Category category);

}
