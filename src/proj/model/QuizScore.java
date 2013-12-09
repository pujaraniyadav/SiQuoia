package proj.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.sun.istack.internal.NotNull;

@Entity
public class QuizScore implements Serializable {
	
	private static final long serialVersionUID = 1629672935573849314L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	private String userid;
	private String quizid;
	private int score;
	
	//
	// get/set
	//
	public String getUserid() {
		return userid;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getQuizid() {
		return quizid;
	}
	
	public void setQuizid(String quizid) {
		this.quizid = quizid;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
}