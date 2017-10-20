package lp.struct;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Chapter
{
	private int num;
	private String title;
	private String digest;
	private String filePath;
	private String docFormat;
	private LocalDateTime startPoint;

	public Chapter(int num, String title, String digest, String filePath, String docFormat, LocalDateTime startPoint)
	{
		super();
		this.num = num;
		this.title = title;
		this.digest = digest;
		this.filePath = filePath;
		this.docFormat = docFormat;
		this.startPoint = startPoint;
	}

	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getDocFormat() {
		return docFormat;
	}
	public void setDocFormat(String docFormat) {
		this.docFormat = docFormat;
	}
	public LocalDateTime getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(LocalDateTime startPoint) {
		this.startPoint = startPoint;
	}

	public long remainingTime()
	{
		long remain = Timestamp.valueOf(startPoint).toInstant().toEpochMilli() - System.currentTimeMillis();
		return remain < 0 ? 30000 : remain;
	}
}
