/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kamal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * REST Web Service
 *
 * @author kulartist
 */
@Path("regions")
public class KamalResource {

 String msg; 
 Date today=new Date();
 long echoTime=today.getTime();
 JSONArray mainArray=new JSONArray();
 JSONObject mainObject = new JSONObject();
 
  Kuldeeep kul=new Kuldeeep();
 
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Kamal
     */
    public KamalResource() {
    }

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    
    
    
    
    
    
    
        @GET
            @Path("getregionlist")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJobsList() {
        
        
        Connection conn = null;
              conn=  kul.getConnection(conn);
              JSONObject singleRegion=new JSONObject();
         
         
        try {
                   String sql;
                sql = "SELECT REGION_ID ,REGION_NAME FROM REGIONS";
    
             PreparedStatement stm = conn.prepareStatement(sql);
                ResultSet rs=stm.executeQuery();
           
            
       
    
    while(rs.next()){
//Retrieve by column name
    int region_id = rs.getInt("REGION_ID");
    
    String region_name = rs.getString("REGION_NAME");

//Display values

     
     singleRegion.accumulate("REGION_ID",region_id );
        singleRegion.accumulate("REGION_NAME", region_name);
      
        mainArray.add(singleRegion);
        singleRegion.clear();

    }
     mainObject.accumulate("status", "ok");
        mainObject.accumulate("Timestamp", echoTime);
    mainObject.accumulate("Regions", mainArray);
    
    
     kul.closeConnection(conn,rs,stm);
            
        } catch (Exception ex) {
            //Logger.getLogger(Kuldeeep.class.getName()).log(Level.SEVERE, null, ex);
            msg=ex.getMessage();
        }
        
        if(mainObject.get("Regions").toString().equals("[]"))
        {
            mainObject.clear();
                   
             mainObject.accumulate("Status", "error");
        mainObject.accumulate("Timestamp", echoTime);
        mainObject.accumulate("Msg",  msg);
       }
       

         return mainObject.toString();
         
    }
    
    
    
    
    
      @GET
            @Path("getSingleRegion&{region_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSingleJob(@PathParam("region_id") String r_id) {


        JSONObject singleRegion =new JSONObject();
        mainObject.clear();
        mainArray.clear();


        Connection conn = null;
              conn=  kul.getConnection(conn);

        try {          



              String sql;
    sql = "SELECT REGION_ID,REGION_NAME From REGIONS where REGION_ID =?";



      PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1,r_id);

                ResultSet rs=stm.executeQuery();

                while(rs.next()) {
    int  region_id = rs.getInt("REGION_ID");
    String region_Title = rs.getString("REGION_NAME");
   
 singleRegion.accumulate("REGION_ID", region_id);
        singleRegion.accumulate("REGION_NAME", region_Title);
     

    }

        }
        catch (SQLException ex) {
                       msg=ex.getMessage();

                    } catch (Exception ex) {
              msg=ex.getMessage();
          }

        if(singleRegion.toString().equals("{}"))
        {
              if(msg==null)
               msg="Record not found";

             singleRegion.accumulate("Status", "error");
        singleRegion.accumulate("Timestamp", echoTime);
        singleRegion.accumulate("Msg",  msg);
       }

         return singleRegion.toString();

    }

     @GET
            @Path("insertRegion&{region_id}&{region_name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String insertJob(@PathParam("region_id") int region_id, @PathParam("region_name") String region_name) {

        Connection conn = null;
              conn=  kul.getConnection(conn);
              int qRes=0;

        try {           

              String sql;
    sql = "INSERT INTO REGIONS VALUES(?,?)";


      PreparedStatement stm = conn.prepareStatement(sql);
                stm.setInt(1,region_id);
                stm.setString(2,region_name);
               

                  qRes=stm.executeUpdate();
                   mainObject.accumulate("status", qRes);
                  if(qRes==1)
                  {
                   mainObject.accumulate("status", "ok");
                    mainObject.accumulate("Timestamp", echoTime);
                  mainObject.accumulate("Msg", "Sucessfully Inserted");
                  }


                  kul.closeConnection(conn,null,stm);

        } catch (SQLException ex) {
            msg=ex.getMessage();
        }

         if(qRes!=1)
        {
            mainObject.clear();
        mainObject.accumulate("Status", "error");
        mainObject.accumulate("Timestamp", echoTime);
        mainObject.accumulate("Msg",  msg);
       }

         return mainObject.toString();

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
     @GET
           @Path("updateRegions&{reg_id}&{reg_name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String updateRegions(@PathParam("reg_id") int reg_id, @PathParam("reg_name") String reg_name) {
        
        int qRes=0;
        
        Connection conn = null;
              conn=  kul.getConnection(conn);
         
         
         
        try {           
            
              String sql;
    sql = "UPDATE REGIONS set REGION_NAME=? where REGION_ID=?";
    
   
      PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1,reg_name);
                stm.setInt(2, reg_id);
               
                

                 qRes=stm.executeUpdate();
                  if(qRes==1)
                  {
                       mainObject.accumulate("status", "ok");
                    mainObject.accumulate("Timestamp", echoTime);
                  mainObject.accumulate("Msg", "Sucessfully Updated");
                  }
                 
                  kul.closeConnection(conn,null,stm);
                  
                  
        } catch (SQLException ex) {
            msg=ex.getMessage();
        }
        
        
       
         if(qRes!=1)
        {
              if(msg==null)
               msg="Record not found";
        mainObject.accumulate("Status", "error");
        mainObject.accumulate("Timestamp", echoTime);
        mainObject.accumulate("Msg",  "Not Updated - "+msg);
       }
 
         return mainObject.toString();
         
    }
    
    
    
    
    
    
    
    
    @GET
            @Path("deleteRegions&{reg_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteRegions(@PathParam("reg_id") String reg_id) {
        
        
        int qRes=0;
        Connection conn = null;
              conn=  kul.getConnection(conn);
         
        try {           
            
              String sql;
    sql = "DELETE from REGIONS where REGION_ID=?";
    

   
      PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1,reg_id);
             
                

                  qRes=stm.executeUpdate();
                  if(qRes==1)
                  {
                       mainObject.accumulate("status", "ok");
                    mainObject.accumulate("Timestamp", echoTime);
                  mainObject.accumulate("Msg", "Sucessfully Deleted");
                  }
                 
                  kul.closeConnection(conn,null,stm);
                  

    
            
        } catch (Exception ex) {
              msg=ex.getMessage();
        }
        
         
         if(qRes!=1)
        {
            if(msg==null)
               msg="Record not found";
        mainObject.accumulate("Status", "error");
        mainObject.accumulate("Timestamp", echoTime);
        mainObject.accumulate("Msg",  "Not Deleted - "+msg);
       }
        
 
         return mainObject.toString();
         
    }
    
    
    
    
    
    


  
}
