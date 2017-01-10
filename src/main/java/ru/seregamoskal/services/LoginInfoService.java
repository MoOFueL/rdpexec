package ru.seregamoskal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import ru.seregamoskal.domain.LoginInfo;
import ru.seregamoskal.repositories.LoginInfoRepository;

import java.io.IOException;
import java.util.List;

/**
 * @author Dmitriy
 */
@Service
public class LoginInfoService {

    private LoginInfoRepository loginInfoRepository;
    private CsvUtils csvUtils;

    @Autowired
    public void setCsvUtils(CsvUtils csvUtils) {
        Assert.notNull(csvUtils);
        this.csvUtils = csvUtils;
    }

    @Autowired
    public void setLoginInfoRepository(LoginInfoRepository loginInfoRepository) {
        Assert.notNull(loginInfoRepository);
        this.loginInfoRepository = loginInfoRepository;
    }

    public void parseFromCsv(MultipartFile multipartFile) throws IOException {
        final List<LoginInfo> loginInfos = csvUtils.parseCsvToLoginInfo(multipartFile);
        loginInfoRepository.save(loginInfos);
    }
}
