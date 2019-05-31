
package model;



import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDB implements DatabaseInfo {
 //-------------------------------------------------------------------------------
    public static boolean updateUser(User s){
        try {
            Class.forName(driverName);      //define drive
            Connection con = DriverManager.getConnection(dbURL,userDB,passDB);  //conncet sql
            PreparedStatement stmt= con.prepareStatement("Update Users set fullName=?,password=?,email=?,status=?, userright=? where userid=?");
            stmt.setString(1, s.getFullName()); //excute
            stmt.setString(2, s.getPassword());
            stmt.setString(3, s.getEmail());
            stmt.setString(4, s.getStatus());
            stmt.setInt(5, s.getUserRight());
            stmt.setString(6, s.getUserID()); 
            int rc=stmt.executeUpdate();        //process
            con.close();                        //close connect
            return rc==1;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
 //-------------------------------------------------------------------------------   
    public static int doBookOrder(String uid, String bid){
        try {
            Class.forName(driverName);
            Connection con = DriverManager.getConnection(dbURL,userDB,passDB);
            CallableStatement stmt=con.prepareCall("{call MuonSach(?,?,?)}");
            stmt.setString(1,bid);
            stmt.setString(2,uid);
            stmt.registerOutParameter(3, 5);
            stmt.execute();
            int logid= stmt.getInt(3);
            con.close();
            return logid;
        } catch (Exception ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
   }
 //-------------------------------------------------------------------------------   
    public static User getUser(String uid){
        User s=null;    //define new User
        try {
            Class.forName(driverName);
            Connection con = DriverManager.getConnection(dbURL,userDB,passDB);
            Statement stmt= con.createStatement();
            ResultSet rs=stmt.executeQuery("Select fullName,email,password,status,userright from Users where userID='"+uid+"'");
            if(rs.next()){
                String fullName=rs.getString(1);
                String email=rs.getString(2);
                String password=rs.getString(3);
                String status=rs.getString(4);
                int userRight=rs.getInt(5);
                String userID=uid;
                s=new User(userID, fullName, email, password, status, userRight);   //return into s
            }
            con.close();
            return s;
        } catch (Exception ex) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
 //--------------------------------------------------------------------------------------------
    public static String addNewUser(String fName, String email, String pass, String status, String uRight){
        try{
            Class.forName(driverName);
            Connection con = DriverManager.getConnection(dbURL, userDB, passDB);
            PreparedStatement stmt = con.prepareStatement("Insert into Users set fullName=?, email=?, password=?, status=?,userright=?"); //????
            
            stmt.setString(1, fName);
            stmt.setString(2, email);
            stmt.setString(3, pass);
            stmt.setString(4, status);
            stmt.setString(5, uRight);
            int rc = stmt.executeUpdate();
            
            con.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
 //--------------------------------------------------------------------------------------------   

}
