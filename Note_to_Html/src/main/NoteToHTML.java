package main;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class NoteToHTML {

	final static File contentFolder = new File(System.getProperty("user.dir") + "\\src\\main\\Content");
	static String headOfHTML = "<!DOCTYPE html>\r\n"
			+ "<html lang=\"en\">\r\n"
			+ "\r\n"
			+ "<head>\r\n"
			+ "  <meta charset=\"UTF-8\">\r\n"
			+ "  <title>CodePen - Pure JavaScript Book Effect</title>\r\n"
			+ "  <link rel=\"stylesheet\" href=\"./style.css\">\r\n"
			+ "\r\n"
			+ "</head>\r\n"
			+ "\r\n"
			+ "<body>\r\n"
			+ "  <!-- partial:index.partial.html -->\r\n"
			+ "  <!-- <link href=\"https://fonts.googleapis.com/css?family=Lovers+Quarrel\" rel=\"stylesheet\"> -->\r\n"
			+ "  <div class=\"book\">\r\n"
			+ "    <div id=\"pages\" class=\"pages\">\r\n"
			+ "      <div class=\"page\">\r\n"
			+ "        <div class=\"notes-for\">\r\n"
			+ "          <h1>Notes</h1>\r\n"
			+ "          <h4>for</h4>\r\n"
			+ "          \r\n"
			+ "        </div>\r\n"
			+ "        <div class=\"coverbook_img\">\r\n"
			+ "          <img class=\"img\" src=\"https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1570010792l/52203834.jpg\" alt=\"\">\r\n"
			+ "        </div>\r\n"
			+ "       \r\n"
			+ "      </div>";
	
	static String endOfHTML = " <div class=\"page\">\r\n"
			+ "        The end\r\n"
			+ "      </div>\r\n"
			+ "    </div>\r\n"
			+ "  </div>\r\n"
			+ "  <!-- partial -->\r\n"
			+ "  <script src=\"./script.js\"></script>\r\n"
			+ "\r\n"
			+ "</body>\r\n"
			+ "\r\n"
			+ "</html>";
	
	static String contentOfHTML = "";
	
	public static void main(String[] args) {
		readContent();
		
	}

	private static void readContent() {
		readFilesInFolder(contentFolder);
	}
	
	@SuppressWarnings("deprecation")
	public static void readFilesInFolder(final File folder)
	{
		
		System.out.println("Folder: " + folder);
		
		for (final File fileEntry : folder.listFiles())
		{
			System.out.println(fileEntry);
			contentOfHTML = "";
			readAndParseFile(fileEntry);
			System.out.println("----------------RESULT-----------------");
			System.out.println(contentOfHTML);
			System.out.println("----------------END RESULT-----------------");
			
			File file = new File("C:/Users/buita/OneDrive/Projects/Somenotes/posts/" + fileEntry.getName() + ".html");
			CharSequence cs = contentOfHTML;
			try {
				FileUtils.write(file, cs);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void readAndParseFile(final File file) {
		try {
			String data = FileUtils.readFileToString(file, "UTF-8");
			parseFile(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void parseFile(String data) {
		
		contentOfHTML += headOfHTML;
		
		// a page has 21 lines
		// a line has 42 characters
		// \n is considered as end of line
		
		String page = "";
		Integer currentLineOfPage = 0;
		String line = "";
		
		for (int i = 0; i < data.length(); i++) {
			line += data.charAt(i);
			
			// signal of end of line
			if ((line.length() >= 42 && (data.charAt(i) == ' ')) || (data.charAt(i) == '\n'))
			{
				page += (data.charAt(i) == '\n') ? line + "<br>" : line;

				++currentLineOfPage;
				
				// signal of end of page
				if (currentLineOfPage >= 21)
				{
					addPageToHTML(page);
					
					
					page = "";
					currentLineOfPage = 0;
				}
				
				line = "";
			}	
		}
		
		if (!line.equals(""))
			page += line;
		if (!page.equals(""))
		{
			addPageToHTML(page);
		}
		contentOfHTML += endOfHTML;
		
		contentOfHTML = contentOfHTML.replace("==========", "<div class=\"separator\">==========</div>");
	}

	private static void addPageToHTML(String page) {
		contentOfHTML += " <div class=\"page\">\r\n"
				+ "        <div class=\"content\">"
				+ page
				+ "</div>\r\n"
				+ "      </div>";
		
		System.out.println("-----------------------Begin of page content--------------------\n" + page);
		System.out.println("-----------------------End of page content----------------------");
	}
	
	
}
