package client.application;

import client.application.dto.RequestDtoOutput;
import client.domain.entity.Client;
import client.domain.repository.IBalancerRepository;
import client.domain.type.ERequestType;

public class RequestUseCase
{    
    private final IBalancerRepository balancerRepository;

    public RequestUseCase(IBalancerRepository balancerRepository)
    {
        this.balancerRepository = balancerRepository;
    }

    public RequestDtoOutput execute()
    {
        Client client = new Client();

        switch (client.getRequestType()) {
            case READ:
                return this.readRequest(client.getRequestType());
            
            case WRITE:
                return this.writeRequest(client.getRequestType(), client.raffleRandomNumber());
        
            default:
                throw new EnumConstantNotPresentException(ERequestType.class, client.getRequestType().toString());
        }
    }

    private RequestDtoOutput writeRequest(ERequestType requestType, int randomNumber)
    {
        try {
            balancerRepository.sendWriteRequest(randomNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return new RequestDtoOutput(requestType.toString(), null);
    }

    private RequestDtoOutput readRequest(ERequestType requestType)
    {
        String body = null;

        try {
            body = balancerRepository.sendReadRequest();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new RequestDtoOutput(requestType.toString(), body);
    }
}
