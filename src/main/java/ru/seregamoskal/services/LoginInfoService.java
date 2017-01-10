package ru.seregamoskal.services;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.seregamoskal.domain.LoginInfo;
import ru.seregamoskal.repositories.LoginInfoRepository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitriy
 */
@Service
public class LoginInfoService {

    private Logger LOGGER = LoggerFactory.getLogger(LoginInfoService.class);

    private static final String [] FILE_HEADER_MAPPING = {"login","password"};
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";


    private LoginInfoRepository loginInfoRepository;
    private CsvUtils csvUtils;

    @Autowired
    public void setCsvUtils(CsvUtils csvUtils) {
        this.csvUtils = csvUtils;
    }

    @Autowired
    public void setLoginInfoRepository(LoginInfoRepository loginInfoRepository) {
        this.loginInfoRepository = loginInfoRepository;
    }

    public void parseFromCsv(MultipartFile multipartFile) throws IOException {
        List<LoginInfo> loginInfos = parseCsvToLoginInfo(multipartFile);
        loginInfoRepository.save(loginInfos);
    }

    private List<LoginInfo> parseCsvToLoginInfo(MultipartFile multipartFile) throws IOException {
        final List<LoginInfo> result = new ArrayList<>(500);
        final CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);
        final Reader reader = new InputStreamReader(multipartFile.getInputStream());
        final CSVParser parser = new CSVParser(reader, csvFileFormat);
        parser.getRecords().forEach(record -> result.add(new LoginInfo(record.get(LOGIN), record.get(PASSWORD))));
        return result;
    }
}
