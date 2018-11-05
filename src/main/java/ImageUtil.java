
import org.apache.commons.io.IOUtils;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ImageUtil {

    /**
     * @Description 双解密图片，base64+desKey，先desKey解密，再base64解密
     * @param sourcePath 加密图片路径
     * @param picKey 图片密钥
     * @param targetPath 解密后图片路径
     */
    private static void Decode(String sourcePath, String picKey, String targetPath){
        try {
            File file = new File(sourcePath);
            FileInputStream fis = new FileInputStream(file);
            String picStr = IOUtils.toString(fis, "ISO8859-1");
            // des解密
            RealNameMsDesPlus desPlus = new RealNameMsDesPlus(picKey);
            String picStrDecode = desPlus.decrypt(picStr);
            // base64解密
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(picStrDecode);
            // 写入本地
            OutputStream out = new FileOutputStream(targetPath);
            out.write(bytes);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String sourcePath = "D:\\test\\BOSS371108520180918152341974578_F.jpg";
        String targetPath = "D:\\test\\DecyImage\\BOSS371108520180918152341974578_F.jpg";// 需要手动创建该目录
        String desKey = "DNCUK";
        Decode(sourcePath, desKey, targetPath);
        System.out.println("图片解密成功！");
    }

}
