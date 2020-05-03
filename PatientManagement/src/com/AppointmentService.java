package com;

//For REST Service 
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 

//For JSON 
import com.google.gson.*; 

//For XML 
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 

import model.Appointments;

@Path("/appointments")

public class AppointmentService {
	
Appointments appObj = new Appointments();
	
	@GET
	@Path("/appointment")
	@Produces(MediaType.TEXT_HTML)
	public String readPatientAppointments() {

		return appObj.readAppointments();
		
	}
	
	@POST
	@Path("/appointment")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPatientAppointment(@FormParam("patientName") String patientName, 
			@FormParam("patientContactNo") String patientContactNo,
			@FormParam("doctorName") String doctorName, 
			@FormParam("hospitalName") String hospitalName, 
			@FormParam("appointmentDate") String appointmentDate, 
			@FormParam("appointmentTime") String appointmentTime){
		String output = appObj.insertAppointment(patientName, patientContactNo, doctorName, hospitalName, appointmentDate, appointmentTime);
		return output;
		
		
	}
	
	
	@PUT
	@Path("/appointment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePatientAppointments(String appointmentData) {
		// Convert the input string to a JSON object
		JsonObject appointmentObject = new JsonParser().parse(appointmentData).getAsJsonObject();

		// Read the values from the JSON object
		String appointmentID = appointmentObject.get("appointmentID").getAsString();
		String patientName = appointmentObject.get("patientName").getAsString();
		String patientContactNo = appointmentObject.get("patientContactNo").getAsString();
		String doctorName = appointmentObject.get("doctorName").getAsString();
		String hospitalName = appointmentObject.get("hospitalName").getAsString();
		String appointmentDate = appointmentObject.get("appointmentDate").getAsString();
		String appointmentTime = appointmentObject.get("appointmentTime").getAsString();

		String output = appObj.updateAppointment(appointmentID, patientName, patientContactNo, doctorName, hospitalName, appointmentDate, appointmentTime);
		
		return output;
	}

		
		
		
	
	
	
	@DELETE
	@Path("/appointment")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePatientAppointments(String appointmentData) { 
		// Convert the input string to an XML document 
		Document doc = Jsoup.parse(appointmentData, "", Parser.xmlParser()); 
		//Read the value from the element <itemID> 
		String appointmentID = doc.select("appointmentID").text();

		String output = appObj.deleteAppointment(appointmentID);

		return output;
	}

}
