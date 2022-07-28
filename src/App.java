import java.util.ArrayList;
import java.util.List;

import balancer.adapter.app.BalancerApp;
import client.adapter.app.ClientApp;
import server.adapter.app.ServerApp;

public class App
{
    private final static short balancerPort = 30000;
    private final static short serverPort = 32000;

    private final static int clientCount = 3;
    private final static int serverCount = 3;
    
    public static void main(String[] args) throws Exception
    {
        List<Short> serverPorts = new ArrayList<>();

        for (short i = 0; i < serverCount; i++) {
            short port  = (short) (serverPort + i);
            serverPorts.add(port);

            ServerApp server = new ServerApp(port);
            server.start();
            server.setName("Server " + (i+1));
        }

        BalancerApp balancer = new BalancerApp(balancerPort, serverPorts);
        balancer.start();
        balancer.setName("Balancer");

        for (short i = 0; i < clientCount; i++) {
            ClientApp client = new ClientApp(balancerPort);
            client.start();
            client.setName("Client " + (i+1));
        }
    }
}
