package lp.struct;

public class DomainResp extends Person
{
	private Domain domain;

	public DomainResp(String firstName, String lastName, String email, String username, String password, Domain domain)
	{
		super(firstName, lastName, email, username, password);
		this.rank = Person.DOM;
		this.domain = domain;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}
}
