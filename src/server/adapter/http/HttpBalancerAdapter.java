package server.adapter.http;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import server.domain.repository.IBalancerRepository;

public class HttpBalancerAdapter implements IBalancerRepository
{
    private final Socket balancerSocket;

    public HttpBalancerAdapter(Socket balancerSocket) throws IOException
    {
        this.balancerSocket = balancerSocket;
    }

    @Override
    public String receiveRequest() throws IOException
    {
        DataInputStream inputStream = new DataInputStream(this.balancerSocket.getInputStream());
        
        String data = inputStream.readUTF();
        // inputStream.close();

        return data;
    }

    @Override
    public int receiveWriteRequest() throws IOException
    {
        DataInputStream inputStream = new DataInputStream(this.balancerSocket.getInputStream());
        
        int number = inputStream.readInt();
        // inputStream.close();

        return number;
    }

    @Override
    public void sendReadRequest(String data) throws IOException
    {
        DataOutputStream outputStream = new DataOutputStream(this.balancerSocket.getOutputStream());
        
        outputStream.writeUTF(data);
        // outputStream.close();
    }
}
