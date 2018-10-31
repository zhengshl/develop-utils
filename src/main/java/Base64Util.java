import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Base64Util {
    private static String getImageStr(String imagePath){
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imagePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data != null ? data : new byte[0]);
    }

    public static void main(String[] args) {
        String imagePath = "D:\\Z.jpg";
        String imageStr = getImageStr(imagePath);
        System.out.println("Base64图片流：" + imageStr);
    }
}
