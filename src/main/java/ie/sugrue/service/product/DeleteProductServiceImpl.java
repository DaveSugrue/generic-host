package ie.sugrue.service.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import ie.sugrue.domain.Product;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.repository.ProductRepository;

@Service("deleteProductService")
@Scope("prototype")
public class DeleteProductServiceImpl implements DeleteProductService {

	private final Logger		log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductRepository	productRepo;

	@Override
	public ResponseWrapper deleteProduct(ResponseWrapper resp, Product product) {

		try {
			if (product.getId() > 0) {
				productRepo.deleteProduct(product.getId());
			} else {
				log.error("Cannot identify Product to be deleted when trying to delete {} from Database", product);
				resp.getStatus().updateStatus(1, "We encountered a problem deleting Product details from our database. Please try again.");
			}
		} catch (EmptyResultDataAccessException erdae) {
			log.info("Problem occured deleting product with id of {} from DB - ", product.getId(), erdae);
			resp.getStatus().updateStatus(1, "Product cannot be deleted as it does not exist.");
		} catch (DataAccessException dae) {
			log.error("Data Access exception encountered trying to delete {} from Database", product, dae);
			resp.getStatus().updateStatus(2, "We encountered a problem deleting Product details from our database. Please try again.");
		} catch (Exception e) {
			log.error("Unknown exception encountered trying to delete {} to Database", product, e);
			resp.getStatus().updateStatus(2, "We encountered a problem deleting Product details from our database. Please try again.");
		}

		return resp;
	}
}
