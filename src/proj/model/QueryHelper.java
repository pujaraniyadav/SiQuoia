package proj.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import com.spaceprogram.simplejpa.EntityManagerFactoryImpl;

public class QueryHelper
{
    private static Map<String, String> properties = new HashMap<String, String>();
    
    static
    {
        properties.put("lobBucketName", S3StorageManager.getKey().toLowerCase() + "-quiz"
                        + StageUtils.getResourceSuffixForCurrentStage());
 
        Configuration config = Configuration.getInstance();
        if ( config.getServiceEndpoint(Configuration.S3_ENDPOINT_KEY) != null ) {
            properties.put("s3endpoint", config.getServiceEndpoint(Configuration.S3_ENDPOINT_KEY));
        }
        
        if ( config.getServiceEndpoint(Configuration.SIMPLE_DB_ENDPOINT_KEY) != null ) {
            properties.put("sdbEndpoint", config.getServiceEndpoint(Configuration.SIMPLE_DB_ENDPOINT_KEY));
        }
    }

    private static EntityManagerFactoryImpl factory = new EntityManagerFactoryImpl("Quiz"
            							+ StageUtils.getResourceSuffixForCurrentStage(), properties);

    //
    // Categories
    //
    public static void GetCategories(List<String> categories, List<String> c0, List<String> c1, List<String> c2)
    {
    	String c0val = null;
    	String c1val = null;
    	
    	for (String s : categories) {
    		String[] tokens = s.split("\\.");
    		
    		System.out.println(s + " .. " + tokens.length);
    		
    		if (tokens.length != 3) continue;
    	
    		if (c0val == null) {
    			c0val = tokens[0];
    		}
    		
    		if (!c0.contains(tokens[0])) c0.add(tokens[0]);
    		
    		if (c1val == null) {
    			c1val = tokens[1];
    		}
    		
    		if (tokens[0].equals(c0val))
    			if (!c1.contains(tokens[1])) c1.add(tokens[1]);
    		if (tokens[1].equals(c1val))
    			if (!c2.contains(tokens[2])) c2.add(tokens[2]);
    	}
    }

    public static User GetUser(String username)
    {
        EntityManager em = null;

        try {
            em = factory.createEntityManager();
            Query query = em.createQuery("select user from proj.model.User u where u.username=:username");
            query.setParameter("username", username);
            return (User) query.getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
        finally {
            if (em!=null) {
                em.close();
            }
        }
    }

    public static List<User> GetAllUser()
    {
        EntityManager em = null;

        try {
            em = factory.createEntityManager();
            Query query = em.createQuery("select user from proj.model.User");
            return query.getResultList();
        }
        catch (NoResultException e) {
            return null;
        }
        finally {
            if (em!=null) {
                em.close();
            }
        }
    }

    public static void Save(User user)
    {
        EntityManager em = null;

        try {
            em = factory.createEntityManager();
            em.persist(user);
        }
        finally {
            if (em!=null) {
                em.close();
            }
        }
    }
    
    
    //
    // Question
    //
    public static String InsertQuestion(Question q)
    {
        EntityManager em = null;

        try {
            em = factory.createEntityManager();
            em.persist(q);
        }
        finally {
            if (em!=null) {
                em.close();
            }
        }
        
        return q.getId();
    }
    
    public static Question GetQuestion(String id)
    {
    	System.out.println(id);
    	
        EntityManager em = null;

        try {
            em = factory.createEntityManager();
            Query query = em.createQuery("select q from proj.model.Question q where q.id=:id");
            query.setParameter("id", id);
            return (Question) query.getSingleResult();
        }
        finally {
            if (em!=null) {
                em.close();
            }
        }
    }
    //
    // Quiz
    //
    public static String SaveQuiz(Quiz quiz)
    {
        EntityManager em = null;

        try {
            em = factory.createEntityManager();
            em.persist(quiz);
        }
        finally {
            if (em!=null) {
                em.close();
            }
        }
        
        return quiz.getId();    	
    }

    public static Quiz GetQuiz(String id)
    {
        EntityManager em = null;

        try {
            em = factory.createEntityManager();
            Query query = em.createQuery("select q from proj.model.Quiz q where q.id=:id");
            query.setParameter("id", id);
            return (Quiz) query.getSingleResult();
        }
        finally {
            if (em!=null) {
                em.close();
            }
        }
    }

    //
    // UserQuiz
    //
    public static String Save(UserQuiz uq)
    {
        EntityManager em = null;

        try {
            em = factory.createEntityManager();
            em.persist(uq);
        }
        finally {
            if (em!=null) {
                em.close();
            }
        }
        
        return uq.getId();    	
    }
    
    public static UserQuiz LookupByUserid(final String quizid, final String userid)
    {
        EntityManager em = null;

        try {
            em = factory.createEntityManager();
            Query query = em.createQuery("select q from proj.model.UserQuiz uq where uq.userid=:userid and uq.quizid=:quizid");
            query.setParameter("userid", userid);
            query.setParameter("quizid", quizid);
            
            return (UserQuiz) query.getSingleResult();   
        } catch (NoResultException e) {
        	return null;
        } finally {
            if (em!=null) {
                em.close();
            }
        }
    }

    //
    // QuizQuestion
    //
    public static String Save(QuizQuestion qq)
    {
        EntityManager em = null;

        try {
            em = factory.createEntityManager();
            em.persist(qq);
        }
        finally {
            if (em!=null) {
                em.close();
            }
        }
        
        return qq.getId();    	
    }
    
    public static List<QuizQuestion> GetQuestions(String quizid)
    {
        EntityManager em = null;

        try {
            em = factory.createEntityManager();
            Query query = em.createQuery("select qq from proj.model.QuizQuestion q where q.quizid=:quizid");
            query.setParameter("quizid", quizid);
            return query.getResultList();
        }
        finally {
            if (em!=null) {
                em.close();
            }
        }
    }
    
    public static List<UserQuiz> GetQuizes(User user)
    {
        EntityManager em = null;

        try {
            em = factory.createEntityManager();
            Query query = em.createQuery("select uq from proj.model.UserQuiz uq where u.userid=:userid");
            query.setParameter("userid", user.getId());
            return query.getResultList();
        }
        finally {
            if (em!=null) {
                em.close();
            }
        }
    }
    
    //
    // QuizScore
    //
    public static String Save(QuizScore qs)
    {
        EntityManager em = null;

        try {
            em = factory.createEntityManager();
            em.persist(qs);
        }
        finally {
            if (em!=null) {
                em.close();
            }
        }
        
        return qs.getId();    	
    }
    
    public static List<QuizScore> GetQuizScores(String userid)
    {
        EntityManager em = null;

        try {
            em = factory.createEntityManager();
            Query query = em.createQuery("select qs from proj.model.QuizScore qs where qs.userid=:userid");
            query.setParameter("userid", userid);
            return query.getResultList();
        }
        finally {
            if (em!=null) {
                em.close();
            }
        }
    }

    public static QuizScore GetMaxQuizScore(String quizid)
    {
        EntityManager em = null;

        try {
            em = factory.createEntityManager();
            Query query = em.createQuery("select qs from proj.model.QuizScore qs where qs.quizid=:quizid");
            query.setParameter("quizid", quizid);
            
            List<QuizScore> qss = query.getResultList();
            
            assert ! qss.isEmpty();
            
            QuizScore ret = qss.get(0);
            
            for (QuizScore qs : qss) {
            	if (ret.getScore() < qs.getScore()) {
            		ret = qs;
            	}
            }
            
            return ret;
            
        }
        finally {
            if (em!=null) {
                em.close();
            }
        }
    }
    
    public static List<Quiz> GetAllQuizes()
    {
        EntityManager em = null;

        try {
            em = factory.createEntityManager();
            Query query = em.createQuery("select q from proj.model.Quiz q");
            
            return query.getResultList();   
        }
        finally {
            if (em!=null) {
                em.close();
            }
        }
    }
    
    //
    // SubmitQuestion
    //
    public static String Save(SubmitQuestion q)
    {
        EntityManager em = null;

        try {
            em = factory.createEntityManager();
            em.persist(q);
        }
        finally {
            if (em!=null) {
                em.close();
            }
        }
        
        return q.getId();    	
    }
}