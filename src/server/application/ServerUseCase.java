package server.application;

import java.io.IOException;

import server.domain.entity.Prime;
import server.domain.repository.IBalancerRepository;
import server.domain.repository.IStorageRepository;

public class ServerUseCase extends Thread
{
    private final IBalancerRepository balancerRepository;
    private final IStorageRepository storageRepository;

	public ServerUseCase(IBalancerRepository balancerRepository, IStorageRepository storageRepository)
    {
        this.balancerRepository = balancerRepository;
        this.storageRepository = storageRepository;
	}
    
    public void run()
    {
        String request;

        try {
            request = this.balancerRepository.receiveRequest();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        switch (request) {
            case "WRITE":
                this.writeRequest();
                break;

            case "READ":
                this.readRequest();
                break;
        
            default:
                break;
        }
    }

    private void writeRequest()
    {
        try
        {
            Prime prime = new Prime(this.balancerRepository.receiveWriteRequest());
            this.storageRepository.write(prime.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readRequest()
    {
        
        try
        {
            String data = this.storageRepository.read();
            this.balancerRepository.sendReadRequest(data);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
