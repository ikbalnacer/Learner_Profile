package lp.naai;

import lp.struct.Chapter;
import lp.struct.Test;

/* NAAI = Non Autonomous Agent Interface */
public interface TeacherNAAI extends NAAI
{
	public void createChapter(Chapter newChapter);

	public void createTest(Test newTest);
}
