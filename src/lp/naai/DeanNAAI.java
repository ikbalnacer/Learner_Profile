package lp.naai;

import java.util.List;

import lp.struct.Domain;

/* NAAI = Non Autonomous Agent Interface */
public interface DeanNAAI extends NAAI
{
	public void createDomain(Domain newDomain);

	public List<Domain> getDomains();
}
