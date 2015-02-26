package jp.gomasy.mchook;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URLEncoder;

import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.event.ServerChatEvent;

public class HookClient extends Thread {
    public static final int PORT = 29654;
    private ServerChatEvent event;

    public HookClient(ServerChatEvent event) {
        this.event = event;
    }

    public void run() {
        Socket socket = null;
        String text = "[CHAT] <" + event.username + "> " + event.message;

        try {
            socket = socketOpen();
            socketWrite(socket, encode(text));
        } catch (IOException e) {
            FMLLog.warning("Socket open or write failed");
        } finally {
            if (socket != null) {
                socketClose(socket);
            }
        }
    }

    public String encode(String text) throws IOException {
        return URLEncoder.encode(text, "utf-8");
    }

    public Socket socketOpen() throws IOException {
        return new Socket("localhost", PORT);
    }

    public void socketWrite(Socket socket, String text) throws IOException {
        new DataOutputStream(socket.getOutputStream()).writeBytes(text);
    }

    public void socketClose(Socket socket) {
        try {
            socket.close();
        } catch (IOException e) {
            FMLLog.warning("Socket close failed");
        }
    }
}
