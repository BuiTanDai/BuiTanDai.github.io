package main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.io.FileUtils;

public class NoteToHTML {

	final static File contentFolder = new File(System.getProperty("user.dir") + "\\src\\main\\Content");
	
	static String title = "";
	static String author = "";
	static String coverImage = "";
	static String headOfHTML = "";
	static String firstNote = "";
	static String reference = "";
	
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
	
	
	static Queue<Book> bookList = new LinkedList<Book>();
	
	static String headOfBookList = "\r\n"
			+ "    <!DOCTYPE html>\r\n"
			+ "    <html lang=\"en\">\r\n"
			+ "\r\n"
			+ "    <head>\r\n"
			+ "        <meta charset=\"UTF-8\">\r\n"
			+ "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
			+ "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
			+ "        <title>Book List</title>\r\n"
			+ "        <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\"\r\n"
			+ "            integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">\r\n"
			+ "        <link rel=\"stylesheet\" href=\"./style.css\">\r\n"
			+ "    </head>\r\n"
			+ "\r\n"
			+ "    <body>\r\n"
			+ "        <!-- <img id=\"bg_im\" src=\"./images/book_self.jpg\" alt=\"Bookself in library\"> -->\r\n"
			+ "\r\n"
			+ "        <!-- Title -->\r\n"
			+ "        <section id=\"title\">\r\n"
			+ "            <h1 class=\"brand-name\">somenotes</h1>\r\n"
			+ "            <h1 class=\"sologan\"> be a better man</h1>\r\n"
			+ "        </section>\r\n"
			+ "\r\n"
			+ "        <!-- Book list -->\r\n"
			+ "        <section id=\"book-list\">\r\n"
			+ "            <div class=\"row\">";
	static String enfOfBookList = " </div>\r\n"
			+ "        </section>\r\n"
			+ "\r\n"
			+ "        <!-- Homepage About -->\r\n"
			+ "        <button id=\"btn-home\"><a class=\"text-decoration-none\" href=\"posts/note.html\">home</a></button>\r\n"
			+ "        <button id=\"btn-about\"><a class=\"text-decoration-none\" href=\"posts/note.html\">about</a></button>\r\n"
			+ "\r\n"
			+ "        <!-- Javascript -->\r\n"
			+ "        <script src=\"index.js\"></script>\r\n"
			+ "    </body>\r\n"
			+ "    <!-- <footer>\r\n"
			+ "        <section id=\"footer\">\r\n"
			+ "            Footer\r\n"
			+ "        </section>\r\n"
			+ "    </footer> -->\r\n"
			+ "\r\n"
			+ "    </html>";
	static String contentOfBookList = "";
	
	
	public static void main(String[] args) {
		// read file/note -> parse to html format
		readContent();
		
		// generate booklist based on notes
		generateBooklist();
		
	}

	private static void generateBooklist() {
		contentOfBookList = headOfBookList;
		while (!bookList.isEmpty())
		{
			Book book = bookList.remove();
			
			contentOfBookList += "<div class=\"col-md-6 col-lg-4\">\r\n"
					+ "                    <div class=\"card book-item\">\r\n"
					+ "                        <a class=\"text-decoration-none\" href=\""
					+ book.reference
					+ "\">\r\n"
					+ "                            <div class=\"card-body\">\r\n"
					+ "                                <img class=\"book-img\" src=\""
					+ book.coverImage
					+ "\" alt=\"\">\r\n"
					+ "                                <div class=\"book-info\">\r\n"
					+ "                                    <h1 class=\"book-title\">[Notes] "
					+ book.title
					+ "</h1>\r\n"
					+ "                                    <p>"
					+ book.firstNote
					+ "</p>\r\n"
					+ "                                </div>\r\n"
					+ "                            </div>\r\n"
					+ "                        </a>\r\n"
					+ "\r\n"
					+ "                    </div>\r\n"
					+ "                </div>";
		}
		contentOfBookList += enfOfBookList;
		
		File file = new File("C:/Users/buita/OneDrive/Projects/Somenotes/BookList.html");
		CharSequence cs = contentOfBookList;
		try {
			FileUtils.write(file, cs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			title = "";
			author = "";
			coverImage = "";
			reference = "posts/" + fileEntry.getName() + ".html";
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
		System.out.println("-----------BEGIN PARSING--------");
		String[] bookInfo = data.split("\n", 4);
		System.out.println("Parsed Title: " + bookInfo[0].split(": ")[1]);
		System.out.println("Parsed Author: " + bookInfo[1].split(": ")[1]);
		System.out.println("Parsed Cover image: " + bookInfo[2].split(": ")[1]);
		System.out.println("Parsed First note: " + bookInfo[3].split("\n", 3)[1]);
		System.out.println("Parsed Notes: " +  bookInfo[3]);
		System.out.println("-----------END PARSING--------");
		
		title = bookInfo[0].split(": ")[1];
		author = bookInfo[1].split(": ")[1];
		coverImage = bookInfo[2].split(": ")[1];
		firstNote = bookInfo[3].split("\n")[1];
		Book book = new Book(title, author, coverImage, firstNote, reference);
		bookList.add(book);
		
		setHeadOfHTML();
		
		contentOfHTML += headOfHTML;
		
		String notes =  bookInfo[3];
		
		// a page has 21 lines
		// a line has 42 characters
		// \n is considered as end of line
		
		String page = "";
		Integer currentLineOfPage = 0;
		String line = "";
		
		for (int i = 0; i < notes.length(); i++) {
			line += notes.charAt(i);
			
			// signal of end of line
			if ((line.length() >= 42 && (notes.charAt(i) == ' ')) || (notes.charAt(i) == '\n'))
			{
				page += (notes.charAt(i) == '\n') ? line + "<br>" : line;

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

	private static void setHeadOfHTML() {
		headOfHTML = "<!DOCTYPE html>\r\n"
				+ "<html lang=\"en\">\r\n"
				+ "\r\n"
				+ "<head>\r\n"
				+ "  <meta charset=\"UTF-8\">\r\n"
				+ "  <title>"
				+ title
				+ " - "
				+ author
				+ "</title>\r\n"
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
				+ "          <img class=\"img\" src="
				+ coverImage
				+ " alt=\"\">\r\n"
				+ "        </div>\r\n"
				+ "       \r\n"
				+ "      </div>";
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
