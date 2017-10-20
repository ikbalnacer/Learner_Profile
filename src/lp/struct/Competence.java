package lp.struct;

public class Competence implements Comparable<Competence>
{
	public static final int BASIC = 0;
	public static final int MODERATE = 1;
	public static final int STRONG = 2;

	private String name;
	private int level;

	public Competence(String name, int level)
	{
		super();
		this.name = name;
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Competence &&
		((Competence) obj).getName().equals(name) &&
		((Competence) obj).getLevel() == level;
	}

	@Override
	public int compareTo(Competence other)
	{
		if (!(other instanceof Competence) || !((Competence) other).getName().equals(name))
		{
			return 2; // 2 means incomparable
		}
		else
		{
			if (other.getLevel() < level) return 1;
			else if (other.getLevel() > level) return -1;
			else return 0;
		}
	}
}
