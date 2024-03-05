import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class ShippersList extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        String codigo = req.getParameter("cod");
		
		System.out.println("ShipperList: "+ codigo);

        toClient.println(Utils.header("Shippers"));
        toClient.println("<table border='1'>");
		
		toClient.println("<tr>");
		toClient.println("<td>Id</td>");
		toClient.println("<td>CompanyName</td>");
		toClient.println("<td>Phone</td>");
        toClient.println("</tr>");
		
		Vector<ShipperData> shipperList=new Vector<ShipperData>();
		
		ShipperData shipper;
		 if (codigo != null) {
           shipper = ShipperData.getShipper(connection, Integer.parseInt(codigo));
		   shipperList.add(shipper);
		   
        } else {
            shipperList = ShipperData.getShipperList(connection);
        }
		
 
        
       for(int i=0; i< shipperList.size(); i++){
				shipper=shipperList.elementAt(i);;
                toClient.println("<tr>");
                toClient.println("<td>" + shipper.shipperID + " </td>");
                toClient.println("<td>" + shipper.companyName + " </td>");
                toClient.println("<td>" + shipper.phone + " </td>");
                
                toClient.println("</tr>");
	   }

        toClient.println("</table>");
        toClient.println(Utils.footer("Shippers"));
        toClient.close();
    }
}