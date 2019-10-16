/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kamal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * REST Web Service
 *
 * @author kulartist
 */
@Path("jobs")
public class Anju {
     
 String status; 
 Date today=new Date();
 long echoTime=today.getTime();
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
        mainObject.accumulate("Timestamp", echoTime);
    mainObject.accumulate("Countries", mainArray);
    
    
     kul.closeConnection(conn,rs,stm);
            
        } catch (Exception ex) {
            //Logger.getLogger(Kuldeeep.class.getName()).log(Level.SEVERE, null, ex);
            status=ex.getMessage();
        }
        
        if(mainObject.get("Countries").toString().equals("[]"))
        {
            mainObject.clear();
                   
             mainObject.accumulate("Status", "error");
        mainObject.accumulate("Timestamp", echoTime);
        mainObject.accumulate("Msg",  status);
       }
       
 
         return mainObject.toString();
         
    }
    
    
    
    
    
    
    
    
    
    
    

   
    
    
}
