package com.bptn.services;

import com.bptn.models.AppUser;
import com.bptn.models.Person;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CsvAppUserLoader implements CsvFileHandler{
    @Override
    public Map<String,Person> loadFile (String filePath) throws IOException {
        Map<String,Person> appUsers = new HashMap<>();
        try (Reader reader = new FileReader(filePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                    .setHeader().setSkipHeaderRecord(true)
                    .build().parse(reader);
            for (CSVRecord record : records){
                String firstName = record.get("firstName");
                String lastName = record.get("lastName");
                int id = Integer.parseInt(record.get("ID"));
                String username = record.get("username");
                String passwordHash = record.get("password");
                String email = record.get("email");
                String avatarUrl = record.get("avatarUrl");

                Person appUser = new AppUser(firstName,lastName,username,passwordHash,id,avatarUrl);
                appUsers.put(username,appUser);
            }
        }


        return appUsers;
    }

    @Override
    public void writeToFile (List<Person> person) throws IOException {

    }
}
