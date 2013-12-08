package proj.webapp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;

import proj.model.Quiz;
import proj.model.QuizQuestion;
import proj.model.User;

public class Session implements Serializable
{
	
	private String username;
	private Game game;
	private String mode;
	private List<QuizQuestion> qquestions;
	private List<Boolean> results;
	private Boolean check;
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<QuizQuestion> getQquestions() {
		return qquestions;
	}

	public void setQquestions(List<QuizQuestion> qquestions) {
		this.qquestions = qquestions;
	}

	public List<Boolean> getResults() {
		return results;
	}

	public void setResults(List<Boolean> results) {
		this.results = results;
	}
	
	public void setResults(int idx, Boolean result) {
		this.results.set(idx, result);
	}

	public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}
}

class Game implements Serializable
{
	public Game(Quiz quiz)
	{
		this.quiz = quiz;
		this.startTime = new Date();
		this.score = 0;
		this.questionid = 0;
	}
	
	private Quiz quiz;
	private Date startTime;
	private int score;
	private int questionid;
	
	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	public Date getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}

	public int getQuestionid() {
		return questionid;
	}

	public void setQuestionid(int questionid) {
		this.questionid = questionid;
	}
}