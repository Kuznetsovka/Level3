package ru.gb.jt.network;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class SocketThread extends Thread {

    private final Socket socket;
    private DataOutputStream out;
    private SocketThreadListener listener;
    private ObjectOutputStream oos;
    private ByteArrayOutputStream bos = new ByteArrayOutputStream();
    private static final String STRMSG = "STRING";
    private static final String OBJMSG = "OBJECT";
    private String markerMsg = STRMSG;

    public SocketThread(SocketThreadListener listener, String name, Socket socket) {
        super(name);
        this.socket = socket;
        this.listener = listener;
        start();
    }

    @Override
    public void run() {
        try {
            listener.onSocketStart(this, socket);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            listener.onSocketReady(this, socket);
            while (!isInterrupted()) {
                if (markerMsg.equals(STRMSG)) {
                    String msg = in.readUTF();
                    listener.onReceiveString(this,socket, msg);
                } else {
                    ObjectInputStream ois = new ObjectInputStream(in);
                    Object msg = ois.readObject();
                    listener.onReceiveObject(this, socket, msg);
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            listener.onSocketException(this, e);
        } finally {
            close();
            listener.onSocketStop(this);
        }
    }

    public synchronized boolean sendMessage(String msg) {
        try {
            markerMsg = STRMSG;
            out.writeUTF(msg);
            out.flush();
            return true;
        } catch (IOException e) {
            listener.onSocketException(this, e);
            close();
            return false;
        }
    }

    public synchronized boolean sendMessage(Object obj) {
        try {
            markerMsg = OBJMSG;
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            out.write(bos.toByteArray());
            out.flush();
            return true;
        } catch (IOException e) {
            listener.onSocketException(this, e);
            close();
            return false;
        }
    }

    public synchronized void close() {
        interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            listener.onSocketException(this, e);
        }
    }
}
