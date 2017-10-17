

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Programming Assignment #2. Webserver with mime-type handling. References are:
 * 
 * http://www.java2s.com/Code/Java/Network-Protocol/ASimpleWebServer.htm
 * 
 * I did do the extra-credit defined by:
 * 
 * - Multi-threaded [done]
 * - Supports MP3 [done]
 * - Supports MP4 [done]
 * - Documentation should also show that multiple machines 
 *   are able to access the server at the same time.
 * - First_Last-PA2-EC.zip 
 * 
 * @author matt
 *
 */
public class webserver {

	public static final String DEFAULT_PAGE = "index.html";
	public static final Map<String, String> MIME_TYPES = new HashMap<>();

	// Defined here:
	// http://greenbytes.de/tech/webdav/rfc2616.html#media.types
	static {
		MIME_TYPES.put("html", "text/html");
		MIME_TYPES.put("htm", "text/html");
		MIME_TYPES.put("jpg", "image/jpeg");
		MIME_TYPES.put("jpeg", "image/jpeg");
		MIME_TYPES.put("png", "image/png");
		MIME_TYPES.put("css", "text/css");
		MIME_TYPES.put("gif", "image/gif");
		MIME_TYPES.put("pdf", "application/pdf");
		MIME_TYPES.put("js", "application/javascript");
		MIME_TYPES.put("mp4", "application/mp4");
		MIME_TYPES.put("mp3", "audio/mpeg3");
	}

	// THIS IS THE BASE WEB DIR, CHANGE THIS PROPERTY IF NOT WORKING
	public static String BASE_DIR = System.getProperty("user.dir");

	/**
	 * This is the main method. Takes one argument, the PORT number to listen on
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			
			if (args.length < 1) {
				System.err.println("Must specify port number!");
				System.exit(-1);
			}
			// first arg should be a port number
			int port = Integer.parseInt(args[0]);

			ServerSocket s;

			log("Webserver started on port: " + port);
			log("Based webserver dir: " + BASE_DIR);
			
			try {
				// create the main server socket
				s = new ServerSocket(port);

				System.out.println("Listening for connections...");
				while (true) {
					Socket remote = s.accept();

					// Each request is handled in a separate thread...
					RequestHandler handler = new RequestHandler(remote);
					Thread t = new Thread(handler);
					t.start();
				}

			} catch (Exception e) {
				System.out.println("Error: " + e);
				return;
			}
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}
	
	private static void log(String x) {
		System.out.println(x);
	}
}

/**
 * Class instance that is created for each request. Handles parsing the request,
 * determining the request type and
 * sending the response with the valid mime-type / content-type header.
 * 
 * Examples I looked at:
 * 
 * http://cs.fit.edu/~mmahoney/cse3103/java/Webserver.java
 * 
 * 
 * @author matt
 *
 */
class RequestHandler implements Runnable {

	Socket request = null;

	public RequestHandler(Socket inRequest) {
		this.request = inRequest;
	}

	@Override
	public void run() {
		PrintStream out = null;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(request.getInputStream()));
			out = new PrintStream(new BufferedOutputStream(request.getOutputStream()));

			List<String> headers = new ArrayList<>();
			String reader = ".";

			// Should be: GET index.html
			while (!reader.equals("")) {
				headers.add(reader);
				reader = in.readLine();
				log("Read: " + reader);
			}

			// Now we have to determine the 'GET ' request by looking through
			// all the headers
			String fileRequest = getFileRequestFromHeaders(headers);

			// should be after the get [space]...

			log("Filename: " + fileRequest);
			fileRequest = filterFileRequest(fileRequest);
			String ext = getExtensionFromRequest(fileRequest);
			String mime = getMimeTypeFromExtension(ext);
			
			log("Filtered request: " + fileRequest);
			log("Base directory (user.dir): " + webserver.BASE_DIR);
			log("Extension: " + ext);
			log("Mime: " + mime);
			
			// Send file contents to client, then close the connection
			InputStream theFile = new FileInputStream(fileRequest);

			out.print("HTTP/1.0 200 OK\r\n");
			out.print("Content-Type: ");
			out.print(mime);
			out.print("\r\n\r\n");

			// Now stream the file
			byte[] a = new byte[4096];
			int n;
			while ((n = theFile.read(a)) > 0) {
				out.write(a, 0, n);
			}
			theFile.close();

		} catch (Exception e) {
			System.err.println(e.getMessage());
			out.println("HTTP/1.0 500 Server Error\r\n" + "Content-type: text/html\r\n\r\n" + "<html><head></head><body>" + e.getMessage()
					+ "</body></html>\r\n");
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}

				if (in != null)
					in.close();

				request.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Loop through all the sent headers and look for the one thing we care
	 * about,
	 * the GET request
	 * 
	 * @param headers
	 * @return
	 */
	private String getFileRequestFromHeaders(List<String> headers) {
		String file = null;
		String patternStr = "(?i)get\\s";
		Pattern pattern = Pattern.compile(patternStr);
		for (String header : headers) {
			Matcher matcher = pattern.matcher(header);
			if (matcher.find()) {
				// Change to Regex
				file = header.substring(matcher.end(), header.lastIndexOf(' ')); // I dont like lastIndexOf
			}
		}

		return file;
	}
	
	/**
	 * This method does the following:
	 * - if a / is all, change to index.html
	 * - if it starts with /, then get rid of it.
	 * @param in
	 * @return
	 */
	private String filterFileRequest(String in) {
		if (in.equalsIgnoreCase("/")) {
			return webserver.DEFAULT_PAGE;
		} else if (in.startsWith("/")) {
			return in.substring(1, in.length());
		} else {
			return in;
		}
	}

	/**
	 * Gets the extension from the request.
	 * 
	 * @param inReq
	 * @return extension with NO dot..
	 */
	String getExtensionFromRequest(String inReq) {
		if (inReq == null || inReq.indexOf(".") < 0) {
			return "html";
		} else {
			return inReq.substring(inReq.lastIndexOf(".") + 1, inReq.length());
		}
	}

	/**
	 * Your web server must support the following MIME types to receive full
	 * credit: HTML, jpg, png, gif, pdf, js and css.
	 * 
	 * @param inExt
	 * @return
	 */
	String getMimeTypeFromExtension(String inExt) {
		return webserver.MIME_TYPES.get(inExt);
	}

	private void log(String x) {
		System.out.println(x);
	}
}
