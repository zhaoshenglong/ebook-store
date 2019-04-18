package org.iBookStore.servlet.utility;

import javax.servlet.http.HttpServletResponse;

public class CORS {
    public static void setCORS(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
    }
}
