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
 * @author anju
 */
@Path("jobs")
public class Anju {
    
    
     
 String msg; 
 Date today=new Date();
 long timeStamp=today.getTime();
 JSONArray mainArray=new JSONArray();
 JSONObject mainObject = new JSONObject();
 
 Kuldeeep kul=new Kuldeeep();
        
        

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Anju
     */
    public Anju() {
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Retrieves representation of an instance of kamal.Anju
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    
    
    
   
    
    
    
    
    
    
         @GET
            @Path("insertJob&{job_id}&{job_title}&{min_sal}&{max_sal}")
    @Produces(MediaType.APPLICATION_JSON)
    public String insertJob(@PathParam("job_id") String job_id, @PathParam("job_title") String job_title, @PathParam("min_sal") int min_sal, @PathParam("max_sal") int max_sal) {
        
        Connection conn = null;
              conn=  kul.getConnection(conn);
              int qRes=0;
         
        try {           
            
              String sql;
    sql = "INSERT INTO JOBS VALUES(?,?,?,?)";
    
   
      PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1,job_id);
                stm.setString(2,job_title);
                stm.setInt(3, min_sal);
                stm.setInt(4, max_sal);

                  qRes=stm.executeUpdate();
                  if(qRes==1)
                  {
                   mainObject.accumulate("status", "ok");
                    mainObject.accumulate("Timestamp", timeStamp);
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
        mainObject.accumulate("Timestamp", timeStamp);
        mainObject.accumulate("Msg", "Not Inserted - "+ msg);
       }
         
         return mainObject.toString();
         
    }
    
    
    
    
    
    
    
    
    
    
     @GET
           @Path("updateJob&{job_id}&{job_title}&{min_sal}&{max_sal}")
    @Produces(MediaType.APPLICATION_JSON)
    public String updateJob(@PathParam("job_id") String job_id, @PathParam("job_title") String job_title, @PathParam("min_sal") int min_sal, @PathParam("max_sal") int max_sal) {
        
        int qRes=0;
        
        Connection conn = null;
              conn=  kul.getConnection(conn);
         
         
         
        try {           
            
              String sql;
    sql = "UPDATE JOBS set JOB_TITLE=?,MIN_SALARY=?,MAX_SALARY=? where JOB_ID=?";
    
   
      PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1,job_title);
                stm.setInt(2, min_sal);
                stm.setInt(3, max_sal);
                stm.setString(4,job_id);
                

                 qRes=stm.executeUpdate();
                  if(qRes==1)
                  {
                       mainObject.accumulate("status", "ok");
                    mainObject.accumulate("Timestamp", timeStamp);
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
        mainObject.accumulate("Timestamp", timeStamp);
        mainObject.accumulate("Msg",  "Not Updated - "+msg);
       }
 
         return mainObject.toString();
         
    }
    
    
    
    
    
    
    
    
    
    
    
    
    @GET
            @Path("deleteJob&{job_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteJob(@PathParam("job_id") String con_id) {
        
        
        int qRes=0;
        Connection conn = null;
              conn=  kul.getConnection(conn);
         
        try {           
            
              String sql;
    sql = "DELETE from JOBS where JOB_ID=?";
    

   
      PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1,con_id);
             
                

                  qRes=stm.executeUpdate();
                  if(qRes==1)
                  {
                       mainObject.accumulate("status", "ok");
                    mainObject.accumulate("Timestamp", timeStamp);
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
        mainObject.accumulate("Timestamp", timeStamp);
        mainObject.accumulate("Msg",  "Not Deleted - "+msg);
       }
        
 
         return mainObject.toString();
         
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
      @GET
            @Path("getJobsList")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJobsList() {
        
        
        Connection conn = null;
              conn=  kul.getConnection(conn);
              JSONObject singleJob=new JSONObject();
         
         
        try {
                   String sql;
                sql = "SELECT JOB_ID,JOB_TITLE,MIN_SALARY,MAX_SALARY FROM JOBS";
    
             PreparedStatement stm = conn.prepareStatement(sql);
                ResultSet rs=stm.executeQuery();
           
            
       
    
    while(rs.next()){
//Retrieve by column name
    String j_id = rs.getString("JOB_ID");
    String jTitle = rs.getString("JOB_TITLE");
    int min_sal = rs.getInt("MIN_SALARY");
     int max_sal = rs.getInt("MAX_SALARY");
//Display values

     
     singleJob.accumulate("JOB_ID", j_id);
        singleJob.accumulate("JOB_TITLE", jTitle);
          singleJob.accumulate("MIN_SALARY", min_sal);
          singleJob.accumulate("MAX_SALARY", max_sal);
        mainArray.add(singleJob);
        singleJob.clear();

    }
     mainObject.accumulate("status", "ok");
        mainObject.accumulate("Timestamp", timeStamp);
    mainObject.accumulate("Jobs", mainArray);
    
    
     kul.closeConnection(conn,rs,stm);
            
        } catch (Exception ex) {
            msg=ex.getMessage();
        }
        
        if(mainArray.isEmpty()||msg!=null)
        {
            mainObject.clear();
              
               if(mainArray.isEmpty() && msg==null)
                  msg="Main array is empty";
                   
             mainObject.accumulate("Status", "error");
        mainObject.accumulate("Timestamp", timeStamp);
        mainObject.accumulate("Msg",  msg);
       }
       
 
         return mainObject.toString();
         
    }
    
    
    
    
    
    
    
    
    
    
      @GET
            @Path("getSingleJob&{j_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSingleJob(@PathParam("j_id") String j_id) {
        
        
        JSONObject singleJob =new JSONObject();
        mainObject.clear();
        mainArray.clear();
        
        
        Connection conn = null;
              conn=  kul.getConnection(conn);
         
        try {          
            
            
            
              String sql;
    sql = "SELECT JOB_ID,JOB_TITLE,MIN_SALARY,MAX_SALARY FROM JOBS where JOB_ID=?";
    
   
   
      PreparedStatement stm = conn.prepareStatement(sql);
                stm.setString(1,j_id);

                ResultSet rs=stm.executeQuery();

                while(rs.next()) {
     String job_id = rs.getString("JOB_ID");
    String jTitle = rs.getString("JOB_TITLE");
    int min_sal = rs.getInt("MIN_SALARY");
     int max_sal = rs.getInt("MAX_SALARY");
//Display values
    singleJob.accumulate("status", "ok");
        singleJob.accumulate("Timestamp", timeStamp);
        singleJob.accumulate("JOB_ID", j_id);
        singleJob.accumulate("JOB_TITLE", jTitle);
          singleJob.accumulate("MIN_SALARY", min_sal);
          singleJob.accumulate("MAX_SALARY", max_sal);
     
    }

        }
        catch (SQLException ex) {
                       msg=ex.getMessage();
                      
                    } catch (Exception ex) {
              msg=ex.getMessage();
          }
 
        if(singleJob.toString().equals("{}"))
        {
              if(msg==null)
               msg="Record not found";
                   
             singleJob.accumulate("Status", "error");
        singleJob.accumulate("Timestamp", timeStamp);
        singleJob.accumulate("Msg",  msg);
       }
        
         return singleJob.toString();
         
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

   
    
    
}
