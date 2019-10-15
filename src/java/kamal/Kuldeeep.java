/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kamal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * REST Web Service
 *
 * @author 1895270
 */
@Path("country")
public class Kuldeeep {

    @Context
    private UriInfo context;
    
    
    
    // JDBC driver name and database URL
 String JDBC_DRIVER = "oracle.jdbc.OracleDriver";
static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:XE";
// Database credentials
static final String USER = "hr";
static final String PASS = "cegepgim";
Statement stm=null;

String status; 


 Date today=new Date();
        long echoTime=today.getTime();
 JSONArray mainArray=new JSONArray();
        JSONObject mainObject = new JSONObject();
    
    
    

    /**
     * Creates a new instance of Kuldeeep
     */
    public Kuldeeep() {
    }
    
    
   
    public Connection getConnection(Connection conn) {
        
        
     String classs = "oracle.jdbc.OracleDriver";

    //a@gmail.com

    String url = "jdbc:oracle:thin:@//144.217.163.57:1521";
    String un = "hr";
    String password = "inf5180";
      String name = " ";
      JSONArray mainArray=new JSONArray();
        JSONObject mainObject = new JSONObject();

    

        
        String ConnURL = null;
         {

           

        try {
             Class.forName(classs);
            conn = DriverManager.getConnection(url, un, password);
            System.out.println("Success");
        } catch (SQLException ex) {
            //Logger.getLogger(ShapesAny.class.getName()).log(Level.SEVERE, null, ex);
            //return ex.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kuldeeep.class.getName()).log(Level.SEVERE, null, ex);
           // return ex.getMessage();
        }


        }
         return conn;
        
    }
    
    
    
    
     public void closeConnection(Connection conn, ResultSet rs,PreparedStatement ps )  {
         
         
        try {
            if(!conn.equals(null))
                conn.close();
            if(!rs.equals(null))
                rs.close();
            if(!ps.equals(null))
                ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(Kuldeeep.class.getName()).log(Level.SEVERE, null, ex);
        }
         
     }
    
    
    
    
    
    
    
              @GET
            @Path("getCountryList")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCountryList() {
        
        
        
        Connection conn = null;
              conn=  getConnection(conn);
              JSONObject singleCountry=new JSONObject();
         
         
         
        try {
            
            
                   String sql;
                sql = "SELECT COUNTRY_ID,COUNTRY_NAME,REGION_ID FROM COUNTRIES";
    
             PreparedStatement stm = conn.prepareStatement(sql);
                

                ResultSet rs=stm.executeQuery();
           
            
       
    
    while(rs.next()){
//Retrieve by column name
    String c_id = rs.getString("COUNTRY_ID");
    String cName = rs.getString("COUNTRY_NAME");
    int r_id = rs.getInt("REGION_ID");
//Display values

     
     singleCountry.accumulate("COUNTRY_ID", c_id);
        singleCountry.accumulate("COUNTRY_NAME", cName);
          singleCountry.accumulate("REGION_ID", r_id);
        mainArray.add(singleCountry);
        singleCountry.clear();

    }
    
     mainObject.accumulate("status", "ok");
        mainObject.accumulate("Timestamp", echoTime);
    mainObject.accumulate("Countries", mainArray);
    
    
            
        } catch (Exception ex) {
            //Logger.getLogger(Kuldeeep.class.getName()).log(Level.SEVERE, null, ex);
            status=ex.getMessage();
        }
        
       //  if(mainObject.equals("{}"))
        {
                   
             mainObject.accumulate("Status", "error");
        mainObject.accumulate("Timestamp", echoTime);
        mainObject.accumulate("Msg",  status);
       }
 
         return mainObject.toString();
         
    }
    
    
    
    
    
    
    
    
    
    
    
      @GET
            @Path("getCountrySingle&{c_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCountrySingle(@PathParam("c_id") String c_id) {
        
        
        
        mainObject.clear();
        mainArray.clear();
        
        
        Connection conn = null;
              conn=  getConnection(conn);
         
        try {          
            
              String sql;
    sql = "SELECT COUNTRY_ID,COUNTRY_NAME,REGION_ID FROM COUNTRIES where COUNTRY_ID=?";
    
   
   
      PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1,c_id);

                ResultSet rs=stm.executeQuery();

                while(rs.next()) {
    String cn_id = rs.getString("COUNTRY_ID");
    String cName = rs.getString("COUNTRY_NAME");
    int r_id = rs.getInt("REGION_ID");
//Display values
 mainObject.accumulate("status", "ok");
        mainObject.accumulate("Timestamp", echoTime);
     mainObject.accumulate("COUNTRY_ID", cn_id);
        mainObject.accumulate("COUNTRY_NAME", cName);
          mainObject.accumulate("REGION_ID", r_id);
     
    }
            
        } catch (Exception ex) {
            status=" - "+ex.getMessage();
        }
 
        if(mainObject.toString().equals("{}"))
        {
                   
             mainObject.accumulate("Status", "error");
        mainObject.accumulate("Timestamp", echoTime);
        mainObject.accumulate("Msg",  status);
       }
        
         return mainObject.toString();
         
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
     
      @GET
            @Path("insertCountry&{con_id}&{con_name}&{reg_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String insertCountry(@PathParam("con_id") String con_id, @PathParam("con_name") String con_name, @PathParam("reg_id") int reg_id) {
        
        Connection conn = null;
              conn=  getConnection(conn);
              int qRes=0;
         
        try {           
            
              String sql;
    sql = "INSERT INTO COUNTRIES VALUES(?,?,?)";
    
   //jjjjj
   
      PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1,con_id);
                stm.setString(2,con_name);
                stm.setInt(3, reg_id);

                  qRes=stm.executeUpdate();
                   mainObject.accumulate("status", "ok");
        mainObject.accumulate("Timestamp", echoTime);
                  mainObject.accumulate("Msg", "Sucessfully Inserted");

    
            
        } catch (SQLException ex) {
            status=ex.getMessage();
        }
        
         if(qRes!=1)
        {
        mainObject.accumulate("Status", "error");
        mainObject.accumulate("Timestamp", echoTime);
        mainObject.accumulate("Msg",  status);
       }
         
       
 
         return mainObject.toString();
         
    }
    
    
    
    
    
    
      @GET
            @Path("updateCountry&{con_id}&{con_name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String updateCountry(@PathParam("con_id") String con_id, @PathParam("con_name") String con_name) {
        
        int qRes=0;
        
        Connection conn = null;
              conn=  getConnection(conn);
         
         
         
        try {           
            
              String sql;
    sql = "UPDATE COUNTRIES set COUNTRY_NAME=? where COUNTRY_ID=?";
    
   //jjjjj
   
      PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(2,con_id);
                stm.setString(1,con_name);
                

                 qRes=stm.executeUpdate();

    
            
        } catch (SQLException ex) {
            status=ex.getMessage();
        }
        
        
       
         if(qRes!=1)
        {
        mainObject.accumulate("Status", "error");
        mainObject.accumulate("Timestamp", echoTime);
        mainObject.accumulate("Msg",  status);
       }
 
         return mainObject.toString();
         
    }
    
    
    
    
    
    
    
    
    
    
    
    
     @GET
            @Path("deleteCountry&{con_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteCountry(@PathParam("con_id") String con_id) {
        
        
        int qRes=0;
        Connection conn = null;
              conn=  getConnection(conn);
         
         
         
        try {           
            
              String sql;
    sql = "DELETE from COUNTRIES where COUNTRY_ID=?";
    
   //jjjjj
   
      PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1,con_id);
             
                

                  int a=stm.executeUpdate();
                  mainObject.accumulate("stat", a);

    
            
        } catch (Exception ex) {
            //Logger.getLogger(Kuldeeep.class.getName()).log(Level.SEVERE, null, ex);
              status=ex.getMessage();
        }
        
         
         if(qRes!=1)
        {
        mainObject.accumulate("Status", "error");
        mainObject.accumulate("Timestamp", echoTime);
        mainObject.accumulate("Msg",  status);
       }
        
 
         return mainObject.toString();
         
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    /**
     * Retrieves representation of an instance of kamal.Kuldeeep
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of Kuldeeep
     * @param content representation for the resource
     */
   
}
