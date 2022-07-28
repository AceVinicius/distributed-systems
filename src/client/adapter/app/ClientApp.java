package client.adapter.app;

import java.io.IOException;

import client.adapter.http.HttpBalancerAdapter;
import client.application.RequestUseCase;
import client.application.dto.RequestDtoOutput;
import client.domain.repository.IBalancerRepository;

public class ClientApp extends Thread
{
    private static long identifier = 1;

    private final long id;
    private final short balancerPort;

    public ClientApp(short balancerPort)
    {
        this.id = identifier++;
        this.balancerPort = balancerPort;
    }

    public void run()
    {
        IBalancerRepository balancerRepository;
        RequestUseCase processRequest;
        RequestDtoOutput output;

        while (true) {
            
            try {
                balancerRepository = new HttpBalancerAdapter(this.balancerPort);
                processRequest = new RequestUseCase(balancerRepository);
                output = processRequest.execute();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            } catch (EnumConstantNotPresentException e) {
                System.out.println("[Client " + id + "] Enum " + e.getClass() + " returnd not valid value: " + e.getMessage());
                continue;
            }
            
            if (output.getRequestType() == "READ") {
                System.out.println("[Client " + id + "]\n" + output.getBody());
            }

            long miliseconds = generateRandomNumberBetween(100, 300);

            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
                System.out.println("[Client " + id + "] An Excetion occured while sleeping: " + e);
            }
        }
    }

    private long generateRandomNumberBetween(int min, int max)
    {
        return Math.round(Math.random() * (max - min + 1) + min);
    }
}
