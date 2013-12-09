package proj.webapp; 

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import edu.emory.mathcs.backport.java.util.Arrays;
import proj.model.*;

@Controller
@SessionAttributes("session")
public class WebController {

    private static final Logger logger=Logger.getLogger(WebController.class.getName());
    
    //
    // testonly
    //
    @RequestMapping(value="create-db-testonly.q")
    public ModelAndView CreateDBTestonly(ModelMap map)
    {
    	System.out.println("Crating sample quiz for testing");
    	
    	//
    	// Init quiz
    	//
       	Quiz quiz = new Quiz();
       	
    	quiz.setName("SampleQuiz");
    	quiz.setCategory("Science");
    	QueryHelper.SaveQuiz(quiz);
    	
    	//
    	// Init question
    	//
    	for (int i = 0; i < 10; ++i) {
    		Question q = new Question();
    		
    		q.setText("Question " + i);
    		
    		String answers = "a0,a1,a2,a3";
    		
    		q.setAnswers(answers);
    		q.setAnswer(1);
    		 		
    		QueryHelper.InsertQuestion(q);
    		
    		QuizQuestion qq = new QuizQuestion();
    		
    		qq.setQuizid(quiz.getId());
    		qq.setQuestionid(q.getId());
    		
    		QueryHelper.Save(qq);
    	}
    	
    	//
    	// Add to all users
    	//
    	List<User> users = QueryHelper.GetAllUser();
    	
    	for (User user : users) {
    		System.out.println("Adding quiz to user " + user.getId());

    		UserQuiz uq = new UserQuiz();
    		
    		uq.setUserid(user.getId());
    		uq.setQuizid(quiz.getId());
    		
    		QueryHelper.Save(uq);
    	}
    	
    	return new ModelAndView("redirect:login.q", map);
    }
    
    //
    // Notification
    //
    public ModelAndView Notify(String msgheader, String msgbody, String msgbutton, String msghref)
    {
    	ModelMap map = new ModelMap();
    	map.addAttribute("msgheader", msgheader);
    	map.addAttribute("msgbody", msgbody);
    	map.addAttribute("msgbutton", msgbutton);
    	map.addAttribute("msghref", msghref);
    
    	return new ModelAndView("quiz-notification", map);
    }
    
    //
    // Error
    // 
    @RequestMapping("error.q")
    public ModelAndView Error(ModelMap map)
    {
    	return new ModelAndView("quiz-error", map);
    }
    
    //
    // Logout
    //
    @RequestMapping("logout.q")
    public ModelAndView Logout(ModelMap map)
    {
    	map.addAttribute("session", new Session());
    	return Notify("Logout !", "You are logged out.", "Login", "login.q");
    }
    
    //
    // Signup
    //
    @RequestMapping(value="signup.q")
    public ModelAndView Signup(ModelMap model) 
    {
    	model.addAttribute("session", new Session());
    	model.addAttribute("user", new User());
    	model.addAttribute("error", false);
    	
    	return new ModelAndView("quiz-signup");
    }

    @RequestMapping("createAccount.q")
    public ModelAndView doCreateAccount (User user, BindingResult result, ModelMap map, @RequestParam("password2") String password) {    	
    	if (user.getUsername().isEmpty() || user.getFullname().isEmpty() || user.getEmail().isEmpty()) {
    		result.reject("*", "Please fill all mandatory fields");
    	}

    	if (user.getPassword() == null || user.getPassword().isEmpty() || !user.getPassword().equals(password)) {
    		result.reject("password", "password is not valid or did not match");
    	}
    	
    	if (result.hasErrors()) {
    		// result.reject("error", "error validating");
    		return new ModelAndView("quiz-signup", map);
    	}
    	
    	if (QueryHelper.GetUser(user.getUsername()) != null) {
    		result.reject("username", "user name already exists");
    	}
    	  	
    	if (result.hasErrors()) {
    		// result.reject("error", "error validating");
    		return new ModelAndView("quiz-signup", map);
    	}
    	
    	user.setPoints(0);
    	QueryHelper.Save(user);
    	
    	return Notify("Success !", "User was successfully signed up.", "Goto Login", "login.q");
    }
    
    //
    // Login
    //
    @RequestMapping(value="login.q")
    public ModelAndView Login(ModelMap map) 
    {
    	map.addAttribute("session", new Session());
    	map.addAttribute("user", new User());
    	map.addAttribute("error", false);
    	
    	return new ModelAndView("quiz-login", map);
    }

    @RequestMapping(value="submit-login.q")
    public ModelAndView Login(User user, BindingResult result, ModelMap map) 
    {	
    	if (result.hasErrors()) {
    		result.reject("Invalid data");
    		return new ModelAndView("quiz-login", map);
    	}
    	
    	if (user.getUsername() == null || user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
    		result.reject("*", "Please provide valid username and password");
    		return new ModelAndView("quiz-login", map);
    	}
    	
    	User u = QueryHelper.GetUser(user.getUsername()); 
    	
    	if (u == null) {
    		result.reject("username", "Üser not found");
    	}

    	if (u != null && !u.getPassword().equals(user.getPassword())) {
    		result.reject("password", "Invalid password");
    	}

    	if (result.hasErrors()) {
    		return new ModelAndView("quiz-login", map);
    	}
    	
    	//
    	// User is logged in
    	//
    	Session session = new Session();
    	session.setUsername(u.getUsername());
    	
    	map.addAttribute("session", session);
    	
    	return MakeHome(u, map);
    }

   
    
    //
    // Home
    //
    @RequestMapping(value="home.q")
    private ModelAndView MakeHome(ModelMap map)
    {
    	Session session = (Session) map.get("session");
    	
    	if (session == null || session.getUsername() == null) {
    		return Notify("Error !", "Session is invalid. Please authenticate.", "Goto Login", "login.q");
    	}
    	
    	User user = QueryHelper.GetUser(session.getUsername());
    	
    	return MakeHome(user, map);
    }
    
    private ModelAndView MakeHome(User u, ModelMap map)
    {
    	//
    	// Select categories
    	//
    	List<UserQuiz> uqs = QueryHelper.GetQuizes(u);
    	List<String> categories = new LinkedList<String>();
    	Map<String,String> games = new LinkedHashMap<String, String>();
    	List<String> modes = new LinkedList<String>();
    	
    	Boolean firstEntry = true;
    	
    	for (UserQuiz uq : uqs) {
    		Quiz q = QueryHelper.GetQuiz(uq.getQuizid());
    		categories.add(q.getCategory());
    	}
    	
    	List<String> categories0 = new LinkedList<String>();
    	List<String> categories1 = new LinkedList<String>();
    	List<String> categories2 = new LinkedList<String>();
    	
    	QueryHelper.GetCategories(categories, categories0, categories1, categories2);
    	
    	String filter = categories.isEmpty() ? "" : categories.get(0);
    	
		for (UserQuiz uq : uqs) {
			Quiz q = QueryHelper.GetQuiz(uq.getQuizid());
			if (q.getCategory().equals(filter)) {
				games.put(q.getId(), q.getName());
			}
		}
    	
    	modes.add("Normal");
    	modes.add("Learner");
    	
    	map.addAttribute("config", new HomeFormConfig());
    	map.addAttribute("games", games);
    	map.addAttribute("categories0", categories0);
    	map.addAttribute("categories1", categories1);
    	map.addAttribute("categories2", categories2);
    	map.addAttribute("modes", modes);
    	
    	return new ModelAndView("quiz-home", map);
    }
    
    //
    // Play game
    //
    @RequestMapping("submit-play.q")
    public ModelAndView submitPlay(HomeFormConfig config, BindingResult result, ModelMap map)
    {
    	Session session = (Session) map.get("session");
    	
    	if (session == null || session.getUsername() == null) {
    		return Notify("Error !", "Session is invalid. Please authenticate.", "Goto Login", "login.q");
    	}
    	
    	if (result.hasErrors()) {
    		return Notify("Error !", "Invalid dat", "Goto Login", "login.q");
    	}
    	
    	User user = QueryHelper.GetUser(session.getUsername());
    	
    	if (config.getGame().isEmpty()) {
    		result.reject("Invalid data");
    		return MakeHome(user, map);
    	}
    	
    	//
    	// Move on to playing the game
    	//
    	String gameid = config.getGame();
    	String mode = config.getMode();
    
    	//
    	// Update session
    	//  	
    	Quiz quiz = QueryHelper.GetQuiz(gameid);

    	if (session.getGame() == null) {
    		session.setGame(new Game(quiz));
    	}
    	
    	session.getGame().setMode(mode);
    	
    	ContinueGame(user, session.getGame(), map);
    	return new ModelAndView("quiz-play", map);
    }

    //
    // Category mgmt
    //
    @RequestMapping("submit-play-cat.q")
    public ModelAndView SubmitPlayCat(HomeFormConfig config, BindingResult result, ModelMap map,  @RequestParam("c") String c) throws Exception
    {
    	Session session = (Session) map.get("session");
    	
    	if (session == null || session.getUsername() == null) {
    		return Notify("Error !", "Session is invalid. Please authenticate.", "Goto Login", "login.q");
    	}

    	User user = QueryHelper.GetUser(session.getUsername());
    	List<UserQuiz> uqs = QueryHelper.GetQuizes(user);
    	
    	//
    	// Select categories
    	//    	
    	List<String> categories = new LinkedList<String>();
    	Map<String,String> games = new LinkedHashMap<String, String>();
    	List<String> modes = new LinkedList<String>();
    	
    	Boolean firstEntry = true;
 
    	String filter;
    	
    	if (c.equals("0")) {
    		filter = config.getCategory0();
    	} else if (c.equals("1")) {
    		filter = config.getCategory0() + "." + config.getCategory1();
    	} else if (c.equals("2")) {
    		filter = config.getCategory0() + "." + config.getCategory1() + "." + config.getCategory2();
    	} else {
    		throw new Exception("Unknown category");
    	}
    	
    	for (UserQuiz uq : uqs) {
    		Quiz q = QueryHelper.GetQuiz(uq.getQuizid());
    		if (q.getCategory().startsWith(filter)) {
    				System.out.println(q.getCategory());
    				categories.add(q.getCategory());
    		}
    	}
 
       	for (UserQuiz uq : uqs) {
    		Quiz q = QueryHelper.GetQuiz(uq.getQuizid());
    		if (!q.getCategory().startsWith(filter)) {
    				categories.add(q.getCategory());
    		}
    	}
 
    	List<String> categories0 = new LinkedList<String>();
    	List<String> categories1 = new LinkedList<String>();
    	List<String> categories2 = new LinkedList<String>();
    	
    	QueryHelper.GetCategories(categories, categories0, categories1, categories2);

    	String category = categories0.isEmpty() ? "" : categories.get(0);
    	
    	for (UserQuiz uq : uqs) {
    		Quiz q = QueryHelper.GetQuiz(uq.getQuizid());
    		if (q.getCategory().equals(category)) {
    				games.put(q.getId(), q.getName());
    		}
    	}

    	
    	modes.add("Normal");
    	modes.add("Learner");
    	
    	map.addAttribute("config", new HomeFormConfig());
    	map.addAttribute("games", games);
    	map.addAttribute("categories0", categories0);
    	map.addAttribute("categories1", categories1);
    	map.addAttribute("categories2", categories2);
    	map.addAttribute("modes", modes);
    	
    	return new ModelAndView("quiz-home", map);

    	
    }
    
    private void ContinueGame(User user, Game game, ModelMap map)
    {
    	Session session = (Session) map.get("session");
    	GameFormConfig config = new GameFormConfig();

    	int idx = game.getQuestionid();
    	
    	Quiz quiz = game.getQuiz();
    	List<QuizQuestion> qqs = QueryHelper.GetQuestions(quiz.getId());
    	Question q = QueryHelper.GetQuestion(qqs.get(idx).getQuestionid());
    	
    	game.addQuestion(q);
    	
    	Map<String,String> answers = new LinkedHashMap<String,String>();
    	
    	int i = 0;
    	for (String s : q.getAnswers().split(",")) {
    		answers.put("" + i, s);
    		++i;
    	}
    	
    	game.setFirstTry(true);
    	
    	long lefttime = 0;
    	if (session.getGame().getEndTime().after(new Date())) {
    		lefttime = (session.getGame().getEndTime().getTime() - new Date().getTime()) / 1000;
    	}
    	
    	map.addAttribute("timeleft", lefttime);
    	map.addAttribute("quizname", quiz.getName());
    	map.addAttribute("question", q.getText());
    	map.addAttribute("answers", answers);
    	map.addAttribute("config", config);
    	map.addAttribute("firsttry", game.getFirstTry());
    	map.addAttribute("mode", game.getMode());
    }

    @RequestMapping("quiz-play")    
    private ModelAndView QuizPlayWithOp(GameFormConfig config, BindingResult result, ModelMap map, @RequestParam("op") String op)
    {
    	Session session = (Session) map.get("session");
    	
    	if (session == null || session.getUsername() == null) {
    		return Notify("Error !", "Session is invalid. Please authenticate.", "Goto Login", "login.q");
    	}
    	
    	if (op.equals("next")) {
    		return QuizPlayNext(config, result, map, /*skip=*/ false);
    	} else if (op.equals("skip")) {
    		return QuizPlayNext(config, result, map, /*skip=*/ true);
    	} else {
    		return QuizPlayCancel(config, result, map);
    	}
    }
    
    private ModelAndView QuizPlayNext(GameFormConfig config, BindingResult result, ModelMap map, Boolean skip)
    {
    	Session session = (Session) map.get("session");
    	
    	assert session != null;
    	
    	User user = QueryHelper.GetUser(session.getUsername());
    	Game game = session.getGame();
    	
    	if (game == null) {
    		return Notify("Error !", "No game is being played.", "Goto Login", "login.q");
    	}	
    	
    	if (game.getMode() != "Learner" && game.getEndTime().getTime() <= new Date().getTime()) {
    		session.setGame(null);
    		return Notify("Times up !", "You have run out of time.", "Go Home", "home.q");
    	}
    	
    	String mode = game.getMode();
    	String answer = config.getAnswer();
    	 
    	//
    	// Process answer
    	//
		int aidx;
		try
		{
			aidx = Integer.parseInt(answer);
		}
		catch(Exception e)
		{
			aidx = -1;
		}
  
    	List<QuizQuestion> qqs = QueryHelper.GetQuestions(session.getGame().getQuiz().getId());
    	Question q = QueryHelper.GetQuestion(qqs.get(game.getQuestionid()).getQuestionid());
    	
    	if (mode.equals("Learner")) {
    		//
    		// Learner mode
    		//
    		if (!skip) {
    			//
    			// next is pressed
    			//
    			if (game.getFirstTry()) {
    				//
    				// next, learner, and first attempt at question
    				//
    				if (aidx != q.getAnswer()) {
    					map.addAttribute("error", "Wrong answer. Correct answer is option (" + (q.getAnswer() + 1) + ")");
    				} else {
    					game.getResults().set(session.getGame().getQuestionid(), true);
    					map.addAttribute("error", "Correct answer");
    				}
 
    				ContinueGame(user, game, map);
    				map.addAttribute("answer", q.getAnswer());
    				game.setFirstTry(false);
    				return new ModelAndView("quiz-play", map);
    			}
    		}
    	} else {
    		//
    		// Normal mode
    		//
    		if (!skip) {
    	    	if (!skip && aidx == -1) {
    	    		map.addAttribute("error", "Please select an answer or choose to skip");
    	    		ContinueGame(user, game, map);
    	    		return new ModelAndView("quiz-play", map);
    	    	}
    			
				if (aidx == q.getAnswer()) {
					game.getResults().set(session.getGame().getQuestionid(), true);
				}    			
    		}
    	}
    	
    	game.setQuestionid(game.getQuestionid() + 1);
    	
    	if (game.getQuestionid() >= 10) {
    		return MakeScorecard(user, game, session, map);
    	}
    	
    	ContinueGame(user, game, map);
    	return new ModelAndView("quiz-play", map);
    }
    
    private ModelAndView QuizPlayCancel(GameFormConfig config, BindingResult result, ModelMap map)
    {
    	Session session = (Session) map.get("session");
    	
    	assert session != null;
    	
    	return Notify("Hmm .. Giving up so easily !", "The quiz has been cancelled.", "Go Home", "home.q");
    }
    
    private ModelAndView MakeScorecard(User user, Game game, Session session, ModelMap map)
    {
    	Map<String, QuestionResult> results = new LinkedHashMap<String, QuestionResult>();
    	
    	int score = 0;
    	for (int i = 0; i < 10; ++i) {
    		
    		QuestionResult r = new QuestionResult();
    		
    		r.setResult(game.getResults().get(i));
    		
    		int points = game.getQuestions().get(i).getPoints();
    		
    		if (points == 1) {
    			r.setPoints("" + points + " [EASY]");
    		} else if (points == 2) {
    			r.setPoints("" + points + " [MEDIUM]");
    		} else {
    			r.setPoints("" + points + " [HARD]");
    		}
    		
    		results.put(i + "", r);
    		
    		if (game.getResults().get(i)) {
    			score += game.getQuestions().get(i).getPoints();
    		}
    	}
    	
    	map.addAttribute("results", results);
    	map.addAttribute("score", score);
    	
    	//
    	// Update quiz score
    	//
    	QuizScore quizScore = new QuizScore();
    	
    	quizScore.setUserid(user.getId());
    	quizScore.setQuizid(game.getQuiz().getId());
    	quizScore.setScore(score);
    	
    	QueryHelper.Save(quizScore);
    	
    	//
    	// Update user with points
    	//
    	user.setPoints(user.getPoints() + score);
    	
    	QueryHelper.Save(user);
    	
    	session.setGame(null);
    	
    	return new ModelAndView("quiz-scorecard", map);
    }
    
    @RequestMapping("bigscorecard.q")
    public ModelAndView submitBigScoreCard(ModelMap map)
    {
    	Session session = (Session) map.get("session");
    	
    	if (session == null || session.getUsername() == null) {
    		return Notify("Error !", "Session is invalid. Please authenticate.", "Goto Login", "login.q");
    	}
 
    	User user = QueryHelper.GetUser(session.getUsername());
    	List<QuizScore> scores = QueryHelper.GetQuizScores(user.getId());
    	
    	List<GameScore> gamescores = new ArrayList<GameScore>();
    	
    	int total = 0;
    	
    	for (QuizScore score : scores) {
    		Quiz quiz = QueryHelper.GetQuiz(score.getQuizid());
    		GameScore s = new GameScore();
    		s.setGameName(quiz.getName());
    		s.setGameScore("" + score.getScore());
    		s.setGameMaxScore("" + QueryHelper.GetMaxQuizScore(score.getQuizid()).getScore());
    		
    		total += score.getScore();
    		
    		gamescores.add(s);
    	}
    	
    	map.addAttribute("games", gamescores);
    	map.addAttribute("total", total);
    	map.addAttribute("totalCredit", user.getPoints());
    	
    	return new ModelAndView("quiz-bigscorecard", map);
    }
    
    //
    // Create Quiz
    //
    @RequestMapping(value="start-create-quiz.q")
    public ModelAndView StartCreateQuiz(ModelMap map)
    {
    	Session session = (Session) map.get("session");
    	
    	if (session == null || session.getUsername() == null) {
    		return Notify("Error !", "Session is invalid. Please authenticate.", "Goto Login", "login.q");
    	}
 
    	session.setCreateQuiz(new CreateQuiz());
    	map.addAttribute("quiz", new CreateQuizConfig());
    	
    	return new ModelAndView("quiz-createquiz", map);
    }
    
    @RequestMapping("create-quiz.q")
    public ModelAndView SubmitCreateQuiz(CreateQuizConfig quiz, BindingResult result, ModelMap map)
    {
    	Session session = (Session) map.get("session");
    	
    	if (session == null || session.getUsername() == null) {
    		return Notify("Error !", "Session is invalid. Please authenticate.", "Goto Login", "login.q");
    	}

    	if (result.hasErrors()) {
    		return new ModelAndView("quiz-createquiz", map);
    	}
    
    	if (quiz.getCategory() == null || quiz.getCategory().isEmpty() || quiz.getName() == null || quiz.getName().isEmpty()) {
    		result.reject("*", "Please fill all fields");
    	}
    	    	
    	if (result.hasErrors()) {
    		return new ModelAndView("quiz-createquiz", map);
    	}
    	
    	Quiz q = new Quiz();
    	
    	q.setName(quiz.getName());
    	q.setCategory(quiz.getCategory());
    	
    	QueryHelper.SaveQuiz(q);
    	    	
    	session.getCreateQuiz().setQuizid(q.getId());
    	session.getCreateQuiz().setQuestionNumber(1);
    	
    	map.addAttribute("number", 1);
    	map.addAttribute("question", new CreateQuestionConfig());
    	
    	return new ModelAndView("quiz-createquestion", map);
    }
    
    @RequestMapping("create-question.q")
    public ModelAndView SubmitCreateQuestion(CreateQuestionConfig config, BindingResult result, ModelMap map)
    {
    	Session session = (Session) map.get("session");
    	
    	if (session == null || session.getUsername() == null) {
    		return Notify("Error !", "Session is invalid. Please authenticate.", "Goto Login", "login.q");
    	}

    	if (result.hasErrors()) {
    		result.reject("There are errors in the form");
    		return new ModelAndView("quiz-createquestion", map);
    	}
    	    	
    	Question q = new Question();
    	
    	q.setText(config.getQuestion());
    	q.setAnswers(config.getAnswers());
    	q.setAnswer(Integer.parseInt(config.getAnswer())); 
    	q.setPoints(config.getPoints());
    	
    	System.out.println("answer = " + config.getAnswer());
    	
    	QueryHelper.InsertQuestion(q);
    	
    	QuizQuestion qq = new QuizQuestion();
    	
    	qq.setQuizid(session.getCreateQuiz().getQuizid());
    	qq.setQuestionid(q.getId());
    	
    	QueryHelper.Save(qq);
    	
    	if (session.getCreateQuiz().getQuestionNumber() >= 10) {
    		session.setCreateQuiz(null);
    		return Notify("Success !", "Quiz created.", "Go Home", "home.q");
    	}
    	
    	session.getCreateQuiz().setQuestionNumber(session.getCreateQuiz().getQuestionNumber() + 1);
    	   	
    	map.addAttribute("number", session.getCreateQuiz().getQuestionNumber());
    	map.addAttribute("question", new CreateQuestionConfig());
    	
    	return new ModelAndView("quiz-createquestion", map);
    }
    
    //
    // Buy games
    //
    @RequestMapping("buy.q")
    public ModelAndView ShowBuyGames(ModelMap map)
    {
    	Session session = (Session) map.get("session");
    	
    	if (session == null || session.getUsername() == null) {
    		return Notify("Error !", "Session is invalid. Please authenticate.", "Goto Login", "login.q");
    	}
	
    	User user = QueryHelper.GetUser(session.getUsername());
    	
    	List<Quiz> quizes = QueryHelper.GetAllQuizes();
    	Map<String, String> ret = new HashMap();
    	
    	for (Quiz q : quizes) {
    		if (QueryHelper.LookupByUserid(q.getId(), user.getId()) == null) {
    			ret.put(q.getId(), q.getCategory() + " : " + q.getName());
    		} 
    	}
    	
    	map.addAttribute("quizes", ret);
    	map.addAttribute("config", new BuyConfig());
    	
    	return new ModelAndView("quiz-buy", map);
    }
    
    @RequestMapping("submit-buy.q")
    public ModelAndView SubmitBuyGames(BuyConfig config, BindingResult result, ModelMap map)
    {
    	Session session = (Session) map.get("session");
    	
    	if (session == null || session.getUsername() == null) {
    		return Notify("Error !", "Session is invalid. Please authenticate.", "Goto Login", "login.q");
    	}
    	
    	map.addAttribute("config", config);
    	
    	if (result.hasErrors()) {
    		map.addAttribute("error", "Error validating form");
    		return ShowBuyGames(map);
    	}
    	
    	if (config.getQuiz() == null || config.getQuiz().isEmpty()) {
    		map.addAttribute("error", "Please select a quiz to buy");
    		return ShowBuyGames(map);    		
    	}
    	
    	if (config.getType() == null || (!config.getType().equals("points") && !config.getType().equals("cc"))) {
    		map.addAttribute("error", "Please select a buying option");
    		return ShowBuyGames(map);
    	}

    	if (config.getType().equals("cc")) {
    		if (config.getFullname() == null || config.getFullname().isEmpty()
    			|| config.getCc() == null || config.getCc().isEmpty()
    			|| config.getEdate() == null || config.getEdate().isEmpty()) {
    			map.addAttribute("error", "Please fill in the credit card details");
    			return ShowBuyGames(map);
    		}
    	}
    	
    	User user = QueryHelper.GetUser(session.getUsername());
    	
    	if (config.getType().equals("points")) {
    		if (user.getPoints() < 10) {
    			map.addAttribute("error", "Sorry ! You don't have sufficient points to buy new games");
    			return ShowBuyGames(map);  
    		} else {
    			user.setPoints(user.getPoints() - 10);
    			QueryHelper.Save(user);
    		}
    	}
    	
    	UserQuiz uq = new UserQuiz();
    	
    	uq.setUserid(user.getId());
    	uq.setQuizid(config.getQuiz());
    	
    	QueryHelper.Save(uq);
    	
    	return Notify("Success !", "The game has been purchased", "Go Home", "home.q");
    }
    
    //
    // Submit Question
    //
    @RequestMapping("question.q")
    public ModelAndView ShowQuestion(ModelMap map)
    {
    	Session session = (Session) map.get("session");
    	
    	if (session == null || session.getUsername() == null) {
    		return Notify("Error !", "Session is invalid. Please authenticate.", "Goto Login", "login.q");
    	}
    	
    	CreateQuestionConfig question = new CreateQuestionConfig();
    	
    	map.addAttribute("question", question);
    	
    	return new ModelAndView("quiz-submitq", map);
    }
    
    @RequestMapping("submit-question.q")
    public ModelAndView SubmitQuestion(CreateQuestionConfig config, BindingResult result, ModelMap map)
    {	
    	map.addAttribute("question", config);
    	
    	Session session = (Session) map.get("session");
    	
    	if (session == null || session.getUsername() == null) {
    		return Notify("Error !", "Session is invalid. Please authenticate.", "Goto Login", "login.q");
    	}
    	
    	if (config.getQuestion() == null || config.getQuestion().isEmpty()
    	    || config.getAnswers() == null || config.getAnswers().isEmpty()) {
    		map.addAttribute("error", "Error validating form");
    		return new ModelAndView("quiz-submitq", map);
    	}
    	
    	SubmitQuestion q = new SubmitQuestion();
    	
    	q.setText(config.getQuestion());
    	q.setAnswers(config.getAnswers());
    	q.setAnswer(Integer.parseInt(config.getAnswer()));
    	q.setPoints(config.getPoints());
    	q.setUsername(session.getUsername());
    	
    	QueryHelper.Save(q);
    	
    	return Notify("Success !", "Your question has been submitted", "Home", "home.q");
    }
    
    //
    // Help
    //
    @RequestMapping("help.q")
    public ModelAndView Help(ModelMap map)
    {
    	return new ModelAndView("quiz-help", map);
    }
 }