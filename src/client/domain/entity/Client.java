package client.domain.entity;

import client.domain.type.ERequestType;

public class Client
{
    private final ERequestType requestType;

    public Client()
    {
        this.requestType = raffleRequest();
    }

    public ERequestType getRequestType()
    {
        return requestType;
    }

    public int raffleRandomNumber()
    {
        if (requestType == ERequestType.READ) {
            throw new IllegalArgumentException("Read requests cannot generate random numbers");
        }

        return generateRandomNumberBetween(2, 1000000);
    }

    private ERequestType raffleRequest()
    {
        int number = generateRandomNumberBetween(0, 1);

        return (number == 0) ? ERequestType.READ : ERequestType.WRITE;
    }

    private int generateRandomNumberBetween(int min, int max)
    {
        return (int) Math.round(Math.random() * (max - min + 1) + min);
    }
}