package ie.sugrue.service.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.Product;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.repository.ProductRepository;

@Service("createProductService")
@Scope("prototype")
public class CreateProductServiceImpl implements CreateProductService {

	private final Logger		log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductRepository	productRepo;

	@Override
	public ResponseWrapper createProduct(ResponseWrapper resp, Product product) {

		try {
			productRepo.createProduct(product);
		} catch (DuplicateKeyException dk) {
			log.info("Duplicate key on id: {}", product.getId());
			resp.getStatus().updateStatus(1, "The product id '" + product.getId() + "' is already in use.");
		} catch (DataAccessException dae) {
			log.error("Data Access exception encountered trying to save {} to Database", product, dae);
			resp.getStatus().updateStatus(2, "We encountered a problem saving the Product details to our database. Please try again.");
		} catch (Exception e) {
			log.error("Unknown exception encountered trying to save {} to Database", product, e);
			resp.getStatus().updateStatus(2, "We encountered a problem saving the Product details to our database. Please try again.");
		}

		return resp;
	}

}
