import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class SendHttpPostUtil {

    /**
     * 向指定url发送post请求
     * @param sendUrl 指定的url
     * @param key 参数key
     * @param content 参数value
     * @return 响应的报文内容
     */
    private static String doHttpPost(String sendUrl, String key, String content) {
        String str = null;
        try {
            PostMethod httpPost = new PostMethod(sendUrl);
            HttpClient client = new HttpClient();
            client.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
            client.getParams().setParameter("http.protocol.content-charset", "UTF-8");
            client.getParams().setParameter("http.socket.timeout", 200 * 1000);
            NameValuePair[] data = {new NameValuePair(key, content)};
            httpPost.setRequestBody(data);
            client.executeMethod(httpPost);
            if (httpPost.getStatusCode() == HttpStatus.SC_OK) {
                String responseXml = httpPost.getResponseBodyAsString();
                httpPost.releaseConnection();
                str = responseXml;
                System.out.println("***return:\n " + responseXml);
            } else {
                System.out.println("***httpPost.getStatusCode() = " + httpPost.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void main(String[] args) {
        String sendUrl = "119.75.217.109";
        String key = "reqParam";
        String content = "{'userName':'admin','password':'123456'}";
        String msg = SendHttpPostUtil.doHttpPost(sendUrl, key, content);
        System.out.println("响应信息：" + msg);
    }
}
