package lp.struct;

import java.util.List;

public class Content
{
	private List<LearningStyle> targetStyles;
	private String format;

	public Content(List<LearningStyle> targetStyles, String format)
	{
		super();
		this.targetStyles = targetStyles;
		this.format = format;
	}

	public List<LearningStyle> getTargetStyles() {
		return targetStyles;
	}

	public void setTargetStyles(List<LearningStyle> targetStyles) {
		this.targetStyles = targetStyles;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
}
