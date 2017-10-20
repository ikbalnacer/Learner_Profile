package lp.struct;

import java.util.List;

public class LSI
{
	private List<LSIQuestion> questions;

	public LSI(List<LSIQuestion> questions)
	{
		this.questions = questions;
	}

	public List<LSIQuestion> getQuestions()
	{
		return questions;
	}

	public void setQuestions(List<LSIQuestion> questions)
	{
		this.questions = questions;
	}
}
