/*
package proj.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.amazon.aws.samplecode.travellog.aws.S3StorageManager;
import com.amazon.aws.samplecode.travellog.util.Configuration;
import com.amazon.aws.samplecode.travellog.util.StageUtils;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.spaceprogram.simplejpa.EntityManagerFactoryImpl;

public class SimpleDB
{
    private static AmazonSimpleDB sdb = null;
    
    static
    {
 
        String secretKey = "zDq9TB4S0ReeNjgOTJVlfV7hM5dT/2tsA1ujs4Se";
        String accessKey = "AKIAJS2AL67SHYRYROEA";
        
		try {
			AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey );
			sdb = new AmazonSimpleDBClient( credentials);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		if (sdb != null) {
			sdb.createDomain(new CreateDomainRequest("sdb-users"));		
		}
    }

    public static void AddUser(User user)
    {
    	List<ReplaceableItem> data = new ArrayList<ReplaceableItem>();
    	
    	ReplaceableAttribute username = new ReplaceableAttribute("username", user.getUsername(), false);
    	ReplaceableAttribute fullname = new ReplaceableAttribute("fullname", user.getFullname(), true);
    }
}
*/