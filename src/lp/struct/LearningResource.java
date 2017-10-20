package lp.struct;

import java.util.List;

public class LearningResource
{
	protected String title;
	protected String description;
	protected int hours; // how many hours should this module be studied
	protected List<Competence> objectives;

	public LearningResource(String title, String description, int hours, List<Competence> objectives)
	{
		super();
		this.title = title;
		this.description = description;
		this.hours = hours;
		this.objectives = objectives;
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
}
