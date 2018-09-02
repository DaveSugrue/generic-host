package ie.sugrue.service.product;

import ie.sugrue.domain.Product;
import ie.sugrue.domain.ResponseWrapper;

public interface DeleteProductService {

	ResponseWrapper deleteProduct(ResponseWrapper resp, Product product);

}
