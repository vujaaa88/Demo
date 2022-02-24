package web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import service.ManipulateXML;


/**
 * Servlet implementation class WebServlet
 */
public class WebServlet extends HttpServlet {

	ManipulateXML xmlEl = new ManipulateXML();
	/*
	public void doGet(HttpServletRequest req,HttpServletResponse res)  
			throws ServletException,IOException  
			{  
	
		try {
			result = (ArrayList<Continent>) xmlEl.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		   PrintWriter out = res.getWriter();
	        //set content type for writing the output
		   res.setContentType("text/html");
	        out.println("<html><head>"
	        		+ "<title>Simple web application</title>"
	        		+ "<link rel=\"stylesheet\" href=\"css/style.css\">"
	        		+ "</head><body>");
	        showRegisterForm(req, res);
	        showTable(req, res);
	    /*    out.println("<table>");
	        out.println("<tr>");
	        out.println("<th>Full Name</th>");
	        out.println("<th>Address</th>");
	        out.println("<th>City</th>");
	        out.println("<th>Country</th>");
	        out.println("<th>Email</th>");
	        out.println("<th>Action</th>");
	        out.println("</tr>");
	        for(Continent con: result) {
	        	countries = con.getCountries();
	        	for(Country country: countries) {
	        		users = country.getUsers();
	        		for(User user: users) {
	        			out.println("<tr>");
	        			out.println("<td>" + user.getFirstName() + " " + user.getLastName() + "</td>");
	        			out.println("<td>" + user.getAddress() + "</td>");
	        			out.println("<td>" + user.getCity() + "</td>");
	        			out.println("<td>" + user.getCountry() + "</td>");
	        			out.println("<td>" + user.getEmail() + "</td>");
	        			out.println("<td><button class=\"btn\">Delete</button></td>"); 
	        			out.println("</tr>");
	        		}	
	        	}
	        }*//*
	        out.println("</body></html>");
			}

    private void showTable(HttpServletRequest req, HttpServletResponse res) throws IOException {
    	 PrintWriter out = res.getWriter();
    	   out.println("<table>");
	        out.println("<tr>");
	        out.println("<th>Full Name</th>");
	        out.println("<th>Address</th>");
	        out.println("<th>City</th>");
	        out.println("<th>Country</th>");
	        out.println("<th>Email</th>");
	        out.println("<th>Action</th>");
	        out.println("</tr>");
	        for(Continent con: result) {
	        	countries = con.getCountries();
	        	for(Country country: countries) {
	        		users = country.getUsers();
	        		for(User user: users) {
	        			out.println("<tr>");
	        			out.println("<td>" + user.getFirstName() + " " + user.getLastName() + "</td>");
	        			out.println("<td>" + user.getAddress() + "</td>");
	        			out.println("<td>" + user.getCity() + "</td>");
	        			out.println("<td>" + user.getCountry() + "</td>");
	        			out.println("<td>" + user.getEmail() + "</td>");
	        			out.println("<td><button class=\"btn\">Delete</button></td>"); 
	        			out.println("</tr>");
	        		}	
	        	}
	        }
	}

	private void showRegisterForm(HttpServletRequest req, HttpServletResponse res) throws IOException {
    	 PrintWriter out = res.getWriter();
    	 out.println("<form action=\"/register\" method=\"post\">\r\n" + 
    			"  <div class=\"container\">\r\n" + 
    			"    <h1>Register</h1>\r\n" + 
    			"\r\n" + 
    			"    <label for=\"firstName\"><b>First Name</b></label>\r\n" + 
    			"    <input type=\"text\" placeholder=\"First Name\" name=\"firstName\" id=\"firstName\" required>\r\n" + 
    			"\r\n" + 
    			"    <label for=\"lastName\"><b>Last Name</b></label>\r\n" + 
    			"    <input type=\"text\" placeholder=\"Last Name\" name=\"lastName\" id=\"lastName\" required>\r\n" + 
    			"\r\n" + 
    			"    <label for=\"address\"><b>Address</b></label>\r\n" + 
    			"    <input type=\"text\" placeholder=\"Address\" name=\"address\" id=\"addres\" required>\r\n" + 
    			"\r\n" + 
    			"    <label for=\"city\"><b>City</b></label>\r\n" + 
    			"    <input type=\"text\" placeholder=\"City\" name=\"city\" id=\"city\" required>\r\n" + 
    			"\r\n" + 
    			"    <label for=\"country\"><b>Country</b></label>\r\n" + 
    			" <select name=\"country\" id=\"country\">\r\n" + 
    			"                <option value=\"United Kingdom\">United Kingdom</option>\r\n" + 
    			"                <option value=\"Germany\">Germany</option>\r\n" + 
    			"                <option value=\"France\">France</option>\r\n" + 
    			"                <option value=\"United States\">United States</option>\r\n" + 
    			"                <option value=\"Canada\">Canada</option>\r\n" + 
    			"                <option value=\"China\">China</option>\r\n" + 
    			"                <option value=\"Japan\">Japan</option>\r\n" + 
    			" </select required>\r\n" +
    			"\r\n" +
    			"    <label for=\"email\"><b>Email</b></label>\r\n" + 
    			"    <input type=\"email\" placeholder=\"Email\" name=\"email\" id=\"email\" required>\r\n" + 
    			"\r\n" + 
    			"    <label for=\"password\"><b>Password</b></label>\r\n" + 
    			"    <input type=\"password\" placeholder=\"Password\" name=\"password\" id=\"password\" required>\r\n" + 
    			"    <hr>\r\n" +  
    			"    <button type=\"submit\" class=\"btn-register\">Register</button>\r\n" + 
    			"  </div>\r\n" + 
    			"</form>\r\n" + 
    			"");
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	 res.setContentType("text/html");  
	        PrintWriter pw=res.getWriter();  

	        String name=req.getParameter("name");//will return value  
	        pw.println("Welcome 1332 "+name);  
	        pw.close(); 
     /*   String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String emailId = req.getParameter("emailId");
        String password = req.getParameter("password");

        resp.setContentType("text/html");
        PrintWriter printWriter = resp.getWriter();
        printWriter.print("<html>");
        printWriter.print("<body>");
        printWriter.print("<h1>Student Resistration Form Data</h1>");
        printWriter.print("<p> firstName :: " + firstName + "</p>");
        printWriter.print("<p> lastName :: " + firstName + "</p>");
        printWriter.print("<p> firstName :: " + firstName + "</p>");
        printWriter.print("<p> firstName :: " + firstName + "</p>");
        printWriter.print("</body>");
        printWriter.print("</html>");
        printWriter.close();

        System.out.println("firstName :: " + firstName);
        System.out.println("lastName :: " + lastName);
        System.out.println("emailId :: " + emailId);
        System.out.println("password :: " + password);*/
   /* }*/
	 private final String showAll = "/showAll";
	 private final String register = "/register";
	 private final String delete = "/delete";

	  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    try {
			processRequest(req, resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }

	
	  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    try {
			processRequest(req, resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }

	  private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	    String path = req.getServletPath();
	    switch (path) {
	      case showAll:
	    	  resp.getWriter().print(xmlEl.getAll());
	        break;
	      case register:
	      
	        break;

	      case delete:
	        // ... call your function3
	        break;
	      default:
	        break;
	  }	
	  }
}
