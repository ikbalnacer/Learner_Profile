package lp.struct;

import java.util.List;

public class Course extends LearningResource
{
	private List<Chapter> chapters;

	public Course(String title, String description, int hours, List<Competence> objectives, List<Chapter> chapters)
	{
		super(title, description, hours, objectives);
		this.chapters = chapters;
	}

	public List<Chapter> getChapters() {
		return chapters;
	}
}
