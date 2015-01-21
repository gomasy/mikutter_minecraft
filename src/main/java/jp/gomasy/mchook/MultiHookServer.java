package jp.gomasy.mchook;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;

public class MultiHookServer extends Thread {
    public static final int PORT = 29653;

    @Override
    public void run() {
        ServerSocket server = null;

        try {
            server = new ServerSocket(PORT);
            while (true) {
                socketCreate(server);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socketClose(server);
        }
    }

    public void socketCreate(ServerSocket server) {
        try {
            Socket socket = server.accept();
            new HookServer(socket).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void socketClose(ServerSocket server) {
        try {
            if (server != null) {
                server.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
