package ru.seregamoskal.rdpexec.services.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.seregamoskal.rdpexec.domain.LoginInfo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitriy
 */
@Service
public class CsvService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvService.class);

    private static final String LOGIN_HEADER = "login";
    private static final String PASSWORD_HEADER = "password";
    private static final String[] FILE_HEADER_MAPPING = {LOGIN_HEADER, PASSWORD_HEADER};

    /**
     * Метод принимает на вход {@link MultipartFile} CSV-файл и парсит его для создания списка объектов {@link LoginInfo}
     *
     * @param multipartFile - CSV-файл для парсинга
     * @return - список полученных {@link LoginInfo}
     * @throws IOException - при создании парсера или при парсинге файла
     */
    public List<LoginInfo> parseCsvToLoginInfoList(MultipartFile multipartFile) throws IOException {

        LOGGER.info("Starting parsing CSV file with login-password pairs");
        final List<LoginInfo> result = new ArrayList<>(100);
        final CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING).withIgnoreHeaderCase();
        final Reader reader = new InputStreamReader(multipartFile.getInputStream());
        final CSVParser parser = new CSVParser(reader, csvFileFormat);
        final List<CSVRecord> records = parser.getRecords();
        records.forEach(record -> result.add(new LoginInfo(record.get(LOGIN_HEADER), record.get(PASSWORD_HEADER))));
        LOGGER.info("Finished parsing CSV file. Number of records is: " + records.size());

        return result;
    }
}
