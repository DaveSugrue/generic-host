package ie.sugrue.service.category;

import ie.sugrue.domain.Category;
import ie.sugrue.domain.ResponseWrapper;

public interface CreateCategoryService {

	ResponseWrapper createCategory(ResponseWrapper resp, Category category);

}
