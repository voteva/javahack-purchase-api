package com.purchase.admin.utils;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import java.io.IOException;

public class EmailProcessing {

    public static String parseContent(Part part) throws MessagingException, IOException {
        if (part.isMimeType("text/plain")) {
            return (String) part.getContent();
        } else if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            StringBuilder sb = new StringBuilder();
            int count = mp.getCount();
            for (int i = 0; i < count; i++) {
                sb.append(parseContent(mp.getBodyPart(i)));
            }
            return sb.length() == 0 ? null : sb.toString();
        }
        return null;
    }
}
