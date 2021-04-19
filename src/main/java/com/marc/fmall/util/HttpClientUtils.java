package com.marc.fmall.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class HttpClientUtils {
    public static String sendGet(String url, Map<String, ?> paramMap) {

        // 打开代理


        HttpURLConnection conn = null;  // 默认返回HTTP，用爸爸接收返回数据
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            conn = (HttpURLConnection) realUrl.openConnection();  // 返回对象为 HttpsURLConnection,儿子的儿子
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 超时设置,防止 网络异常的情况下,可能会导致程序僵死而不继续往下执行
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (IOException ex) {
                log.error("GET方法调用第三方API出错");
                ex.printStackTrace();
            }
        }
        return result;  // 返回的为JSON字符串


    }
    public static String sendPost(String url, Map<String, ?> paramMap) {

// 打开代理

        HttpURLConnection conn = null;  // 默认返回HTTP，用爸爸接收返回数据
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String param = "";
        if (paramMap != null) {
            Iterator<String> it = paramMap.keySet().iterator();

            while (it.hasNext()) {
                String key = it.next();
                param += key + "=" + paramMap.get(key) + "&";
            }
        }
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            conn = (HttpURLConnection) realUrl.openConnection();  // 返回对象为 HttpsURLConnection,儿子的儿子
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 超时设置,防止 网络异常的情况下,可能会导致程序僵死而不继续往下执行
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("POST调用第三方API出错");
//            log.error(e.getMessage(), e);
        }
        //使用finally块来关闭输出流、输入流
        finally {
//关闭代理
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;  // 返回的为JSON字符串


    }
}
