package com;

import model.Patient;

//For REST Service 
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 

//For JSON 
import com.google.gson.*; 

//For XML 
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 




@Path("/Patients")
public class PatientService {
	
Patient patientObj = new Patient();
	

	@GET
	@Path("/patient")
	@Produces(MediaType.TEXT_HTML)
	public String readPatients() {

		return patientObj.readPatients();
		
	}
	
	
	

	@POST
	@Path("/patient")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPatient(@FormParam("patientFName") String patientFName, 
			@FormParam("patientLName") String patientLName,
			@FormParam("patientAge") String patientAge, 
			@FormParam("patientAddress") String patientAddress, 
			@FormParam("patientPhone") String patientPhone) {
		String output = patientObj.insertPatient(patientFName, patientLName, patientAge, patientAddress, patientPhone);
		return output;
	}


	@PUT
	@Path("/patient")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePatient(String patientData) {
		// Convert the input string to a JSON object
		JsonObject patientObject = new JsonParser().parse(patientData).getAsJsonObject();

		// Read the values from the JSON object
		String patientID = patientObject.get("patientID").getAsString();
		String patientFName = patientObject.get("patientFName").getAsString();
		String patientLName = patientObject.get("patientLName").getAsString();
		String patientAge = patientObject.get("patientAge").getAsString();
		String patientAddress = patientObject.get("patientAddress").getAsString();
		String patientPhone = patientObject.get("patientPhone").getAsString();

		String output = patientObj.updatePatient(patientID, patientFName, patientLName, patientAge, patientAddress, patientPhone);
		
		return output;
	}
	
	

	@DELETE
	@Path("/patient")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePatient(String patientData) { 
		// Convert the input string to an XML document 
		Document doc = Jsoup.parse(patientData, "", Parser.xmlParser()); 
		//Read the value from the element <itemID> 
		String patientID = doc.select("patientID").text();

		String output = patientObj.deletePatient(patientID);

		return output;
	}
}
