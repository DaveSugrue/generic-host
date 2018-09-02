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
import ie.sugrue.utils.Utils;

@Service("updateProductService")
@Scope("prototype")
public class UpdateProductServiceImpl implements UpdateProductService {

	private final Logger		log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductRepository	productRepo;

	@Autowired
	private GetProductService	getProductService;

	@Override
	public ResponseWrapper updateProduct(ResponseWrapper resp, Product product) {

		try {
			product = populateProductNullsWithdefaults(product);

			resp.addObject(productRepo.updateProduct(product));

		} catch (EmptyResultDataAccessException erdae) {
			log.error("Cannot identify product to be updated when trying to update {} to Database", product, erdae);
			resp.getStatus().updateStatus(1, "We encountered a problem saving the Product details to our database. Please try again.");
		} catch (DataAccessException dae) {
			log.error("Data Access exception encountered trying to update {} to Database", product, dae);
			resp.getStatus().updateStatus(2, "We encountered a problem saving the Product details to our database. Please try again.");
		} catch (Exception e) {
			log.error("Unknown exception encountered trying to update {} to Database", product, e);
			resp.getStatus().updateStatus(2, "We encountered a problem saving the Product details to our database. Please try again.");
		}

		return resp;
	}

	private Product populateProductNullsWithdefaults(Product product) {

		Product previousProductDetails = getProductService.getProduct(product.getId());

		if (Utils.isNull(product.getCategoryId())) {
			product.setCategoryId(previousProductDetails.getCategoryId());
		}
		if (Utils.isNull(product.getFormat())) {
			product.setFormat(previousProductDetails.getFormat());
		}
		if (Utils.isNull(product.getName())) {
			product.setName(previousProductDetails.getName());
		}
		if (Utils.isNull(product.getDescription())) {
			product.setDescription(previousProductDetails.getDescription());
		}
		if (Utils.isNull(product.getPrice())) {
			product.setPrice(previousProductDetails.getPrice());
		}
		if (Utils.isNull(product.getStock())) {
			product.setStock(previousProductDetails.getStock());
		}
		if (Utils.isNull(product.getIconUrl())) {
			product.setIconUrl(previousProductDetails.getIconUrl());
		}
		if (Utils.isNull(product.getImageUrl())) {
			product.setImageUrl(previousProductDetails.getImageUrl());
		}
		if (Utils.isNull(product.getRating())) {
			product.setRating(previousProductDetails.getRating());
		}

		return product;
	}

}
