package balancer.adapter.http;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import balancer.domain.repository.IClientRepository;

public class HttpClientAdapter implements IClientRepository
{
    private final Socket clientSocket;

    public HttpClientAdapter(Socket clientSocket) throws IOException
    {
        this.clientSocket = clientSocket;
    }

    @Override
    public String receiveRequest() throws IOException
    {
        DataInputStream inputStream = new DataInputStream(this.clientSocket.getInputStream());
        
        String data = inputStream.readUTF();
        // inputStream.close();

        return data;
    }

    @Override
    public int receiveWriteRequest() throws IOException
    {
        DataInputStream inputStream = new DataInputStream(this.clientSocket.getInputStream());
        
        int number = inputStream.readInt();
        // inputStream.close();

        return number;
    }

    @Override
    public void sendReadRequest(String data) throws IOException
    {
        DataOutputStream outputStream = new DataOutputStream(this.clientSocket.getOutputStream());
        
        outputStream.writeUTF(data);
        outputStream.close();
    }
}
