package server.domain.entity;

public class Prime
{
    private final int number;
    private String message;
    
    public Prime(int number)
    {
        if (number < 2) {
            throw new IllegalArgumentException("[Server] The number must be greater than 2");
        }

        if (number > 1000000) {
            throw new IllegalArgumentException("[Server] The number must be smaller than 1.000.000");
        }

        this.number = number;
    }

    public boolean isPrime()
    {
        if (this.number == 2) {
            this.message = "O número " + this.number + " é primo\n";
            return true;
        }
        
        if (this.number % 2 == 0) {
            this.message = "O número " + this.number + " não é primo\n";
            return false;
        }

        for (int i = 3; i <= Math.sqrt(this.number); i += 2) {
            if (this.number % i == 0) {
                this.message = "O número " + this.number + " não é primo\n";
                return false;
            }
        }

        this.message = "O número " + this.number + " é primo\n";
        return true;
    }

    public String getMessage()
    {
        if (this.message == null) {
            this.isPrime();
        }

        return this.message;
    }
}
