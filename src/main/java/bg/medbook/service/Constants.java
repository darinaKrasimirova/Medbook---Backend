package bg.medbook.service;

import java.time.format.DateTimeFormatter;

public final class Constants {
    public static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TIME_PATTERN = DateTimeFormatter.ofPattern("HH:mm");

}
