package proj.webapp;

import java.io.Serializable;

public class QuestionResult implements Serializable {

	private Boolean result;
	private String points;
	
	public Boolean getResult() {
		return result;
	}
	public void setResult(Boolean result) {
		this.result = result;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
}
