import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class GZIPUtil {
    /**
     * 将一组文件打成tar包
     * @param files 需要打包的文件
     * @param target 打包后输出的文件（带目录）
     * @return 打包后的tar包文件
     */
    private static File pack(File[] files, File target){
        try {
            FileOutputStream fos = new FileOutputStream(target);
            TarArchiveOutputStream taros = new TarArchiveOutputStream(fos);
            for (File file : files) {
                taros.putArchiveEntry(new TarArchiveEntry(file));
                IOUtils.copy(new FileInputStream(file), taros);
                taros.closeArchiveEntry();
            }
            taros.flush();
            taros.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return target;
    }

    /**
     * 将tar包压缩成gz包
     * @param source 需要打包的tar包
     * @param path 指定tar.gz包的输出目录
     */
    private static void compress(File source, String path){
        File file = new File(path + source.getName() + ".gz");
        FileInputStream fis = null;
        GZIPOutputStream gos = null;
        try {
            fis = new FileInputStream(source);
            gos = new GZIPOutputStream(new FileOutputStream(file));
            byte[] bytes = new byte[1024*10];
            int len;
            while ((len = fis.read(bytes)) != -1) {
                gos.write(bytes, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (gos != null) {
                    gos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String path = "D:\\test\\";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        String targetPath = path + "3711085_PIC20180803104820000001.tar";
        File[] files = new File[]{new File("D:\\371_Z.jpg"), new File("D:\\371_F.jpg")};
        compress(pack(files, new File(targetPath)), path);
        System.out.println("tar.gz压缩包打包完成！tar.gz包保存在" + file.getAbsolutePath() + "目录下。");
    }
}
