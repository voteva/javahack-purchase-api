package com.purchase.admin.utils;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UUIDParser {
    private static final Pattern pattern =
            Pattern.compile("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})");

    public static UUID parse(String content) {
        if (content == null) {
            return null;
        }

        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return UUID.fromString(matcher.group());
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(parse("Идентификатор клиента 81c578f9-5e6d-4d78-8729-f32acefc144b с увожением"));;
    }
}
