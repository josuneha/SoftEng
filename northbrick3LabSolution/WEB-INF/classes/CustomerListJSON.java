import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class CustomerListJSON extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }
	
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
		String country = req.getParameter("country");
        toClient.println(Utils.header("List of Customers of " + country));
        toClient.print("<div id='list'></div>");
        toClient.print("<script>data=[");
        Vector<CustomerData> customerList = CustomerData.getCustomerList(connection, country);
        for(int i=0; i< customerList.size(); i++){
                CustomerData customer = customerList.elementAt(i);
                if (i!=0) {
                    toClient.print(",");
                    }
                toClient.print("{");
                toClient.print("\"CustomerID\":\"" + customer.CustomerID + "\"");
                toClient.print(",\"CompanyName\":\"" + customer.CompanyName + "\"");
                toClient.print(",\"City\":\"" + customer.City + "\"");
                toClient.print("}");
        }

        toClient.println("]</script>");
        toClient.println("<script src='createTable.js'></script>");
        toClient.println(Utils.footer("CustomersJSON"));
        toClient.close();
    }
}