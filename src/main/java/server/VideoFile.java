package server;

import java.io.Serializable;

public class VideoFile implements Serializable {

	private String id;
	private String title;
	private String filename;

	public VideoFile() {

	}

	public String getID() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getFilename() {
		return filename;
	}

	public void setID(String id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setFileName(String filename) {
		this.filename = filename;
	}

}
