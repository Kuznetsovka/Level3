package ru.gb.jt.chat.server.core;

import ru.gb.jt.chat.library.Library;
import ru.gb.jt.network.ServerSocketThread;
import ru.gb.jt.network.ServerSocketThreadListener;
import ru.gb.jt.network.SocketThread;
import ru.gb.jt.network.SocketThreadListener;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

public class ChatServer implements ServerSocketThreadListener, SocketThreadListener {

    ServerSocketThread server;
    ChatServerListener listener;
    private Vector<SocketThread> clients = new Vector<>();

    public ChatServer(ChatServerListener listener) {
        this.listener = listener;
    }

    public void start(int port) {
        if (server == null || !server.isAlive()) {
            server = new ServerSocketThread(this, "Server", port, 2000);
        } else {
            putLog("Server already started!");
        }
    }

    public void stop() {
        if (server != null && server.isAlive()) {
            server.interrupt(); //null.interrupt();
        } else {
            putLog("Server is not running");
        }
    }

    private void putLog(String msg) {
        listener.onChatServerMessage(msg);
    }

    /**
     * Server Socket Thread methods
     * */

    @Override
    public void onServerStart(ServerSocketThread thread) {
        putLog("Server started");
        SqlClient.connect();
    }

    @Override
    public void onServerStop(ServerSocketThread thread) {
        putLog("Server stopped");
        for (SocketThread client : clients) {
            client.close();
        }
        SqlClient.disconnect();
    }

    @Override
    public void onServerSocketCreated(ServerSocketThread thread, ServerSocket server) {
        putLog("Server socket created");
    }

    @Override
    public void onServerTimeout(ServerSocketThread thread, ServerSocket server) { }

    @Override
    public void onSocketAccepted(ServerSocketThread thread, ServerSocket server, Socket socket) {
        putLog("Client connected");
        String name = "Socket Thread " + socket.getInetAddress() + ":" + socket.getPort();
        new ClientThread(this, name, socket);
    }

    @Override
    public void onServerException(ServerSocketThread thread, Throwable exception) {
        exception.printStackTrace();
    }

    /**
     * Socket Thread methods
     * */

    @Override
    public synchronized void onSocketStart(SocketThread thread, Socket socket) {
        putLog("Client connected");
    }

    @Override
    public synchronized void onSocketStop(SocketThread thread) {
        ClientThread client = (ClientThread) thread;
        clients.remove(thread);
        if (client.isAuthorized() && !client.isReconnected()) {
            sendToAllAuthorizedClients(Library.getTypeBroadcast("Server",
                    client.getNickname() + " disconnected"));
        }
        sendToAllAuthorizedClients(Library.getUserList(getUsers()));
    }

    @Override
    public synchronized void onSocketReady(SocketThread thread, Socket socket) {
        putLog("Client is ready to chat");
        clients.add(thread);
    }

    @Override
    public synchronized void onReceiveString(SocketThread thread, Socket socket, String msg) {
        ClientThread client = (ClientThread) thread;
        if (client.isAuthorized()) {
            handleAuthorizedMessage(client, msg);
        } else {
            handleNonAuthorizedMessage(client, msg);
        }
    }

    @Override
    public synchronized void onReceiveObject(SocketThread thread, Socket socket,Object obj){
        System.out.println("Hello");
        System.out.println(obj.hashCode());
    }

    @Override
    public synchronized void onSocketException(SocketThread thread, Exception exception) {
        exception.printStackTrace();
    }

    private void handleNonAuthorizedMessage(ClientThread client, String msg) {
//        /auth_request±login±password
        String[] arr = msg.split(Library.DELIMITER);
        if (arr.length != 3 || !arr[0].equals(Library.AUTH_REQUEST)) {
            client.msgFormatError(msg);
            return;
        }
        String login = arr[1];
        String password = arr[2];
        String nickname = SqlClient.getNickname(login, password);
        if (nickname == null) {
            putLog("Invalid credentials for user" + login);
            client.authFail();
            return;
        } else {
            ClientThread oldClient = findClientByNickname(nickname);
            client.authAccept(nickname);
            if (oldClient == null) {
                sendToAllAuthorizedClients(Library.getTypeBroadcast("Server", nickname + " connected"));
            } else {
                oldClient.reconnect();
                clients.remove(oldClient);
            }
        }
        sendToAllAuthorizedClients(Library.getUserList(getUsers()));
    }

    private void handleAuthorizedMessage(ClientThread client, String msg) {
        String[] arr = msg.split(Library.DELIMITER);
        int countSelUsers = arr.length-2;
        String[] arrUsers = new String[countSelUsers];
        String msgType = arr[0];
        System.arraycopy(arr, 1, arrUsers, 0, countSelUsers);
        switch (msgType) {
            case Library.TYPE_BCAST_CLIENT:
                sendToAllAuthorizedClients(
                        Library.getTypeBroadcast(client.getNickname(), arr[1]));
                break;
            case Library.TYPE_PRIVATE_CLIENT:
                sendToPrivateAuthorizedClient(client.getNickname(),arrUsers,arr[arr.length-1]);
                break;
            default:
                client.sendMessage(Library.getMsgFormatError(msg));
        }
    }

    private void sendToPrivateAuthorizedClient(String fromNickname, String[] toNicknames, String msg) {
        for (String toNickname : toNicknames) {
            for (SocketThread socketThread : clients) {
                ClientThread client = (ClientThread) socketThread;
                if (client.isAuthorized() && (toNickname.equals(client.getNickname()) || fromNickname.equals(client.getNickname())))
                    client.sendMessage(Library.getTypePrivateClient(fromNickname, msg));
            }
        }

    }

    private synchronized void sendToAllAuthorizedClients(String msg) {
        for (SocketThread socketThread : clients) {
            ClientThread client = (ClientThread) socketThread;
            if (!client.isAuthorized()) continue;
            client.sendMessage(msg);
        }
    }

    private synchronized String getUsers() {
        StringBuilder sb = new StringBuilder();
        for (SocketThread socketThread : clients) {
            ClientThread client = (ClientThread) socketThread;
            if (!client.isAuthorized()) continue;
            sb.append(client.getNickname()).append(Library.DELIMITER);
        }
        return sb.toString();
    }

    private synchronized ClientThread findClientByNickname(String nickname) {
        for (SocketThread socketThread : clients) {
            ClientThread client = (ClientThread) socketThread;
            if (!client.isAuthorized()) continue;
            if (client.getNickname().equals(nickname))
                return client;
        }
        return null;
    }

}
