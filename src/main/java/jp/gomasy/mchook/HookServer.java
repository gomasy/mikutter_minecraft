package jp.gomasy.mchook;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
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
            socketRead(new BufferedReader(new InputStreamReader(socket.getInputStream())));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socketClose(socket);
        }
    }

    public void sendText(String text) {
        if (mc.thePlayer != null) {
            mc.thePlayer.sendChatMessage(text);
        }
    }

    public void socketRead(BufferedReader reader) {
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                try {
                    sendText(line);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void socketClose(Socket socket) {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
