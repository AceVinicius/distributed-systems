package server.adapter.database;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import server.domain.repository.IStorageRepository;

public class FileAdapter implements IStorageRepository
{
    private final File file;

    public FileAdapter(long serverId) throws IOException
    {
        this.file = new File("src/server/adapter/database/server-" + serverId + "-db.txt");
        this.file.createNewFile();
    }
    
    @Override
    public void write(String data) throws IOException
    {
        FileWriter fileWriter = new FileWriter(this.file, true);

        fileWriter.write(data);
        fileWriter.close();
    }
    
    @Override
    public String read() throws IOException
    {
        FileReader fileReader = new FileReader(this.file);
        
        String data = "";
        int i;

        while ((i = fileReader.read()) != -1) {
            data += (char) i;
        }

        fileReader.close();

        return data;
    }
}
