package com.bakiye.veresiye_defteri.exceptions;

public class ResponseUtil {

    public static SuccessResponse buildSuccessResponse(String message) {
        return new SuccessResponse(true, message);
    }

    public static SuccessResponse buildSuccessResponse() {
        return new SuccessResponse(true, "Success");
    }

    public static SuccessResponse buildErrorResponse(String message) {
        return new SuccessResponse(false, message);
    }
}

