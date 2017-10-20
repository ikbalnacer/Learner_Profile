package lp.struct;

import java.util.ArrayList;
import java.util.List;

public class Exo
{
	private int num;
	private String kolbProcessing;
	private String kolbPerceiving;
	private List<Question> questions;

	public Exo()
	{
		questions = new ArrayList<>();
	}

	public Exo(int num, String kolbProcessing, String kolbPerceiving,
			List<Question> questions)
	{
		super();
		this.num = num;
		this.kolbProcessing = kolbProcessing;
		this.kolbPerceiving = kolbPerceiving;
		this.questions = questions;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getKolbProcessing() {
		return kolbProcessing;
	}

	public void setKolbProcessing(String kolbProcessing) {
		this.kolbProcessing = kolbProcessing;
	}

	public String getKolbPerceiving() {
		return kolbPerceiving;
	}

	public void setKolbPerceiving(String kolbPerceiving) {
		this.kolbPerceiving = kolbPerceiving;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
}
