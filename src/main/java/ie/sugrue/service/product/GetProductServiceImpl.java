package ie.sugrue.service.product;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.Product;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.Status;
import ie.sugrue.repository.ProductRepository;

@Service("getProductService")
@Scope("prototype")
public class GetProductServiceImpl implements GetProductService {

	private final Logger		log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductRepository	productRepo;

	@Override
	public Product getProduct(long id) {

		Product product = new Product();

		try {
			product = productRepo.getProduct(id);
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting product with id of {} from DB - ", id, erdae);
		} catch (Exception e) {
			log.error("Problem occured getting product with id of {} from DB - ", id, e);
		}

		return product;
	}

	@Override
	public ResponseWrapper getProduct(ResponseWrapper resp, long id) {

		try {
			Product product = productRepo.getProduct(id);
			resp.addObject(product);
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting product with id of {} from DB - ", id, erdae);
			Status status = new Status(1, "Product Does Not Exist");
			resp.setStatus(status);
		} catch (Exception e) {
			log.error("Problem occured getting product with id of {} from DB - ", id, e);
			Status status = new Status(2, "Problem getting Product data from DB");
			resp.setStatus(status);
		}

		return resp;
	}

	@Override
	public ArrayList<Product> getProducts() {

		return getProducts("*");
	}

	@Override
	public ResponseWrapper getProducts(ResponseWrapper resp) {

		return getProducts(resp, "*");
	}

	@Override
	public ArrayList<Product> getProducts(String category_id) {

		ArrayList<Product> products = new ArrayList<Product>();

		try {
			if (category_id.equals("*")) {
				products = productRepo.getProducts();
			} else {
				products = productRepo.getProducts(category_id);
			}
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting back the list of Products", erdae);
		} catch (Exception e) {
			log.error("Problem occured getting back the list of Products", e);
		}

		return products;
	}

	@Override
	public ResponseWrapper getProducts(ResponseWrapper resp, String category_id) {

		ArrayList<Product> products = new ArrayList<Product>();
		try {

			if (category_id.equals("*")) {
				products = productRepo.getProducts();
			} else {
				products = productRepo.getProducts(category_id);
			}
			resp.setObjects(products);

		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured getting back the list of Products", erdae);
			Status status = new Status(1, "No Products found");
			resp.setStatus(status);
		} catch (Exception e) {
			log.error("Problem occured getting back the list of Products", e);
			Status status = new Status(2, "Problem getting Product data from DB");
			resp.setStatus(status);
		}

		return resp;
	}
}
