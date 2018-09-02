package ie.sugrue.service.product;

import java.util.ArrayList;

import ie.sugrue.domain.Product;
import ie.sugrue.domain.ResponseWrapper;

public interface GetProductService {

	public Product getProduct(long id);

	public ResponseWrapper getProduct(ResponseWrapper resp, long id);

	public ArrayList<Product> getProducts();

	public ResponseWrapper getProducts(ResponseWrapper resp);

	public ArrayList<Product> getProducts(String category_id);

	public ResponseWrapper getProducts(ResponseWrapper resp, String category_id);
}
