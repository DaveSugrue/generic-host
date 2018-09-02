package ie.sugrue.service.product;

import ie.sugrue.domain.Product;
import ie.sugrue.domain.ResponseWrapper;

public interface UpdateProductService {

	ResponseWrapper updateProduct(ResponseWrapper resp, Product product);

}
