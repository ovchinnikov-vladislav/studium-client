package ru.kamchatgtu.studium.entity;

import javafx.beans.property.*;

import java.util.Date;
import java.util.Objects;

public class Log {

    private IntegerProperty idLog;
    private StringProperty codeQuery;
    private StringProperty typeQuery;
    private ObjectProperty<Date> dateQuery;
    private StringProperty textQuery;
    private StringProperty tableName;

    public Log() {
        idLog = new SimpleIntegerProperty();
        codeQuery = new SimpleStringProperty();
        typeQuery = new SimpleStringProperty();
        dateQuery = new SimpleObjectProperty<>();
        textQuery = new SimpleStringProperty();
        tableName = new SimpleStringProperty();
    }

    public int getIdLog() {
        return idLog.get();
    }

    public IntegerProperty idLogProperty() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog.set(idLog);
    }

    public String getCodeQuery() {
        return codeQuery.get();
    }

    public StringProperty codeQueryProperty() {
        return codeQuery;
    }

    public void setCodeQuery(String codeQuery) {
        this.codeQuery.set(codeQuery);
    }

    public String getTypeQuery() {
        return typeQuery.get();
    }

    public StringProperty typeQueryProperty() {
        return typeQuery;
    }

    public void setTypeQuery(String typeQuery) {
        this.typeQuery.set(typeQuery);
    }

    public Date getDateQuery() {
        return dateQuery.get();
    }

    public ObjectProperty<Date> dateQueryProperty() {
        return dateQuery;
    }

    public void setDateQuery(Date dateQuery) {
        this.dateQuery.set(dateQuery);
    }

    public String getTextQuery() {
        return textQuery.get();
    }

    public StringProperty textQueryProperty() {
        return textQuery;
    }

    public void setTextQuery(String textQuery) {
        this.textQuery.set(textQuery);
    }

    public String getTableName() {
        return tableName.get();
    }

    public StringProperty tableNameProperty() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName.set(tableName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Log log = (Log) o;
        return Objects.equals(idLog.get(), log.idLog.get()) &&
                Objects.equals(codeQuery.get(), log.codeQuery.get()) &&
                Objects.equals(typeQuery.get(), log.typeQuery.get()) &&
                Objects.equals(dateQuery.get(), log.dateQuery.get()) &&
                Objects.equals(textQuery.get(), log.textQuery.get()) &&
                Objects.equals(tableName.get(), log.tableName.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLog.get(), codeQuery.get(), typeQuery.get(), dateQuery.get(), textQuery.get(), tableName.get());
    }
}
