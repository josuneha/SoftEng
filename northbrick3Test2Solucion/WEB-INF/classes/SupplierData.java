import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierData {
    int    supplierId;
    String contactName;
    String    city;
	String country;
	
    
    SupplierData (int a, String b, String c) {
        this.supplierId    = a;
        this.contactName  = b;
        this.city = c;
    }
	
	 SupplierData (int a, String b, String c, String d) {
        this.supplierId    = a;
        this.contactName  = b;
        this.city = c;
		this.country=d;
    }
	
    public static Vector<SupplierData> getSupplierList(Connection connection, String pais) {
        Vector<SupplierData> vec = new Vector<SupplierData>();
		
		System. out. println("getSupplierList");
        String sql = "Select SupplierID, ContactName, City FROM Suppliers";
		sql += " WHERE Country=?";
        System.out.println("getSupplierList: " + sql);
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, pais);
            ResultSet result = pstmt.executeQuery();
            while(result.next()) {
                SupplierData supplier = new SupplierData(
                    Integer.parseInt(result.getString("SupplierID")),
                    result.getString("ContactName"),
                    result.getString("City")
                );
                vec.addElement(supplier);
            }
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getSupplierList: " + sql + " Exception: " + e);
        }
        return vec;
    }
    
    public static SupplierData getSupplierInfo(Connection connection, int id) {
        String sql = "Select ContactName, City, Country FROM Suppliers";
		sql += " WHERE SupplierID=?";
        
        System.out.println("getSupplierInfo: " + sql);
        SupplierData proveedor = null;;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
			
            ResultSet result = pstmt.executeQuery();
            if(result.next()) {
                proveedor = new SupplierData(
                    id,
                    result.getString("ContactName"),
                    result.getString("City"),
					result.getString("Country")
                );
            }
            result.close();
            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getSupplierInfo: " + sql + " Exception: " + e);
        }
        return proveedor;
    }
    
	
	
    public static int updateSupplier(Connection connection, SupplierData proveedor) {
        String sql ="UPDATE Suppliers "
            + "SET ContactName = ?, City = ?, Country= ?"
            + " WHERE SupplierID = ?";
        System.out.println("updateSupplier: " + sql);
        int n = 0;
        try {
            PreparedStatement stmtUpdate= connection.prepareStatement(sql);
            stmtUpdate.setString(1,proveedor.contactName);
            stmtUpdate.setString(2,proveedor.city);
			stmtUpdate.setString(3,proveedor.country);
			stmtUpdate.setInt(4,proveedor.supplierId);
           

            n = stmtUpdate.executeUpdate();
            stmtUpdate.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in updateSupplier: " + sql + " Exception: " + e);
        }
        return n;
    }

	 public static int insertSupplier(Connection connection, SupplierData proveedor) {
        String sql ="INSERT INTO Suppliers (ContactName, City, Country, CompanyName, ContactTitle) "
            + "VALUES (?, ?, ?, ?, ?)";
        System.out.println("insertSupplier: " + sql);
        int n = 0;
        try {
            PreparedStatement stmtUpdate= connection.prepareStatement(sql);
            stmtUpdate.setString(1,proveedor.contactName);
            stmtUpdate.setString(2,proveedor.city);
            stmtUpdate.setString(3,proveedor.country);
            stmtUpdate.setString(4,"Tokyo Traders");
            stmtUpdate.setString(5,"Marketing manager");
            n = stmtUpdate.executeUpdate();
            stmtUpdate.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in insertSupplier: " + sql + " Exception: " + e);
        }
        return n;
    }
	
	
	
}
