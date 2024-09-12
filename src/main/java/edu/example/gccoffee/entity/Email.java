package edu.example.gccoffee.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
@ToString
public class Email {
    private String email;

    public Email(String address) {
        Assert.notNull(address, "Email address must not be null");
        Assert.isTrue(address.length() >=0  && address.length()<=50, "Email address must be between 4 and 50 characters");

        Assert.isTrue(checkAddress(address),"Invalid email address");
        this.email = address;
    }

    private static boolean checkAddress(String address) {
        return Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b",address);

    }
}
