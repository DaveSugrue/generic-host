package ie.sugrue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@Configuration
@ComponentScan
public class MainApp extends SpringBootServletInitializer implements CommandLineRunner {

	// Only needed for deploying war
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MainApp.class);
	}
	
	private final Logger	log	= LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(MainApp.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	@Override
	public void run(String... strings) throws Exception {

		System.out.println("------Starting Up--------");
		
//		MoviesGateway moviesGateway = new MoviesGateway();
//		
//		List<Movie> movies = moviesGateway.populateMovies();
//		
//		if ( !movies.isEmpty() ){
//			moviesGateway.displayMovies(movies);
//		}
	}
}