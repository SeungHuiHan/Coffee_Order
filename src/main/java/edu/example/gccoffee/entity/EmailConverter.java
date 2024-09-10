package edu.example.gccoffee.entity;

import jakarta.persistence.AttributeConverter;

//Email 클래스의 인스턴스를 데이터베이스에서 저장하고 조회할 수 있도록 변환기를 정의
public class EmailConverter implements AttributeConverter<Email, String> {
    @Override
    public String convertToDatabaseColumn(Email email) {
        return email != null ? email.getAddress() : null;
    }

    @Override
    public Email convertToEntityAttribute(String dbData) {
        return dbData != null ? new Email(dbData) : null;
    }
}

