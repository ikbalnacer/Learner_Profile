package lp.struct;

import java.util.List;

public class Module
{
	// states
	public static final int AVAIL = 0; //available to study
	public static final int NEW = 1; //not assigned a teacher
	public static final int SUSP = 2; //has teacher, but no course yet

	private String title;
	private String description;
	private int hours; // how many hours should this module be studied
	private List<Competence> objectives;
	private List<Competence> preRequisite;
	private int state;

	public Module(String title, String description, int hours, List<Competence> preRequisite,
			List<Competence> objectives, int state)
	{
		this.title = title;
		this.description = description;
		this.hours = hours;
		this.objectives = objectives;
		this.preRequisite = preRequisite;
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public String getTitle() {
		return title;
	}

	public List<Competence> getObjectives() {
		return objectives;
	}
	public List<Competence> getPreRequisite() {
		return preRequisite;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
