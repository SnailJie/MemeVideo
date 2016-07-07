package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLReader extends DefaultHandler  {

	private List<VideoFile> videoList;
	private VideoFile videoFile;
	private String elementsTag = null;

	public XMLReader(String filename) {
		SAXParserFactory saxfac = SAXParserFactory.newInstance();

		try {

			SAXParser saxparser = saxfac.newSAXParser();

			InputStream is = new FileInputStream(filename);

			saxparser.parse(is, this);

		} catch (ParserConfigurationException e) {

			e.printStackTrace();

		} catch (SAXException e) {

			e.printStackTrace();

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}

	public List<VideoFile> getList() {

		return videoList;
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if (qName.equalsIgnoreCase("videoList")) {
			if (null == videoList) {
				videoList = new ArrayList<VideoFile>();
			} else {
				videoList.clear();
			}
		} else if (qName.equalsIgnoreCase("video")) {
			videoFile = new VideoFile();
			String id = attributes.getValue("id");
			videoFile.setID(id);
		} else if (qName.equalsIgnoreCase("title")) {
			elementsTag = "title";
		} else if (qName.equalsIgnoreCase("filename")) {
			elementsTag = "filename";
		}

	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		String value = new String(ch, start, length);
		if (videoFile != null && elementsTag != null) {
			if ("title".equalsIgnoreCase(elementsTag)) {
				videoFile.setTitle(value);
				elementsTag = null;
			}
			if ("filename".equalsIgnoreCase(elementsTag)) {
				videoFile.setFileName(value);
				elementsTag = null;
			}

		}

	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if (qName.equalsIgnoreCase("video")) {
			videoList.add(videoFile);
			videoFile = null;
		}

	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}

	public int find(String id, String title, String filename) {
		int ret = -1;
		for (int i = 0; i < videoList.size(); i++) {
			if (videoList.get(i).getID().equalsIgnoreCase(id)) {
				if (videoList.get(i).getTitle().equalsIgnoreCase(title)) {
					if (videoList.get(i).getFilename().equalsIgnoreCase(filename)) {
						ret = i;
						break;
					}
				}
			}
		}
		return ret;
	}

}
