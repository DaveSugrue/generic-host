package ie.sugrue.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import ie.sugrue.domain.ResponseWrapper;
import ie.sugrue.domain.Status;

public class GatewayConnector {
	
	private final Logger	log	= LoggerFactory.getLogger(this.getClass());
	
	private String urlString = "http://localhost:8080/rest/default/direct/v1/TestResource";
	
	/**
	 * Calls the gateway and returns the response string. If the message sent from the Gateway already contains an error, the returned string from this method will simply read "ERROR"
	 * @return	String	Data returned from Gateway as one long string.
	 */
	public String callGateway(){
		
		HttpURLConnection conn = null;
		BufferedReader br = null;
		String response = null;
		
		try{
			conn = connectToGateway(conn);
			
			if (conn.getResponseCode() == 200) {
				br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			} else {
				br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
			}
			
			System.out.println("Output from server:\n");
			
			String output;
			
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				response = output;
			}
			
			log.info("response = " + response);
		} catch (IOException ioe) {
			log.error("Although we could make a connection to the Gateway, there was a problem retrieving the data", ioe);
		} finally {
			if (Utils.isNotNull(br)){
				try{
					log.info("closing buffered reader");
					br.close();
				} catch(IOException ioe) {
					log.error("Error closing buffered reader", ioe);
				}
			}
		}
		
		if (Utils.isNotNull(conn)){
			log.info("Disconnecting");
			conn.disconnect();
			log.info("Disconnected");
		}
		
		if ( !isGatewayResponseOK(response) ){
			response = "ERROR";
		} else {
			response = extractObjectString(response);
		}
		
		log.info("response before returning = " + response);
		
		return response;
	}
	
	public HttpURLConnection connectToGateway(HttpURLConnection conn){
		
		try {
			URL url = new URL(urlString);
		
			log.info("URL {} created", url);
			
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			log.info("Success - Set Request Method");
			conn.setRequestProperty("Accept", "application/json");
			log.info("Success - Set Accept property");
			conn.setRequestProperty("Authorization", "Basic YWRtaW46UGFzc3dvcmQx");
			log.info("Success - Set Authorization method");
	
			log.info("Connection opened. Response Code = {} ", conn.getResponseCode());
		
		} catch(MalformedURLException mue) {
			log.error("MalformedURLException encountered trying to specify {} as a URL", urlString, mue);
		} catch (IOException ioe) {
			log.error("Major IO Exception when trying to talk to {}", urlString, ioe);
		} catch(Exception e) {
			log.error("Cannot connect to Gateway at URL - {}", urlString, e);
		}
		
		return conn;
	}
	
	public boolean isGatewayResponseOK(String jsonString){
		
		Gson gson = new Gson();
		
		//Get Status and confirm it is ok
		ResponseWrapper respFromGateway = gson.fromJson(jsonString, ResponseWrapper.class);
		
		log.info("respFromGateway = " + respFromGateway.toString());
		
		Status status = respFromGateway.getStatus();
		
		log.info("status = " + status.toString());
		log.info("status type = " + status.getClass().getTypeName());
		
		if (status.getCode() == 0){
			return true;
		}
		else {
			return false;
		}
	}
	
	public String extractObjectString(String jsonString){
		
		String objectsJsonString = "";
		
		//Get objects and cast them back to a list of what they should be. To do this, we should just map the objects part of the JSON
		String token = "},\"objects\":";
		log.info("token = " + token);
		
		String[] data = jsonString.split(token);
		objectsJsonString = data[1].substring(0, data[1].length() - 1);
		log.info("objectsJsonString = " + objectsJsonString);
		
		return objectsJsonString;
	}
	
	public String getUrlString() {
		return urlString;
	}

	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}
}
