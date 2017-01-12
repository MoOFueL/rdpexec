package ru.seregamoskal.rdpExec.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.seregamoskal.rdpExec.domain.LoginInfo;
import ru.seregamoskal.rdpExec.services.LoginInfoService;

import java.io.IOException;

/**
 * @author Dmitriy
 */
@RestController
@RequestMapping(path = "logins", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LoginInfoController {

    private LoginInfoService loginInfoService;

    @Autowired
    public void setLoginInfoService(LoginInfoService loginInfoService) {
        Assert.notNull(loginInfoService);
        this.loginInfoService = loginInfoService;
    }

    @PostMapping(path = "/uploadCsv")
    @ResponseStatus(code = HttpStatus.OK)
    public void uploadFromCsv(MultipartFile logins) throws IOException {
        loginInfoService.parseFromCsv(logins);
    }

    @GetMapping
    public Page<LoginInfo> findAll(Pageable pageable) {
        return loginInfoService.findAll(pageable);
    }
}
