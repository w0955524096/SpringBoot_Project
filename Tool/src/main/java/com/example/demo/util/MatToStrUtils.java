package com.example.demo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class MatToStrUtils {

    /**
     * mapToStr
     *
     * @param params              Map資料
     * @param isIgnoreBlankOrNull 是否忽略空白Key
     * @return 回傳拼接字串，e.x. key1=value1&key2=value2
     */

    //jsonObjectToStr
    private static String jsonObjectKeySortToLinkString(Map<String, Object> params, boolean isIgnoreBlankOrNull) throws UnsupportedEncodingException {
        ArrayList keys = new ArrayList(params.keySet());
        Collections.sort(keys);
        StringBuilder sb = new StringBuilder();

        for (String key : params.keySet()) {
            String value = (String) params.get(key);
            if ((isIgnoreBlankOrNull || !"".equals(value) && value.trim().length() != 0)) {
                sb.append(key).append("=").append(URLEncoder.encode(value, "utf-8")).append("&");
            }
        }

        return String.valueOf(sb);
    }

}
