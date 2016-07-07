package server;

import java.io.Serializable;
import java.util.List;

public class VideoList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<VideoFile> videoList;

	public List<VideoFile> getVideoList() {
		return videoList;
	}

	public void setVideoList(List<VideoFile> videoList) {
		this.videoList = videoList;
	}
}
