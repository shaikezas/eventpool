package com.eventpool.common.exceptions;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * User: praneethgayam
 * Date: 19/07/12
 */
public class MessageFormatter {

    public static final String BASE_PACKAGE = "com.hs18.";

    public static final String ERRORS_FILE_NAME = "errors";

    public static String formatMessage(String code, Object... args) {
        String namespace = code.substring(0, code.lastIndexOf(".") + 1);
        ResourceBundle bundle = ResourceBundle.getBundle(BASE_PACKAGE + namespace + ERRORS_FILE_NAME);
        return MessageFormat.format(bundle.getString(code), args);
    }

}
