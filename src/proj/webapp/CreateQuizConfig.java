package proj.webapp;

import com.sun.istack.internal.NotNull;

public class CreateQuizConfig {

	@NotNull
	private String name;
	
	@NotNull
	private String category;
		
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
}
