import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.InputStream;
 
 
/**
 * Simple Web Server for learning purposes. Handles one client connection 
 * at a time and sends back a static HTML page as response.
 */
public class WebServer {

    public static void main(String[] args) {

        final int LISTENING_PORT = 8088;

        WebServer webServer = new WebServer();
        try {
            webServer.runServer(LISTENING_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }

	ServerSocket s;
 
	 /**
	  * Creates and returns server socket.
	  * @param port Server port.
	  * @return created server socket
	  * @throws Exception Exception thrown, if socket cannot be created.
	  */
    protected ServerSocket getServerSocket(int port) throws Exception {
        return new ServerSocket(port);
    }
 
    /**
     * Starts web server and handles web browser requests.
     * @param port Server port(ex. 80, 8080)
     * @throws Exception Exception thrown, if server fails to start.
     */
    public void runServer(int port) throws Exception {
        s = getServerSocket(port);
 
        while (true) {
            try {
                Socket serverSocket = s.accept();
                handleRequest(serverSocket);
            } catch(IOException e) {
            	System.out.println("Failed to start server: " + e.getMessage());
                System.exit(0);
                return;
            }
        }
    }

    String webServerAddress;

    public void handleRequest(Socket s){
        InputStream is;     // inputStream from web browser
        String request;        // Request from web browser
        try {
            webServerAddress = s.getInetAddress().toString(  );
            System.out.println("Received a connection");
            //System.out.println("ACCEPTING YO");
            is = s.getInputStream();
            //BufferedReader bf = new BufferedReader(new InputStreamReader(s.getInputStream()));
 
            int count = 0;

            while(count != 10){
                
                if(is.read() == '\n') count++;
            }

            BufferedImage image = ImageIO.read(is);
            File imageFile = File.createTempFile("sqlDoc", ".jpg");
            ImageIO.write(image, "jpg", imageFile);

            System.out.println("absPath"+imageFile.getAbsolutePath());

            //do stuff with image
            
            s.close();
        } catch (IOException e) {
            System.out.println("Failed to send response to client: " + e.getMessage());
        } finally {
            if(s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return;
    }


    public void printHTTP(Socket s, String name){
        try{
            PrintWriter os;     // outputStream to web browser
            os = new PrintWriter(s.getOutputStream(), true);
            os.println("HTTP/1.0 200");
            os.println("Content-type: text/html");
            os.println("Server-name: myserver");
            String response = "<html><head>" +
                "<title>Simpl Web Page</title></head>\n" +
                "<h1>Congratulations!!!</h1>\n" +
                "<h3>This page was returned by " + webServerAddress + "</h3>\n" +
                "<p>You got from from ... " + name + 
                "</html>\n";
            os.println("Content-length: " + response.length(  ));
            os.println("");
            os.println(response);
            os.flush();
            os.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}