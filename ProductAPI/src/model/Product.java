package model;

import java.sql.*;
public class Product
{ //A common method to connect to the DB
private Connection connect()
 {
 Connection con = null;
 try
 {
 Class.forName("com.mysql.jdbc.Driver");

 //Provide the correct details: DBServer/DBName, username, password
 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gb", "root", "");
 }
 catch (Exception e)
 {e.printStackTrace();}
 return con;
 }




public String insertProduct(String code, String name, String price, String qty, String desc)
 {
 String output = "";
 try
 {
 Connection con = connect();
 if (con == null)
 {return "Error while connecting to the database for adding."; }
 // create a prepared statement
 String query = " insert into product(`proID`,`proCode`,`proName`,`proPrice`,`proQty`, `proDesc`) values (?, ?, ?, ?, ?, ?)";
 PreparedStatement preparedStmt = con.prepareStatement(query);
 // binding values
 preparedStmt.setInt(1, 0);
 preparedStmt.setString(2, code);
 preparedStmt.setString(3, name);
 preparedStmt.setDouble(4, Double.parseDouble(price));
 preparedStmt.setInt(5, Integer.parseInt(qty));
 preparedStmt.setString(6, desc);
// execute the statement

 preparedStmt.execute();
 con.close();
 String newProducts = readProduct();
 output = "{\"status\":\"success\", \"data\": \"" +newProducts + "\"}";
 }
 catch (Exception e)
 {
 output = "{\"status\":\"error\", \"data\":\"Error while adding the product.\"}";
 System.err.println(e.getMessage());
 } 
 return output;
 }


public String readProduct()
 {
 String output = "";
 try
 {
 Connection con = connect();
 if (con == null)
 {return "Error while connecting to the database for reading."; }
//Prepare the html table to be displayed
output = "<table border=\"1\"><tr><th>Product Code</th><th>Product Name</th><th>Product Price</th><th>Product Quantity</th><th>Product Description</th><th>Edit</th><th>Delete</th></tr>";
String query = "select * from product";
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);
// iterate through the rows in the result set
while (rs.next())
{
String proID = Integer.toString(rs.getInt("proID"));
String proCode = rs.getString("proCode");
String proName = rs.getString("proName");
String proPrice = Double.toString(rs.getDouble("proPrice"));
String proQty = Integer.toString(rs.getInt("proQty"));
String proDesc = rs.getString("proDesc");

// Add into the html table
output += "<tr><td>"+ proCode + "</td>";
output += "<td>" + proName + "</td>";
output += "<td>" + proPrice + "</td>";
output += "<td>" + proQty + "</td>";
output += "<td>" + proDesc + "</td>";
// buttons
output += "<td><input name='btnEdit' type='button' value='Edit' "
+ "class='btnEdit btn btn-secondary' data-productid='" + proID + "'></td>"
+ "<td><input name='btnDelete' type='button' value='Delete' "
+ "class='btnDelete btn btn-danger' data-productid='" + proID + "'></td></tr>";
} 
 con.close();
 // Complete the html table
 output += "</table>";
 }
 catch (Exception e)
 {
 output = "Error while reading the product.";
 System.err.println(e.getMessage());
 }
 return output;
 }



public String updateProduct(String ID, String code, String name, String price, String qty, String desc)

 {
 String output = "";
 try
 {
 Connection con = connect();
 if (con == null)
 {return "Error while connecting to the database for editing."; }
 // create a prepared statement
 String query = "UPDATE product SET proCode=?,proName=?,proPrice=?,proQty=?,proDesc=?WHERE proID=?";
 PreparedStatement preparedStmt = con.prepareStatement(query);
 // binding values
 preparedStmt.setString(1, code);
 preparedStmt.setString(2, name);
 preparedStmt.setDouble(3, Double.parseDouble(price));
 preparedStmt.setInt(4, Integer.parseInt(qty));
 preparedStmt.setString(5, desc);
 preparedStmt.setInt(6, Integer.parseInt(ID));
 // execute the statement
 preparedStmt.execute();
 con.close();
 String newProducts = readProduct();
 output = "{\"status\":\"success\", \"data\": \"" +newProducts + "\"}";
 }
 catch (Exception e)
 {
 output = "{\"status\":\"error\", \"data\": \"Error while editing the product.\"}";
 System.err.println(e.getMessage());
 } 
 return output;
 }




public String deleteProduct(String ID)
 {
 String output = "";
 try
 {
 Connection con = connect();
 if (con == null)
 {return "Error while connecting to the database for deleting."; }
 // create a prepared statement
 String query = "delete from product where proID=?";
 PreparedStatement preparedStmt = con.prepareStatement(query);
 // binding values
 preparedStmt.setInt(1, Integer.parseInt(ID));
 // execute the statement
 preparedStmt.execute();
 con.close();
 String newProducts = readProduct();
 output = "{\"status\":\"success\", \"data\": \"" +newProducts + "\"}";
 }
 catch (Exception e)
 {
 output = "{\"status\":\"error\", \"data\":\"Error while deleting the product.\"}";
 System.err.println(e.getMessage());
 } 
 return output;
 }
} 