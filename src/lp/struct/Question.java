package lp.struct;

import java.util.List;

public class Question
{
	public static final String QCM = "QCM";
	public static final String DIRECT = "direct";

	private int num;
	private int points;
	private String type;
	private String content;
	private List<String> choices;

	public Question(){}

	public Question(int num, int points, String type, String content,
			List<String> choices)
	{
		super();
		this.num = num;
		this.points = points;
		this.type = type;
		this.content = content;
		this.choices = choices;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getChoices() {
		return choices;
	}

	public void setChoices(List<String> choices) {
		this.choices = choices;
	}
}
