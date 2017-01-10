package ru.seregamoskal.domain;

import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Dmitriy
 */
@Entity
@Table(name = "server_info")
public class ServerInfo {

    @Id
    @SequenceGenerator(name = "server_info_id_seq", sequenceName = "server_info_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "server_info_id_seq")
    @Column(name = "id", updatable = false)
    private Long id;

    /**
     * Имя сервера(опционально)
     */
    private String name;

    /**
     * Адрес сервера
     */
    private String address;

    /**
     * Ссылка на пару логин-пароль
     */
    @OneToOne
    @JoinColumn(name = "loginInfo")
    private LoginInfo loginInfo;

    /**
     * Признак доступности(работает или нет)
     */
    private boolean working;

    @Column(name = "date_of_last_access")
    private Date dateOfLastAccess;

    @OneToMany
    @JoinColumn(name = "id")
    @OrderBy("date DESC")
    private Set<Operation> operations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public Date getDateOfLastAccess() {
        return dateOfLastAccess;
    }

    public void setDateOfLastAccess(Date dateOfLastAccess) {
        this.dateOfLastAccess = dateOfLastAccess;
    }

    public Set<Operation> getOperations() {
        if (CollectionUtils.isEmpty(operations)) {
            operations = new HashSet<>();
        }
        return operations;
    }

    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", loginInfo=" + loginInfo +
                ", working=" + working +
                ", dateOfLastAccess=" + dateOfLastAccess +
                '}';
    }
}
