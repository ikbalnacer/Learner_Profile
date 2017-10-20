package lp.struct;

public class Teacher extends Person
{
	private Module module;

	public Teacher(String firstName, String lastName, String email,
			String username, String password, Module module)
	{
		super(firstName, lastName, email, username, password);
		this.rank = Person.TEACH;
		this.module = module;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}
}
