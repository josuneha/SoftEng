# Solution Exercise 1



# Product edition
We want to include further information in the  ProductEdit Servlet such as :  CategoryId, QuantityPerUnit, UnitsInStock and UnitsOnOrder. To do so, you should include these fields as attributes in ProductData class, in addition to add a new constructor and a new method to get this information from the database. 
Once you have made changes in this class, you should make changes in ProductEdit servlet so that you can show in the table the new fields: CategoryId, QuantityPerUnit, UnitsInStock and UnitsOnOrder.
If you want to update some of these values in the database, the changes will be saved in the servlet ProductUpdate.java.


This is a guide to understand what you need to do in this exercise. 
If you start the application and go to ProductList Servlet, you will see the list of products as you see in the following picture. 
http://localhost:8082/northbrick3LabSolution/ProductList

![List of products](https://josuneha.github.io/SoftEng/ProductListEx1.png)


If we click on Edit link, we want to see the additional info in the table: CategoryId, QuantityPerUnit, UnitsInStock and UnitsOnOrder.

![Product Information](https://josuneha.github.io/SoftEng/ProductListEx1b.png)

To do this you need to follow the following steps:
1) Add new fields/attributes in ProductData.java class:

```
public class ProductData {
    String    productId;
    String productName;
    int    supplierId;
    String companyName;
    float    unitPrice;
	
	//------New---------------
	// Add new attributes in the class
	int categoryId;
	int quantityPerUnit;
	int unitsInStock;
	int unitsOnOrder;
	
	//-------------------------------------------
	

```
2)	You need to add a new constructor in ProductData.java class to be able to use the new attributes.
Please, ADD a new constructor and not modify or replace the existing one.

```
ProductData (String productId, String productName, int supplierId, String companyName, float unitPrice) {
        this.productId    = productId;
        this.productName  = productName;
        this.supplierId   = supplierId;
        this.companyName = companyName;
        this.unitPrice = unitPrice;
    }
	
//------New---------------
// New constructor with the new fields

ProductData (String productId, String productName, int supplierId, String companyName, float unitPrice, int catId, int quantity, int stock, int order) {
        this.productId    = productId;
        this.productName  = productName;
        this.supplierId   = supplierId;
        this.companyName = companyName;
        this.unitPrice = unitPrice;
		
	this.categoryId    = catId;
        this.quantityPerUnit  = quantity;
        this.unitsInStock   = stock;
	this.unitsOnOrder =order;
        			
    }
	
  //-------------------------------------------------------------------------------------

```

3) Add a new method to get the information of a Product. 

```
//---------------------New----------------------------------
// New method to read all the fields from the database. I have changed the name to keep both methods
	
public static ProductData getProduct2(Connection connection, String id) {
        String sql = "Select * FROM Products";
        sql += " WHERE ProductId=?";
        System.out.println("getProduct: " + sql);
        ProductData product = null;;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, id);
            ResultSet result = pstmt.executeQuery();
            if(result.next()) {
                product = new ProductData(
                result.getString("ProductId"),
                result.getString("ProductName"),
                Integer.parseInt(result.getString("SupplierId")),
                null,
                Float.parseFloat(result.getString("UnitPrice")),			
		Integer.parseInt(result.getString("CategoryId")),
		Integer.parseInt(result.getString("QuantityPerUnit")), 
		Integer.parseInt(result.getString("UnitsInStock")),
		Integer.parseInt(result.getString("UnitsOnOrder"))
					
                );
			
            }
            result.close();
            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getProduct: " + sql + " Exception: " + e);
        }
        return product;
    }
    
	//---------------------------------------------------------------------------------
```
4) In ProductEdit servlet, you should now use getProduct2 method instead of the previous one (getProduct) to get the new attributes we have added in the ProductData class.

```
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;

@SuppressWarnings("serial")
public class ProductEdit extends HttpServlet {
    Connection connection;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        connection = ConnectionUtils.getConnection(config);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException  {
        res.setContentType("text/html");
        PrintWriter toClient = res.getWriter();
        toClient.println(Utils.header("Product Form"));
        toClient.println("<form action='ProductUpdate' method='GET'>");
        toClient.println("<table border='1'>");
        String idStr = req.getParameter("id");
        
		//------------New----------------------------------------------------------------------------------------------------
		// Use the new method to get the info form the database
		//ProductData product = ProductData.getProduct(connection, idStr);
		ProductData product = ProductData.getProduct2(connection, idStr);
		System.out.println("units ProductEdit: "+ product.unitsOnOrder);
		
		
		
		//---------------------------------------------------------------------------
        
		
		
		toClient.println("<tr><td>Id</td>");
        toClient.println("<td><input name='productId' value='" + product.productId + "'></td></tr>");
        toClient.println("<tr><td>Name</td>");
        String name = product.productName;
        System.out.println("Name: " + name);
        name = name.replace("'","&#39;");
        System.out.println("Name: " + name);
        toClient.println("<td><input name='productName' value='" + name + "'></td></tr>");
        toClient.println("<tr><td>Supplier</td>");
        toClient.println("<td><input name='supplierId' value='" + product.supplierId + "'></td>");
        toClient.println("<tr><td>Price</td>");
        toClient.println("<td><input name='unitPrice' value='" + product.unitPrice + "'></td>");
        toClient.println("</tr>");
		
	//-------New----------------------------------------------------------------------------------------------------
	//New: Add new fields from the database: CategoryId, QuantityPerUnit, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued
	toClient.println("<tr><td>CategoryId</td>");
        toClient.println("<td><input name='CategoryId' value='" + product.categoryId + "'></td>");
        toClient.println("<tr><td>QuantityPerUnit</td>");
        toClient.println("<td><input name='QuantityPerUnit' value='" + product.quantityPerUnit + "'></td>");
        toClient.println("</tr>");
	toClient.println("<tr><td>UnitsInStock</td>");
        toClient.println("<td><input name='UnitsInStock' value='" + product.unitsInStock + "'></td>");
        toClient.println("<tr><td>UnitsOnOrder</td>");
        toClient.println("<td><input name='UnitsOnOrder' value='" + product.unitsOnOrder + "'></td>");
        toClient.println("</tr>");
	//---------------------------------------------------------------------------------------------------------------------
		
        toClient.println("<tr><td>Image</td>");
        toClient.println("<td><img src='http://northbrick1.appspot.com/images/" + product.productId + ".png'></td>");
        toClient.println("</tr>");
        toClient.println("</table>");
        toClient.println("<input type='submit'>");
        toClient.println("</form>");
        toClient.println(Utils.footer("Product Form"));
        toClient.close();
    }
}

```
5) Now, if you want to update the fields of the product so that the changes are updated in the database, you should create a new method similar to updataProduct in ProductData class. Please, try to do it!!
