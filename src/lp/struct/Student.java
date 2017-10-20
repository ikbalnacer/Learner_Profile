package lp.struct;

public class Student extends Person
{
	private Domain studyDomain;
	private Profile profile;

	public Student(String firstName, String lastName, String email,
			String username, String password, Domain studyDomain)
	{
		super(firstName, lastName, email, username, password);
		this.rank = Person.STUD;
		this.studyDomain = studyDomain;
		this.profile = null;
	}

	public Domain getStudyDomain() {
		return studyDomain;
	}

	public void setStudyDomain(Domain studyDomain) {
		this.studyDomain = studyDomain;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
}
