package lp.struct;

public class LearningStyle
{
	public static final int DIVERGER = 0;
	public static final int ASSIMILATOR = 1;
	public static final int CONVERGER = 2;
	public static final int ACCOMMODATOR = 3;

	/* The percentages (%) of each part from the 2 Kolb's axis. */
	private double concretePerc;
	private double reflectivePerc;
	private double abstractPerc;
	private double activePerc;

	
	public LearningStyle(){}
	public LearningStyle(double concretePerc, double reflectivePerc, double abstractPerc, double activePerc)
	{
		super();
		this.concretePerc = concretePerc;
		this.reflectivePerc = reflectivePerc;
		this.abstractPerc = abstractPerc;
		this.activePerc = activePerc;
	}

	public double getConcretePerc() {
		return concretePerc;
	}

	public void setConcretePerc(double concretePerc) {
		this.concretePerc = concretePerc;
	}

	public double getReflectivePerc() {
		return reflectivePerc;
	}

	public void setReflectivePerc(double reflectivePerc) {
		this.reflectivePerc = reflectivePerc;
	}

	public double getAbstractPerc() {
		return abstractPerc;
	}

	public void setAbstractPerc(double abstractPerc) {
		this.abstractPerc = abstractPerc;
	}

	public double getActivePerc() {
		return activePerc;
	}

	public void setActivePerc(double activePerc) {
		this.activePerc = activePerc;
	}

	public int getStyle()
	{
		if (concretePerc > abstractPerc)
		{
			if (reflectivePerc > activePerc)
			{
				return DIVERGER;
			}
			else // reflectivePerc <= activePerc
			{
				return ACCOMMODATOR;
			}
		}
		else // concretePerc <= abstractPerc
		{
			if (reflectivePerc > activePerc)
			{
				return ASSIMILATOR;
			}
			else // reflectivePerc <= activePerc
			{
				return CONVERGER;
			}
		}
	}
}
