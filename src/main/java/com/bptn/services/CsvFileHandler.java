package com.bptn.services;

import com.bptn.models.AppUser;
import com.bptn.models.Person;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CsvFileHandler {
    public Map<String, AppUser> loadFile(final String filePath) throws IOException;

    public void writeToFile(List<Person> person) throws IOException;
}
