package com.example.spring.Service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service("WebService")
public class WebService {

    public String TestService() {

        return "test1";
    }

    /**
     * @param url
     * @return
     */
    public String UrlService(String url) {
        if (url.contains(".jpg") || url.contains(".png"))
            return "<textarea  cols=200><img src=\"" + url + "\"></textarea>";

        return "<textarea  cols=200><a href=\"" + url + "\" target=\"_blank\">" + url + "</a></textarea>";

    }

    /**
     * @param number
     * @return
     */
    public String LotteryService(String number) {

        //總獎金
        int TotalWinning = 100000000;

        //中獎號
        String WinningString = "1,2,3,4,5,6";
        String WinningNumber[] = WinningString.split(",");

        //特別號
        String SpecNumber = "3";

        //下注
        String SplitNumber[] = number.split(",");

        //特別號
        Boolean Spec = SplitNumber[6].equals(SpecNumber);

        //結果
        String Result;
        int Money;

        int howmuch = (Spec) ? 10 : 0;

        for (int i = 0; i < SplitNumber.length - 1; i++) {
            for (int j = 0; j < WinningNumber.length; j++)
                if (SplitNumber[i].equals(WinningNumber[j]))
                    howmuch++;

        }
        switch (howmuch) {
            case 11:
                Result = "Ninth";
                Money = 100;
                break;
            case 12:
                Result = "Eighth";
                Money = 200;
                break;
            case 13:
                Result = "Seventh";
                Money = 400;
                break;
            case 4:
                Result = "Sixth";
                Money = 800;
                break;
            case 14:
                Result = "Fifth";
                Money = 4000;
                break;
            case 5:
                Result = "Forth";
                Money = 20000;
                break;
            case 15:
                Result = "Third";
                Money = 150000;
                break;
            case 6:
                Result = "Second";
                Money = (int) (TotalWinning * 0.15);
                break;
            case 16:
                Result = "Jackpot";
                Money = (int) (TotalWinning * 0.85);
                break;
            default:
                Result = "Garbage";
                Money = 0;

        }

        return ("[" + Result + " Prize]" +
                "\n<br>" +
                "<Textarea cols=20 rows=1>you got $" + Money + "</Textarea>" +
                "\n<br>" +
                "\n<br>" +
                "[WinningNumber]" +
                "\n<br>" +
                "<Textarea cols=20 rows=1>[" + WinningString + "][" + SpecNumber + "]</Textarea>" +
                "\n<br>" +
                "\n<br>" +
                "[YourNumber]" +
                "\n<br>" +
                "<Textarea cols=20 rows=1>[" + number.substring(0, 11) + "][" + SplitNumber[6] + "]</Textarea>");

    }

    /**
     * @param url
     * @param obj
     * @return
     */
    public String PostService(String url, JSONObject obj) {

        try {

            CloseableHttpClient httpclient = HttpClients.createDefault();

            HttpGet httpGet = new HttpGet(url);
            //httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");

            // 解决中文乱码问题
            StringEntity stringEntity = new StringEntity(obj.toString(), "UTF-8");
            stringEntity.setContentEncoding("UTF-8");

            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(final HttpResponse response)
                        throws ClientProtocolException, IOException {//
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {

                        HttpEntity entity = response.getEntity();

                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException(
                                "Unexpected response status: " + status);
                    }
                }
            };
            String responseBody = httpclient.execute(httpGet, responseHandler);
            return responseBody;

        } catch (Exception e) {
            return e.toString();
        }

    }

    @Scheduled(fixedRate = 2000)
    public String ScheduledService() {

        System.out.println(new Date());
        return "null";
    }

    public String UploadService(HttpServletRequest request, String description, MultipartFile file) throws IOException {
// 获取文件描述参数 description，纯粹测试使用
        System.out.println("description = " + description);

        // 测试MultipartFile接口的各个方法
        System.out.println("文件类型ContentType=" + file.getContentType());
        System.out.println("文件组件名称Name=" + file.getName());
        System.out.println("文件原名称OriginalFileName=" + file.getOriginalFilename());
        System.out.println("文件大小Size=" + file.getSize() / 1024 + "KB");

        // 如果文件不为空，写入上传路径，进行文件上传
        if (!file.isEmpty()) {

            // 构建上传文件的存放路径
            String path = request.getServletContext().getRealPath("/upload/");
            System.out.println("path = " + path);

            // 获取上传的文件名称，并结合存放路径，构建新的文件名称
            String filename = file.getOriginalFilename();
            File filepath = new File(path, filename);

            // 判断路径是否存在，不存在则新创建一个
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }

            // 将上传文件保存到目标文件目录
            file.transferTo(new File(path + File.separator + filename));
            return "<img src=\""+"/upload/"+file.getOriginalFilename()+"\">" +
                    "<br><Textarea cols=200>"+"<img src=\"localhost:6663"+"/upload/"+file.getOriginalFilename()+"\">"+"</Textarea>";
        } else {
            return "error";
        }
    }

    public String SpiderService() {

        System.out.println(new Date());
        return "null";
    }
}




