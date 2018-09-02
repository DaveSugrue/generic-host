package ie.sugrue.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ie.sugrue.domain.Genre;
import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.service.genre.CreateGenreService;
import ie.sugrue.service.genre.DeleteGenreService;
import ie.sugrue.service.genre.GetGenreService;
import ie.sugrue.service.genre.UpdateGenreService;

@CrossOrigin(origins = "*", maxAge = 3600)

@RestController
@RequestMapping("/genres")
@Scope("request")
public class GenreController {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	@Autowired
	ResponseWrapper			resp;
	@Autowired
	GetGenreService			getGenreService;
	@Autowired
	CreateGenreService		createGenreService;
	@Autowired
	UpdateGenreService		updateGenreService;
	@Autowired
	DeleteGenreService		deleteGenreService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseWrapper getGenre(@PathVariable String id) {
		return getGenreService.getGenre(resp, id);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseWrapper getGenre() {
		return getGenreService.getGenres(resp);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseWrapper createGenre(@RequestBody Genre genre) {
		log.info("CREATING -> {}", genre);

		return createGenreService.createGenre(resp, genre);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseWrapper updateGenre(@PathVariable String id, @RequestBody Genre genre) {
		log.info("UPDATING -> {}", genre);

		return updateGenreService.updateGenre(resp, genre);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseWrapper deleteGenre(@PathVariable String id) {
		log.info("DELETING Genre with id -> {}", id);
		return deleteGenreService.deleteGenre(resp, id);
	}
}