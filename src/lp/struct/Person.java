package lp.struct;

public abstract class Person
{
	// Ranks
	public static final int STUD = 0;
	public static final int TEACH = 1;
	public static final int DOM = 2;
	public static final int DEAN = 3;

	protected String firstName;
	protected String lastName;
	protected String email;

	protected String username;
	protected String password;
	protected int rank;

	public Person() {}

	public Person(String firstName, String lastName, String email, String username, String password)
	{
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
}
