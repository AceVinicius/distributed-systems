package client.domain.repository;

import java.io.IOException;

public interface IBalancerRepository
{
    public void sendWriteRequest(int number) throws IOException;

    public String sendReadRequest() throws IOException;
}
