package ru.seregamoskal.rdpExec.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Дмитрий on 10.01.2017.
 */
@Entity
@Table(name = "operation")
public class Operation {

    @Id
    @SequenceGenerator(name = "operation_id_seq", sequenceName = "operation_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operation_id_seq")
    @Column(name = "id", updatable = false)
    private Long id;
    /**
     * Текст операции(команды)
     */
    private String text;

    /**
     * Результат выполнения команды
     */
    private String result;

    /**
     * Дата выполнения команды
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date date;

    /**
     * Признак положительного завершения операции
     */
    private boolean wentOk;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isWentOk() {
        return wentOk;
    }

    public void setWentOk(boolean wentOk) {
        this.wentOk = wentOk;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", result='" + result + '\'' +
                ", date=" + date +
                ", wentOk=" + wentOk +
                '}';
    }
}
