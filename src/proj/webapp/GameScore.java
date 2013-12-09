package proj.webapp;

import java.io.Serializable;

public class GameScore implements Serializable {

	private String gameName;
	private String gameScore;
	private String gameMaxScore;
	
	public String getGameName() {
		return gameName;
	}
	
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	
	public String getGameScore() {
		return gameScore;
	}
	
	public void setGameScore(String gameScore) {
		this.gameScore = gameScore;
	}
	
	public String getGameMaxScore() {
		return gameMaxScore;
	}
	
	public void setGameMaxScore(String gameMaxScore) {
		this.gameMaxScore = gameMaxScore;
	}
}
