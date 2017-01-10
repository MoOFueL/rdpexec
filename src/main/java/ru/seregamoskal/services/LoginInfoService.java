package ru.seregamoskal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import ru.seregamoskal.domain.LoginInfo;
import ru.seregamoskal.repositories.LoginInfoRepository;
import ru.seregamoskal.services.util.CsvService;

import java.io.IOException;
import java.util.List;

/**
 * @author Dmitriy
 */
@Service
public class LoginInfoService {

    private LoginInfoRepository loginInfoRepository;
    private CsvService csvService;

    @Autowired
    public void setCsvService(CsvService csvService) {
        Assert.notNull(csvService);
        this.csvService = csvService;
    }

    @Autowired
    public void setLoginInfoRepository(LoginInfoRepository loginInfoRepository) {
        Assert.notNull(loginInfoRepository);
        this.loginInfoRepository = loginInfoRepository;
    }

    public void parseFromCsv(MultipartFile multipartFile) throws IOException {
        final List<LoginInfo> parsedLoginInfos = csvService.parseCsvToLoginInfoList(multipartFile);
        loginInfoRepository.save(parsedLoginInfos);
    }

    public Page<LoginInfo> findAll(Pageable pageable) {
        return loginInfoRepository.findAll(pageable);
    }
}
