import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShipperData {

    int   shipperID;
    String companyName;
    String phone;
	
   
    
    ShipperData (int a, String b, String c){
        this.shipperID  = a;
        this.companyName   = b;
        this.phone = c;

    }
	
	  ShipperData (String b, String c){
        //this.shipperID  = a;
        this.companyName   = b;
        this.phone = c;

    }

public static ShipperData getShipper(Connection connection, int cod) {
       
        String sql = "Select * FROM Shippers";
        sql += " WHERE ShipperID=?";
        System.out.println("getShipper: " + sql);
		ShipperData shipper=null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, cod);
			
            ResultSet result = pstmt.executeQuery();
             if(result.next()) {
                shipper = new ShipperData(
                    cod,
                    result.getString("CompanyName"),
					result.getString("Phone")
                    
                );
            }
            result.close();
            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getShipper: " + sql + " Exception: " + e);
        }
        return shipper;
    }
	
	
	public static Vector<ShipperData> getShipperList(Connection connection) {
        Vector<ShipperData> vec = new Vector<ShipperData>();
        String sql = "Select * FROM Shippers";
        System.out.println("getShipper: " + sql);
		ShipperData shipper=null;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
           
			
            ResultSet result = pstmt.executeQuery();
             while(result.next()) {
                shipper = new ShipperData(
                    result.getInt("ShipperID"),
                    result.getString("CompanyName"),
					result.getString("Phone")
                    
                );
				vec.addElement(shipper);
            }
            result.close();
            pstmt.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in getShipper: " + sql + " Exception: " + e);
        }
        return vec;
    }
	
	
	public static int insertShipper(Connection connection, ShipperData shipper) {
        String sql ="Insert into Shippers (CompanyName, Phone) Values (?, ?);";

        System.out.println("insertShipper: " + sql);
        int n = 0;
        try {
            PreparedStatement stmtUpdate= connection.prepareStatement(sql);
            stmtUpdate.setString(1,shipper.companyName);
            stmtUpdate.setString(2,shipper.phone);
            
			
			n = stmtUpdate.executeUpdate();
            stmtUpdate.close();
        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Error in insertShipper: " + sql + " Exception: " + e);
        }
        return n;
    }
	
}