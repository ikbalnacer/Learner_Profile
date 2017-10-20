package lp.struct;

import java.util.ArrayList;
import java.util.List;

public class LSIQuestion extends Question
{
	private List<String> styles;
	private List<Integer> values;

	public LSIQuestion(int num, int points, String type, String content,
			List<String> choices, List<String> styles, List<Integer> values)
	{
		super(num, points, type, content, choices);
		this.styles = styles;
		this.values = values == null ? new ArrayList<>() : values;
	}

	public List<String> getStyles() {
		return styles;
	}

	public void setStyles(List<String> styles) {
		this.styles = styles;
	}

	public List<Integer> getValues() {
		return values;
	}

	public void setValues(List<Integer> values) {
		this.values = values;
	}
}
