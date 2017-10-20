package lp.struct;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class Test
{
	private int id;
	private List<Exo> exos;
	private Chapter chapter;
	private LearningStyle exosPerc;
	private LocalDateTime testDate;

	public Test()
	{
		exos = new ArrayList<>();
	}

	public Test(int id, List<Exo> exos, Chapter chapter, LearningStyle exosPerc, LocalDateTime testDate)
	{
		super();
		this.id = id;
		this.exos = exos;
		this.chapter = chapter;
		this.exosPerc = exosPerc;
		this.testDate = testDate;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Exo> getExos() {
		return exos;
	}
	public void setExos(List<Exo> exos) {
		this.exos = exos;
	}

	public Chapter getChapter() {
		return chapter;
	}

	public void setChapter(Chapter chapter) {
		this.chapter = chapter;
	}

	public LearningStyle getExosPerc() {
		return exosPerc;
	}

	public void setExosPerc(LearningStyle exosPerc) {
		this.exosPerc = exosPerc;
	}

	public LocalDateTime getTestDate() {
		return testDate;
	}

	public void setTestDate(LocalDateTime testDate) {
		this.testDate = testDate;
	}

	public long remainingTime()
	{
		long remain = Timestamp.valueOf(testDate).toInstant().toEpochMilli() - System.currentTimeMillis();
		return remain < 0 ? 40000 : remain;
	}
}
