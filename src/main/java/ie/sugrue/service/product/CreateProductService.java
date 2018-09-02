package ie.sugrue.service.product;

import ie.sugrue.domain.Product;
import ie.sugrue.domain.ResponseWrapper;

public interface CreateProductService {

	ResponseWrapper createProduct(ResponseWrapper resp, Product product);

}
