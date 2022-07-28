package client.application.dto;

public class RequestDtoOutput
{
    private final String requestType;
    private final String body;

    public RequestDtoOutput(String requestType, String body)
    {
        this.requestType = requestType;
        this.body = body;
    }

    public String getRequestType()
    {
        return requestType;
    }

    public String getBody()
    {
        return body;
    }
}
