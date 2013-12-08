package proj.webapp; 

import java.util.ArrayList;
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
    	
    	QueryHelper.AddUser(user);
    	
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
    	Map<String,String> categories = new LinkedHashMap<String, String>();
    	Map<String,String> games = new LinkedHashMap<String, String>();
    	List<String> modes = new LinkedList<String>();
    	
    	Boolean firstEntry = true;
    	
    	for (UserQuiz uq : uqs) {
    		Quiz q = QueryHelper.GetQuiz(uq.getQuizid());
    		categories.put(q.getId(), q.getCategory());
    		
    		if (firstEntry) {
    			games.put(q.getId(), q.getName());
    		}
    	}
	
    	modes.add("Normal");
    	modes.add("Learner");
    	
    	map.addAttribute("config", new HomeFormConfig());
    	map.addAttribute("games", games);
    	map.addAttribute("categories", categories);
    	map.addAttribute("modes", modes);
    	
    	return new ModelAndView("quiz-home", map);
    }
    
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
    	
    	if (config.getCategory().isEmpty() || config.getGame().isEmpty()) {
    		result.reject("Invalid data");
    		return MakeHome(user, map);
    	}
    	
    	//
    	// Move on to playing the game
    	//
    	String categoryid = config.getCategory();
    	String gameid = config.getGame();
    	String mode = config.getMode();
    
    	//
    	// Update session
    	//  	
    	Quiz quiz = QueryHelper.GetQuiz(gameid);

    	session.setMode(mode);
    	if (session.getGame() == null) {
    		session.setGame(new Game(quiz));
    	}

    	ContinueGame(user, session.getGame(), map);
    	return new ModelAndView("quiz-play", map);
    }

    private void ContinueGame(User user, Game game, ModelMap map)
    {
    	Session session = (Session) map.get("session");
    	GameFormConfig config = new GameFormConfig();

    	int idx = game.getQuestionid();
    	
    	Quiz quiz = game.getQuiz();
    	List<QuizQuestion> qqs = QueryHelper.GetQuestions(quiz.getId());
    	Question q = QueryHelper.GetQuestion(qqs.get(idx).getQuestionid());
    	
    	Map<String,String> answers = new HashMap<String,String>();
    	
    	int i = 0;
    	for (String s : q.getAnswers().split(",")) {
    		answers.put("" + i, s);
    		++i;
    	}
    	
    	if (session.getMode().equals("Learner")) {
    		session.setCheck(true);
    	}
    	
    	map.addAttribute("quizname", quiz.getName());
    	map.addAttribute("question", q.getText());
    	map.addAttribute("answers", answers);
    	map.addAttribute("config", config);
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
    	
    	String mode = session.getMode();
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
    		if (!skip && session.getCheck()) {
    			if (aidx == -1 || aidx != q.getAnswer()) {
    				map.addAttribute("error", "Wrong answer. Correct answer is option (" + (aidx + 1) + ")");
    			} else {
    				map.addAttribute("error", "Correct answer");
    			}
				ContinueGame(user, game, map);
				map.addAttribute("answer", q.getAnswer());
				session.setCheck(false);
				return new ModelAndView("quiz-play", map);
    		}
    	} else {
        	if (!skip && aidx == -1) {
        		map.addAttribute("error", "Please select an answer or choose to skip");
        		ContinueGame(user, game, map);
        		return new ModelAndView("quiz-play", map);
        	}    		
    	}
    	
    	game.setQuestionid(game.getQuestionid() + 1);
    	
    	if (game.getQuestionid() > 10) {
    		return new ModelAndView("quiz-scorecard", map);
    	}
    	
    	ContinueGame(user, game, map);
    	return new ModelAndView("quiz-play", map);
    }
    
    private ModelAndView QuizPlayCancel(GameFormConfig config, BindingResult result, ModelMap map)
    {
    	Session session = (Session) map.get("session");
    	
    	assert session != null;
    	
    	User user = QueryHelper.GetUser(session.getUsername());
    	
    	return Notify("Hmm .. Giving up so easily !", "The quiz has been cancelled.", "Go Home", "home.q");
    }
 }