package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Patient {
	// A common method to connect to the DB

		private Connection connect() {

			Connection con = null;

			try {
				Class.forName("com.mysql.jdbc.Driver");

				// Provide the correct details: DBServer/DBName, username, password
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/paf", "root", "");

			} catch (Exception e) {

				e.printStackTrace();
			}

			return con;
		}

		public String insertPatient(String fname, String lname, String age, String address, String phone) {

			String output = "";

			try {
				Connection con = connect();

				if (con == null) {

					return "Error while connecting to the database for inserting.";
				}

				// create a prepared statement
				String query = " insert into patients(`patientID`,`patientFName`,`patientLName`,`patientAge`,`patientAddress`,`patientPhone`)"
						+ " values (?, ?, ?, ?, ?, ?)";

				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values

				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, fname);
				preparedStmt.setString(3, lname);
				preparedStmt.setString(4, age);
				preparedStmt.setString(5, address);
				preparedStmt.setString(6, phone);

				// execute the statement
				preparedStmt.execute();
				con.close();

				output = "Inserted successfully";

			} catch (Exception e) {

				output = "Error while inserting the item.";
				System.err.println(e.getMessage());
			}

			return output;
		}
		
		
		
		
		
		public String readPatients() {

			String output = "";

			try {

				Connection con = connect();

				if (con == null) {
					return "Error while connecting to the database for reading.";
				}

				// Prepare the html table to be displayed
				output = "<table border=\"1\"><tr><th>Patient First Name</th><th>Patient Last Name</th><th>Patient Age</th><th>Patient Address</th><th>Patient Contact Number</th><th>Update</th><th>Remove</th></tr>";

				String query = "select * from patients";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);

				// iterate through the rows in the result set
				while (rs.next()) {

					String patientID = Integer.toString(rs.getInt("patientID"));
					String patientFName = rs.getString("patientFName");
					String patientLName = rs.getString("patientLName");
					String patientAge = rs.getString("patientAge");
					String patientAddress = rs.getString("patientAddress");
					String patientPhone = rs.getString("patientPhone");

					// Add into the html table
					output += "<tr><td>" + patientFName + "</td>";
					output += "<td>" + patientLName + "</td>";
					output += "<td>" + patientAge + "</td>";
					output += "<td>" + patientAddress + "</td>";
					output += "<td>" + patientPhone + "</td>";

					// buttons
					output += "<td><input name=\"btnUpdate\" type=\"button\"value=\"Update\" class=\"btn btn-secondary\"></td>"
							+ "<td><form method=\"post\" action=\"items.jsp\">"
							+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"class=\"btn btn-danger\">"
							+ "<input name=\"itemID\" type=\"hidden\" value=\"" + patientID + "\">" + "</form></td></tr>";
				}

				con.close();

				// Complete the html table
				output += "</table>";
			} catch (Exception e) {
				output = "Error while reading the items.";
				System.err.println(e.getMessage());
			}

			return output;
		}
		
		

		public String updatePatient(String ID, String fname, String lname, String age, String address, String phone) {

			String output = "";

			try {
				Connection con = connect();

				if (con == null) {
					return "Error while connecting to the database for updating.";
				}

				// create a prepared statement
				String query = "UPDATE patients SET patientFName=?,patientLName=?,patientAge=?,patientAddress=?,patientPhone=? WHERE patientID=?";

				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values
				preparedStmt.setString(1, fname);
				preparedStmt.setString(2, lname);
				preparedStmt.setString(3, age);
				preparedStmt.setString(4, address);
				preparedStmt.setString(5, phone);
				preparedStmt.setInt(6, Integer.parseInt(ID));

				// execute the statement

				preparedStmt.execute();
				con.close();

				output = "Updated successfully";
			} catch (Exception e) {
				output = "Error while updating the item.";
				System.err.println(e.getMessage());
			}

			return output;
		}

		
		
		public String deletePatient(String patientID) {

			String output = "";

			try {
				Connection con = connect();

				if (con == null) {
					return "Error while connecting to the database for deleting.";
				}

				// create a prepared statement
				String query = "delete from patients where patientID=?";

				PreparedStatement preparedStmt = con.prepareStatement(query);

				// binding values

				preparedStmt.setInt(1, Integer.parseInt(patientID));

				// execute the statement

				preparedStmt.execute();
				con.close();

				output = "Deleted successfully";

			} catch (Exception e) {

				output = "Error while deleting the item.";

				System.err.println(e.getMessage());
			}

			return output;
		}
}
