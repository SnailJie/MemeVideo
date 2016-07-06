package server;

import static org.junit.Assert.*;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class XMLReaderTest {

	private XMLReader reader;
	private List<VideoFile> videoList;
	private Server server;

	@Before
	public void setUp() throws Exception {
		reader = new XMLReader("videoList.xml");
		videoList = reader.getList();
		server = new Server();
	}

	@Test
	public void createListOfVideos() {
		assertTrue(videoList instanceof List);
	}

	@Test
	public void listContainsVideoFiles() {
		assertTrue(videoList.get(0) instanceof VideoFile);
	}

	@Test
	public void videoFileReturnsCorrectFields() {
		VideoFile videoFile = videoList.get(0);
		assertNotNull(videoFile.getID());
		assertNotNull(videoFile.getTitle());
		assertNotNull(videoFile.getFilename());
	}

	@Test
	public void videoDataCorrectlyReadIn() {
		assertEquals(0, reader.find("20120213a2", "Monsters Inc.", "monstersinc_high.mpg"));
	}

	@Test
	public void videoDataAllCorrectlyReadIn() {
		assertEquals(0, reader.find("20120213a2", "Monsters Inc.", "monstersinc_high.mpg"));
		assertEquals(1, reader.find("20120102b7", "Avengers", "avengers-featurehp.mp4"));
		assertEquals(2, reader.find("20120102b4", "Prometheus","prometheus-featureukFhp.mp4"));
	}
	
	@Test
	public void cmpListServer2xml(){
		for(int i=0; i<videoList.size(); i++)
		{
			assertEquals(videoList.get(i).getID(),server.getList().get(i).getID());
			assertEquals(videoList.get(i).getTitle(),server.getList().get(i).getTitle());
			assertEquals(videoList.get(i).getFilename(),server.getList().get(i).getFilename());
		}
		for(int i=0; i<server.getList().size(); i++)
		{
			assertEquals(videoList.get(i).getID(),server.getList().get(i).getID());
			assertEquals(videoList.get(i).getTitle(),server.getList().get(i).getTitle());
			assertEquals(videoList.get(i).getFilename(),server.getList().get(i).getFilename());
		}
	}

}
