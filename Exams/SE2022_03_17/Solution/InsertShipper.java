import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class InsertShipper extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

		 
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        
		
		ShipperData shipper = new ShipperData(req.getParameter("company"),
                    req.getParameter("phone")
                 
                    );
        
		
		int n = ShipperData.insertShipper(connection, shipper);

		if (n!=0){
			System.out. println("Shipper insertado");
			//res.sendRedirect("ShipperList.html" );
			//res.sendRedirect("InsertNewCustomer.html");
		}
		else {
			System.out. println("NO insertado");
		}
		
		res.sendRedirect("ShipperList.html" );
		
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();

        toClient.println(Utils.header("Shipper insert"));
       
        toClient.println(Utils.footer("Shipper insert"));
        toClient.close();
    }
}