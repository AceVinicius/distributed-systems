package client.adapter.http;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import client.domain.repository.IBalancerRepository;

public class HttpBalancerAdapter implements IBalancerRepository
{
    private final Socket balancerSocket;
    public HttpBalancerAdapter(short balancerPort) throws IOException
    {
        this.balancerSocket = new Socket("127.0.0.1", balancerPort);
    }

    @Override
    public void sendWriteRequest(int number) throws IOException
    {
        DataOutputStream outputStream = new DataOutputStream(this.balancerSocket.getOutputStream());

        outputStream.writeUTF("WRITE");
        outputStream.writeInt(number);
        outputStream.close();
    }

    @Override
    public String sendReadRequest() throws IOException
    {
        DataInputStream inputStream = new DataInputStream(this.balancerSocket.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(this.balancerSocket.getOutputStream());

        outputStream.writeUTF("READ");
        String data = inputStream.readUTF();

        inputStream.close();
        outputStream.close();

        return data;
    }
}
