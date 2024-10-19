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
    public Map<String, AppUser> loadFile (String filePath) throws IOException {
        Map<String,AppUser> appUsers = new HashMap<>();
        try (Reader reader = new FileReader(filePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                    .setHeader().setSkipHeaderRecord(true)
                    .build().parse(reader);
            for (CSVRecord record : records){
                String firstName = record.get("first_name");
                String lastName = record.get("last_name");
                int id = Integer.parseInt(record.get("id"));
                String username = record.get("username");
                String passwordHash = record.get("password");
                String email = record.get("email");
                String avatarUrl = record.get("avatar_url");

                AppUser appUser = new AppUser(firstName,lastName,username, email,passwordHash,id,avatarUrl);
                appUsers.put(username,appUser);
            }
        }


        return appUsers;
    }

    @Override
    public void writeToFile (List<Person> IPeople) throws IOException {

    }
}
