package server.domain.repository;

import java.io.IOException;

public interface IStorageRepository
{
    public void write(String data) throws IOException;

    public String read() throws IOException;
}
