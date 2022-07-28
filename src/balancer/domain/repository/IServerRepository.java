package balancer.domain.repository;

import java.io.IOException;

public interface IServerRepository
{
    public String sendReadRequest() throws IOException;

    public void sendWriteRequest(int number) throws IOException;
}
