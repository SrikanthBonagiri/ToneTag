package com.tonetag.resourse;

import java.util.Iterator;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



@Path("/resource")
public class DialogResource {
	        
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/callback")
	public Response getUserInput(String userInput) {
		
		JSONParser parser = new JSONParser();
		JSONObject request = null;
		String json = null;
		StringBuffer response = new StringBuffer();
		int counter = 0;

		
		try {
			request = (JSONObject) parser.parse(userInput);
		} catch (ParseException pe) {
			System.out.println("Error: could not parse JSON response:");
		}
		
		System.out.println(userInput);
		
		JSONObject queryResult = (JSONObject) request.get("queryResult");
		JSONObject intentResult = (JSONObject) queryResult.get("intent");
		String intent = (String) intentResult.get("displayName");
		System.out.println(intent);
		
		if(intent.equalsIgnoreCase(Intents.welcome.toString())){
			
			response.append("Welcome to Chayoos Restaurent !!");
			
		}
		else if(intent.equalsIgnoreCase(Intents.ordering.toString())){
			
			JSONArray contexts =  (JSONArray) queryResult.get("outputContexts");
			
			for(int i =0;i<contexts.size();i++){
				
				JSONObject array = (JSONObject) contexts.get(i);
				JSONObject parameters = (JSONObject) array.get("parameters");
				
				JSONArray menulist =  (JSONArray) parameters.get("menu.original");
				JSONArray Quantitylist =  (JSONArray) parameters.get("number.original");
				
				response.append("Ordering  ");
				
					for (Object Quantity : Quantitylist) {
						if(counter==0){
							System.out.println(Quantity.toString());
						response.append(Quantity.toString());
						}
						counter++;
						Iterator<String> menuItem = menulist.iterator();   
						if(counter==1){
						    String name = menuItem.next().toString();
						    System.out.println(name);
							response.append(" "+ name+" ");
						    if(name.equals(name)){
						    	menuItem.remove();
						    }
						    counter--;
						}
	
					}
					break;
					
				
			}
			
			response.append(".Thank you for ordering!!");
			
		}else{
			
			response.append("Sorry !! I haven't trained on this.");
			
		}
		
		try {
			
			json = "{\"fulfillmentMessages\": [ {\"text\": {\"text\": [\"" + response + "\"] } } ]"
					+ ",\"fulfillmentText\": \"" + response + "\",\"source\":\"Test\"}";
		
		} catch (Exception e) {
			
			response.append("Opps!. I am still learning. Please talk to a human to resolve issues.");
		}
		return Response.status(Response.Status.OK).entity(json).build();
	}

}

