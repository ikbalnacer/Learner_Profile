package lp.struct;

public class Dean extends Person
{

	public Dean(String firstName, String lastName, String email, String username, String password)
	{
		super(firstName, lastName, email, username, password);
		this.rank = Person.DEAN;
	}

}
