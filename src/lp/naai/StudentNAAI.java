package lp.naai;

import java.util.List;
import java.util.Map;

import lp.struct.Chapter;
import lp.struct.LSI;
import lp.struct.Module;

/* NAAI = Non Autonomous Agent Interface */
public interface StudentNAAI extends NAAI
{
	public void initProfile(List<Module> competences, Map<String, Double> bacMarks, LSI lsiResponses);

	public void startCourse();

	public void getChapters(Module module);

	public void download(Chapter selectedChapter);
}
