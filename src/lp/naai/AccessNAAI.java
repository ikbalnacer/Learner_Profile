package lp.naai;

/* NAAI = Non Autonomous Agent Interface */
public interface AccessNAAI
{
	public void subscribe(String[] subscribeArgs);

	public void login(String username, String password);
}
