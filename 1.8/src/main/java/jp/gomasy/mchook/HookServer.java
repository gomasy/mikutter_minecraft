package jp.gomasy.mchook;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.URLDecoder;
import net.minecraft.client.Minecraft;

public class HookServer extends Thread {
    private Socket socket;
    private Minecraft mc = Minecraft.getMinecraft();

    public HookServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            socketRead(socketOpen());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socketClose();
            }
        }
    }

    public String decode(String text) throws IOException {
        return URLDecoder.decode(text, "utf-8");
    }

    public void sendText(String text) {
        if (mc.thePlayer != null) {
            mc.thePlayer.sendChatMessage(text);
        }
    }

    public BufferedReader socketOpen() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void socketRead(BufferedReader reader) throws IOException {
        String line;

        while ((line = reader.readLine()) != null) {
            sendText(decode(line));

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void socketClose() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
