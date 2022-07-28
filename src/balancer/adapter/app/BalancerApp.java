package balancer.adapter.app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import balancer.adapter.http.HttpClientAdapter;
import balancer.adapter.http.HttpServerAdapter;
import balancer.domain.repository.IClientRepository;
import balancer.domain.repository.IServerRepository;

public class BalancerApp extends Thread
{
    private final ServerSocket balancerServerSocket;
    private final Queue<Short> servers;
    private final List<Short> serverPorts;

    public BalancerApp(short port, List<Short> servers) throws IOException
    {
        this.balancerServerSocket = new ServerSocket(port);
        this.servers = new ConcurrentLinkedQueue<>(servers);
        this.serverPorts = servers;
    }

    public void run()
    {
        while (true) {
            String request;
            Socket clientSocket;
            IClientRepository clientRepository;

            try {
                clientSocket = balancerServerSocket.accept();
                clientRepository = new HttpClientAdapter(clientSocket);

                request = clientRepository.receiveRequest();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            switch (request) {
                case "WRITE":
                    this.writeRequest(clientRepository);
                    break;

                case "READ":
                    this.readRequest(clientRepository);
                    break;
            
                default:
                    break;
            }
        }
    }

    private void writeRequest(IClientRepository clientRepository)
    {
        try
        {
            int number = clientRepository.receiveWriteRequest();

            for (Short port : serverPorts) {
                IServerRepository serverRepository = new HttpServerAdapter(port);
                serverRepository.sendWriteRequest(number);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readRequest(IClientRepository clientRepository)
    {
        try
        {
            IServerRepository serverRepository = new HttpServerAdapter(servers.peek());

            servers.add(servers.poll());

            String content = serverRepository.sendReadRequest();
            clientRepository.sendReadRequest(content);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
