package com.HospitalSystem_Pojo.Utils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeepSeekAPI {              //DeepSeek V3/R1 API

    private static final String URL = "https://api.deepseek.com";   //官网URL
    private static final String API_KEY = "**";            //官网KEY

    private static final String URL_Aliyun = "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions";  //阿里云URL
    private static final String API_KEY_Aliyun = "**";     //阿里云KEY

    private static ExecutorService threadPool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors(),
            0L,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(6));

    public static HashMap<String, Object> sendRequestToDeepSeek(String message, String departments_list) {
        Callable<String> task = () -> {
            String reply = null;
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost post = new HttpPost(URL_Aliyun);
                post.setHeader("Content-Type", "application/json");
                post.setHeader("Authorization", "Bearer " + API_KEY_Aliyun);

//                String jsonBody_R1 = String.format("{\"model\": \"deepseek-reasoner\", \"messages\": [{\"role\": \"system\", \"content\": \"现在你是一名医院中的导诊员，根据病人提出的症状来指导病人应该去看什么科室（重要），以及提供一些医疗建议（次要）\"}, {\"role\": \"user\", \"content\": \"%s\"}]}",
//                        message);
//                String jsonBody_V3 = String.format("{\"model\": \"deepseek-chat\", \"messages\": [{\"role\": \"system\", \"content\": \"现在你是一名医院中的导诊员，根据病人提出的症状来指导病人应该去看什么科室（重要），以及提供一些医疗建议（次要）\"}, {\"role\": \"user\", \"content\": \"%s\"}]}",
//                        message);
                String departments = "科室列表：" + departments_list;
                String pre_message = "现在你是医院中的一名导诊员，根据病人提出的问题，在以下科室列表中提出1个最适合病人就诊的科室(重要)并使用“【】”括号标注科室名，以及适当地提供一些医疗建议(次要) " + departments;
                String jsonBody_V3_Aliyun = String.format("{\"model\": \"deepseek-v3\", \"messages\": [{\"role\": \"system\", \"content\": \"%s\"}, {\"role\": \"user\", \"content\": \"%s\"}]}",
                        pre_message, message);

                post.setEntity(new StringEntity(jsonBody_V3_Aliyun, StandardCharsets.UTF_8));

                try (CloseableHttpResponse response = httpClient.execute(post)) {
                    JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
                    reply = jsonObject.getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content");
                }
                return reply;
            }
            catch (Exception e) {
                e.printStackTrace();
                return "请求DeepSeek时发生异常";
            }
        };

        Future<String> future = threadPool.submit(task);

        try {
            var response_message = future.get();
            HashMap<String, Object> data = new HashMap<>();
            data.put("message", response_message);
            data.put("departments", getRecommendDepartmentsName(response_message));

            return data;
        }
        catch (Exception e) {
            e.printStackTrace();
            HashMap<String, Object> data = new HashMap<>();
            data.put("message", "请求DeepSeek时发生异常");
            return data;
        }
    }

    //获取AI回复中推荐的科室名
    public static ArrayList<String> getRecommendDepartmentsName(String message) {
        ArrayList<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("【(.*?)】");
        Matcher matcher = pattern.matcher(message);

        while (matcher.find()) {
            result.add(matcher.group(1));
        }
        return result;
    }
}
