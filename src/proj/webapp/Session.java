package proj.webapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Scope;

import proj.model.Question;
import proj.model.Quiz;
import proj.model.QuizQuestion;
import proj.model.User;

public class Session implements Serializable
{
	public Session()
	{
	}
	
	private String username;
	private Game game;
	private CreateQuiz createQuiz;
	
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

	public CreateQuiz getCreateQuiz() {
		return createQuiz;
	}

	public void setCreateQuiz(CreateQuiz createQuiz) {
		this.createQuiz = createQuiz;
	}
}

class Game implements Serializable
{
	public Game(Quiz quiz)
	{
		this.quiz = quiz;
		this.endTime = new Date();
		this.endTime.setTime(this.endTime.getTime() + (10 * 60 * 1000));
		this.score = 0;
		this.questionid = 0;
		
		this.results = new ArrayList<Boolean>(10);
		
		for (int i = 0; i < 10; ++i) {
			this.results.add(i, false);
		}
		
		this.firstTry = false;
	}
	
	private Quiz quiz;
	private Date endTime;
	private int score;
	private int questionid;
	private String mode;
	private List<QuizQuestion> qquestions;
	private List<Boolean> results;
	private Boolean firstTry;
	private List<Question> questions;
	
	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
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

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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

	public Boolean getFirstTry() {
		return firstTry;
	}

	public void setFirstTry(Boolean firstTry) {
		this.firstTry = firstTry;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	public void addQuestion(Question q)
	{
		if (this.questions == null) {
			this.questions = new LinkedList<Question>();
		}
		
		this.questions.add(q);
	}
}