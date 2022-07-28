package balancer.domain.repository;

import java.io.IOException;

public interface IClientRepository
{
    public String receiveRequest() throws IOException;

    public int receiveWriteRequest() throws IOException;

    public void sendReadRequest(String data) throws IOException;
}
