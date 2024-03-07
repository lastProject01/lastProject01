package com.project.Enovel.global.util;


import org.apache.catalina.util.URLEncoder;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class Ut {
    public static class exception {
        public static String toString(Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String stackTrace = sw.toString();

            StringBuilder details = new StringBuilder();

            // 예외 메시지 추가
            details.append("Exception Message: ").append(e.getMessage()).append("\n");

            // 예외 원인 추가
            Throwable cause = e.getCause();
            if (cause != null) {
                details.append("Caused by: ").append(cause.toString()).append("\n");
            }

            // 스택 트레이스 추가
            details.append("Stack Trace:\n").append(stackTrace);

            return details.toString();
        }
    }

    public static class match {
        public static boolean isTrue(Boolean bool) {
            return bool != null && bool;
        }

        public static boolean isFalse(Boolean bool) {
            return bool != null && !bool;
        }
    }

    public static class date {
        private date() {
        }

        public static String getCurrentDateFormatted(String pattern) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(new Date());
        }

        public static String getCurrentYearMonth() {
            return getCurrentDateFormatted("yyyy-MM");
        }

        public static int getEndDayOf(int year, int month) {
            String yearMonth = year + "-" + "%02d".formatted(month);

            return getEndDayOf(yearMonth);
        }

        public static int getEndDayOf(String yearMonth) {
            LocalDate convertedDate = LocalDate.parse(yearMonth + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            convertedDate = convertedDate.withDayOfMonth(
                    convertedDate.getMonth().length(convertedDate.isLeapYear()));

            return convertedDate.getDayOfMonth();
        }

        public static LocalDateTime parse(String pattern, String dateText) {
            return LocalDateTime.parse(dateText, DateTimeFormatter.ofPattern(pattern));
        }

        public static LocalDateTime parse(String dateText) {
            return parse("yyyy-MM-dd HH:mm:ss.SSSSSS", dateText);
        }
    }



    public static class str {

        public static String lcfirst(String str) {
            if (str == null || str.isEmpty()) {
                return str;
            }
            return str.substring(0, 1).toLowerCase() + str.substring(1);
        }

        public static boolean hasLength(String string) {
            return string != null && !string.trim().isEmpty();
        }

        public static boolean isBlank(String string) {
            return !hasLength(string);
        }
    }

    public static class url {
        public static String modifyQueryParam(String url, String paramName, String paramValue) {
            url = deleteQueryParam(url, paramName);
            url = addQueryParam(url, paramName, paramValue);

            return url;
        }

        public static String addQueryParam(String url, String paramName, String paramValue) {
            if (!url.contains("?")) {
                url += "?";
            }

            if (!url.endsWith("?") && !url.endsWith("&")) {
                url += "&";
            }

            url += paramName + "=" + paramValue;

            return url;
        }

        public static String deleteQueryParam(String url, String paramName) {
            int startPoint = url.indexOf(paramName + "=");
            if (startPoint == -1) return url;

            int endPoint = url.substring(startPoint).indexOf("&");

            if (endPoint == -1) {
                return url.substring(0, startPoint - 1);
            }

            String urlAfter = url.substring(startPoint + endPoint + 1);

            return url.substring(0, startPoint) + urlAfter;
        }

        public static String encode(String url) {
            return new URLEncoder().encode(url, StandardCharsets.UTF_8);
        }
    }
}
