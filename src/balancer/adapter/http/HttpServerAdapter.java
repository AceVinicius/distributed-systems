package balancer.adapter.http;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import balancer.domain.repository.IServerRepository;

public class HttpServerAdapter implements IServerRepository
{
    private final Socket serverSocket;

    public HttpServerAdapter(short serverPort) throws IOException
    {
        this.serverSocket = new Socket("127.0.0.1", serverPort);
    }
    
    @Override
    public String sendReadRequest() throws IOException
    {
        DataInputStream inputStream = new DataInputStream(this.serverSocket.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(this.serverSocket.getOutputStream());

        outputStream.writeUTF("READ");
        String data = inputStream.readUTF();

        // inputStream.close();
        // outputStream.close();

        return data;
    }
    
    @Override
    public void sendWriteRequest(int number) throws IOException
    {
        DataOutputStream outputStream = new DataOutputStream(this.serverSocket.getOutputStream());

        outputStream.writeUTF("WRITE");
        outputStream.writeInt(number);

        // outputStream.close();
    }
}
