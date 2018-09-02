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

import ie.sugrue.domain.Basket;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.User;
import ie.sugrue.service.basket.CreateBasketService;
import ie.sugrue.service.basket.DeleteBasketService;
import ie.sugrue.service.basket.GetBasketService;
import ie.sugrue.service.basket.UpdateBasketService;

@CrossOrigin(origins = "*", maxAge = 3600)

@RestController
@RequestMapping("/basket")
@Scope("request")
public class BasketController extends PrimaryController {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	ResponseWrapper			resp;
	@Autowired
	GetBasketService		getBasketService;
	@Autowired
	CreateBasketService		createBasketService;
	@Autowired
	UpdateBasketService		updateBasketService;
	@Autowired
	DeleteBasketService		deleteBasketService;

	@RequestMapping("")
	public ResponseWrapper getBasket(@RequestParam(value = "id", defaultValue = "") long id) {
		return getBasketService.getBasket(resp, id);
	}

	@RequestMapping(value = "/create", method = RequestMethod.PUT)
	public ResponseWrapper createBasket(@RequestBody Basket basket) {
		log.info("CREATING -> {}", basket);

		return createBasketService.createBasket(resp, basket);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseWrapper updateBasket(@RequestBody Basket basket) {
		log.info("UPDATING -> {}", basket);

		return updateBasketService.updateBasket(resp, basket);
	}

	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public ResponseWrapper deleteBasket(@RequestBody Basket basket) {
		log.info("DELETING -> {}", basket);
		return deleteBasketService.deleteBasket(resp, basket);
	}

	@RequestMapping("/user")
	public ResponseWrapper getBasketForUser(@RequestParam(value = "userId", defaultValue = "") long userId) {
		return getBasketService.getBasketForUser(resp, userId);
	}

	@RequestMapping(value = "/user", method = RequestMethod.DELETE)
	public ResponseWrapper deleteBasketForUser(@RequestBody User user) {
		log.info("DELETING BASKET FOR -> {}", user);
		return deleteBasketService.deleteBasketForUser(resp, user);
	}
}