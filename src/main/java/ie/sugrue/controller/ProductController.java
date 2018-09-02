package ie.sugrue.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ie.sugrue.domain.Product;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.service.product.CreateProductService;
import ie.sugrue.service.product.DeleteProductService;
import ie.sugrue.service.product.GetProductService;
import ie.sugrue.service.product.UpdateProductService;

@CrossOrigin(origins = "*", maxAge = 3600)

@RestController
@RequestMapping("/product")
@Scope("request")
public class ProductController extends PrimaryController {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	ResponseWrapper			resp;
	@Autowired
	GetProductService		getProductService;
	@Autowired
	CreateProductService	createProductService;
	@Autowired
	UpdateProductService	updateProductService;
	@Autowired
	DeleteProductService	deleteProductService;

	@RequestMapping("")
	public ResponseWrapper getProduct(@RequestParam(value = "id", defaultValue = "") long id) {
		ResponseWrapper resp2 = getProductService.getProduct(resp, id);
		return resp2;
	}

	@RequestMapping("/all")
	public ResponseWrapper getProduct(@RequestParam(value = "category_id", defaultValue = "") String category_id) {
		// If no Category id specified, bring them all back (default)
		if (category_id.equals("")) {
			resp = getProductService.getProducts(resp);
		} else {
			resp = getProductService.getProducts(resp, category_id);
		}
		log.info("Called:: /product/all");
		log.info("Returning:: " + resp);
		return resp;
	}

	@RequestMapping(value = "/create", method = RequestMethod.PUT)
	public ResponseWrapper createProduct(@RequestBody Product product) {
		log.info("CREATING -> {}", product);

		return createProductService.createProduct(resp, product);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseWrapper updateProduct(@RequestBody Product product) {
		log.info("UPDATING -> {}", product);

		return updateProductService.updateProduct(resp, product);
	}

	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public ResponseWrapper deleteProduct(@RequestBody Product product) {
		log.info("DELETING -> {}", product);
		return deleteProductService.deleteProduct(resp, product);
	}
}