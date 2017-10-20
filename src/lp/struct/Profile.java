package lp.struct;

import java.util.List;
import java.util.Map;

public class Profile
{
	private String style;
	private TestResult kolbResult;
	private List<TestResult> otherTestsResults;
	private List<Competence> requisites;
	private Map<String, Double> bacMarks;

	public Profile()
	{
		this.style = "";
	}

	public Profile(String style)
	{
		this.style = style;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public TestResult getKolbResult() {
		return kolbResult;
	}

	public void setKolbResult(TestResult kolbResult) {
		this.kolbResult = kolbResult;
	}

	public List<TestResult> getOtherTestsResults() {
		return otherTestsResults;
	}

	public void setOtherTestsResults(List<TestResult> otherTestsResults) {
		this.otherTestsResults = otherTestsResults;
	}

	public List<Competence> getRequisites() {
		return requisites;
	}

	public void setRequisites(List<Competence> requisites) {
		this.requisites = requisites;
	}

	public Map<String, Double> getBacMarks() {
		return bacMarks;
	}

	public void setBacMarks(Map<String, Double> bacMarks) {
		this.bacMarks = bacMarks;
	}

	/* true if all this module's competences are already acquired
	 *  by this profile's student. */
	public boolean acquired(Module module)
	{
		return getRequisites().containsAll(module.getObjectives());
	}

	/* true if all pre-requisites for the module are here
	 *  in this profile.*/
	public boolean qualified(Module module)
	{
		return getRequisites().containsAll(module.getPreRequisite());
	}
}
