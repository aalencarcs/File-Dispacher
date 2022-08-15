import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) {
        try {

        ServerSocket serverSocket = new ServerSocket(8077);

        while (true) {


            Socket clientSocket = serverSocket.accept();
            ExecutorService cachedPool = Executors.newCachedThreadPool();
            cachedPool.submit(new Dispatcher(clientSocket));




        }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

