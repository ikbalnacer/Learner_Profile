package lp.struct;

public class TestResult
{
	public static final int LSI = 0;
	public static final int ORDINARY = 1;

	private Test test;
	private int type;
	private LearningStyle resultPerc;

	public TestResult(Test test, int type, LearningStyle resultPerc)
	{
		super();
		this.test = test;
		this.type = type;
		this.resultPerc = resultPerc;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public LearningStyle getResultPerc() {
		return resultPerc;
	}

	public void setResultPerc(LearningStyle resultPerc) {
		this.resultPerc = resultPerc;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
