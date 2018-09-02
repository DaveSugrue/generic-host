package ie.sugrue.repository;

import java.util.ArrayList;

import ie.sugrue.domain.Product;

public interface ProductRepository {

	public void createProduct(Product product);

	public Product getProduct(long id);

	public ArrayList<Product> getProducts(String category_id);

	public ArrayList<Product> getProducts();

	public void deleteProduct(long id);

	public Product updateProduct(Product product);
}