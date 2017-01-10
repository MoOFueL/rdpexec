package ru.seregamoskal.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.seregamoskal.domain.LoginInfo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitriy
 */
@Service
public class CsvUtils {

    private static final String[] FILE_HEADER_MAPPING = {"login", "password"};
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    private Logger LOGGER = LoggerFactory.getLogger(LoginInfoService.class);

    public List<LoginInfo> parseCsvToLoginInfo(MultipartFile multipartFile) throws IOException {

        LOGGER.info("Starting parsing CSV file with login-password pairs");
        final List<LoginInfo> result = new ArrayList<>(500);
        final CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);
        final Reader reader = new InputStreamReader(multipartFile.getInputStream());
        final CSVParser parser = new CSVParser(reader, csvFileFormat);
        final List<CSVRecord> records = parser.getRecords();
        records.forEach(record -> result.add(new LoginInfo(record.get(LOGIN), record.get(PASSWORD))));
        LOGGER.info("Finished parsing CSV file. Number of lines is: " + records.size());

        return result;
    }
}
