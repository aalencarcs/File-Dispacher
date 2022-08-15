import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Dispatcher implements Runnable{

    private Socket clientSocket;

    public Dispatcher(Socket socket) {
        this.clientSocket = socket;
    }


    @Override
    public void run() {
        try {

            // Port Connection
          //  ServerSocket serverSocket = new ServerSocket(555);
            //Socket clientSocket = serverSocket.accept();

            // Client Request
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String page = in.readLine();
            System.out.println(page);

            // 200 Header
            String standardHeader = "HTTP/1.0 200 Document Follows\r\n" +
                    "Content-Type: text/html; charset=UTF-8\r\n" +
                    "Content-Length: 320 \r\n" +
                    "\r\n";

            // 404 Header
            String errorHeader = "HTTP/1.0 404 Not Found\n" +
                    "Content-Type: text/html; charset=UTF-8\r\n" +
                    "Content-Length: 161 \r\n" +
                    "\r\n";

            // Changing files to bytes
            byte[] output = standardHeader.getBytes();
            byte[] error = errorHeader.getBytes();
            byte[] standardFile = Files.readAllBytes(Paths.get("/Users/codecadet/Desktop/simple_server/src/data/site.html"));
            byte[] errorFile = Files.readAllBytes(Paths.get("/Users/codecadet/Desktop/simple_server/src/data/error.html"));

            // Client Response
            DataOutputStream outputFile = new DataOutputStream(clientSocket.getOutputStream());

            // Checks if the request is accurately met to print the page, else prints error page
            if (page.contains("GET /src/data/site.html HTTP/1.1")) {
                outputFile.write(output);

                outputFile.write(standardFile, 0, standardFile.length);
            } else {
                outputFile.write(error);

                outputFile.write(errorFile, 0, errorFile.length);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    }

