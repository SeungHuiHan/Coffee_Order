package edu.example.gccoffee.util;


import java.sql.Timestamp;
import java.time.LocalDateTime;

public class JdbcUtils {
    static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp!=null?timestamp.toLocalDateTime():null;
    }
}
