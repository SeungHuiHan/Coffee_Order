package edu.example.gccoffee.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    public void testEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            Email email=new Email("acccsv");

        });
    }

    @Test
    public void testValidEmail() {
        Email email=new Email("accccc@naver.com");
        assertTrue(email.getAddress().contains("accccc@naver.com"));

    }

    @Test
    public void testEqEmail() {
        Email email1=new Email("accccc@naver.com");
        Email email2=new Email("accccc@naver.com");
        assertTrue(email1.equals(email2));

    }

}