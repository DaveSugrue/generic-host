package ie.sugrue.gateway;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ie.sugrue.domain.Movie;
import ie.sugrue.utils.GatewayConnector;

public class MoviesGateway {

	private final Logger	log	= LoggerFactory.getLogger(this.getClass());
	
	public List<Movie> populateMovies(){
		
		List<Movie> movies = new ArrayList<Movie>();
		
		GatewayConnector gatewayConnector = new GatewayConnector();
		Gson gson = new Gson();
		
		try {
			String jsonString = gatewayConnector.callGateway();
			log.info("jsonString = " + jsonString);
			
			if( jsonString.equals("ERROR") ){
				log.error("Gateway Response not as expected. Cannot proceed");
			} else {
				movies = gson.fromJson(jsonString, new TypeToken<List<Movie>>(){}.getType());
				log.info("movies = " + movies.toString());
			}
		} catch(ClassCastException cce){
			log.error("Error casting response from Gateway to objects we recognise", cce);
		} catch(Exception e) {
			log.error("Error trying to map the response from Gateway - data is not as we expected", e);
		}
		
		return movies;
	}
	
	public void displayMovies(List<Movie> movies){
		
		Movie movie;
		
		log.info("Displaying Movies...\n");
		
		for (int i = 0; i < movies.size(); i++){
			movie = (Movie)movies.get(i);
			System.out.println(i + " - " + movie.getName());
		}
		log.info("...and we're done!");
	}
}
