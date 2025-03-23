import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class SupplierEdit extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        toClient.println(Utils.header("Supplier Form"));
		
		
        toClient.println("<form action='SupplierUpdate' method='GET'>");
        toClient.println("<table border='1'>");
        String idStr = req.getParameter("supplierId");
		int proveedorID=Integer.parseInt(idStr);
		System.out.println("SupplierEdit: "+ proveedorID);
        
		//------------New----------------------------------------------------------------------------------------------------
		// Use the new method to get the info form the database
		
		SupplierData proveedor = SupplierData.getSupplierInfo(connection, proveedorID);
	
		
		//---------------------------------------------------------------------------
  
		
		toClient.println("<tr><td>Id</td>");
        toClient.println("<td><input name='supplierId' value='" + proveedor.supplierId + "'></td></tr>");
        toClient.println("<tr><td>Name</td>");
     
        toClient.println("<td><input name='supplierName' value='" + proveedor.contactName + "'></td></tr>");
        toClient.println("<tr><td>City</td>");
        toClient.println("<td><input name='City' value='" + proveedor.city + "'></td>");
        toClient.println("<tr><td>Country</td>");
        toClient.println("<td><input name='Country' value='" + proveedor.country + "'></td>");
        toClient.println("</tr>");
		
		
        toClient.println("</table>");
        toClient.println("<input type='submit'>");
        toClient.println("</form>");
        toClient.println(Utils.footer("Supplier Form"));
        toClient.close();
    }
}