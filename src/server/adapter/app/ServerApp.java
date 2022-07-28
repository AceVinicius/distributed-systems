package server.adapter.app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import server.adapter.database.FileAdapter;
import server.adapter.http.HttpBalancerAdapter;
import server.application.ServerUseCase;
import server.domain.repository.IBalancerRepository;
import server.domain.repository.IStorageRepository;

public class ServerApp extends Thread
{
    private static long identifier = 1;

    private final long id;
    private final ServerSocket serverServerSocket;

    public ServerApp(short serverPort) throws IOException
    {
        this.id = identifier++;
        this.serverServerSocket = new ServerSocket(serverPort);
    }

    public void run()
    {
        Socket balancerSocket;
        ServerUseCase handler;
        IStorageRepository storageRepository;
        IBalancerRepository balancerRepository;

        try {
            storageRepository = new FileAdapter(this.id);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (true) {
            try {
                balancerSocket = serverServerSocket.accept();
                balancerRepository = new HttpBalancerAdapter(balancerSocket);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            handler = new ServerUseCase(balancerRepository, storageRepository);
            handler.start();
            handler.setName("Server " + this.id + " Handler");
        }
    }
}
