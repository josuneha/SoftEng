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
