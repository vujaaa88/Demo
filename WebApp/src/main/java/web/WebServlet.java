package web;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;
import service.XMLService;

/**
 * Servlet implementation class WebServlet
 */
public class WebServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private XMLService service = new XMLService();

  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    String action = req.getServletPath();
    try {
      switch (action) {
      case "/data":
        showAll(req, resp);
        break;
      case "/add":
        addUser(req, resp);
        break;
      case "/delete":
        deleteUser(req, resp);
        break;
      default:
    	  welcome(req, resp);
    	  break;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  private void welcome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    RequestDispatcher requestDispatcher = req.getRequestDispatcher("/index.html");
    requestDispatcher.forward(req, resp);
  }
  private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    String email = req.getParameter("email");
    User user = service.findUserByEmail(email);
    if (user != null) {
      service.deleteUser(user);
    }
    resp.sendRedirect("/WebApp");
  }

  private void showAll(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    resp.getWriter().print(service.getJsonData());
  }

  private void addUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    User user = getUser(req, resp);
    service.addUser(user);
    resp.sendRedirect("/WebApp");
  }

  private User getUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
    String firstName = req.getParameter("firstName");
    String lastName = req.getParameter("lastName");
    String address = req.getParameter("address");
    String city = req.getParameter("city");
    String country = req.getParameter("country");
    String email = req.getParameter("email");
    String password = req.getParameter("password");

    User user = service.getUser(firstName, lastName, address, city, country, email, password);
    return user;
  }
}