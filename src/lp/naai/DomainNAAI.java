package lp.naai;

import java.util.List;

import lp.struct.Module;

/* NAAI = Non Autonomous Agent Interface */
public interface DomainNAAI extends NAAI
{
	public void createModule(Module newModule);

	public List<Module> getModules();
}
