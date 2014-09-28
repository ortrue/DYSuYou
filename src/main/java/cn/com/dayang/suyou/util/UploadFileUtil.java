package cn.com.dayang.suyou.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class UploadFileUtil {
	public final static Logger logger = Logger.getLogger(UploadFileUtil.class);
	public static void uploadToServer(MultipartFile localFile, String fileName,String serverPath) {
		try {
			String remotePath = "";
			remotePath = serverPath + remotePath;
			if (!remotePath.endsWith("/")) {
				remotePath = remotePath + "/";
			}

			/** 上传前,先删除同名文件,以保持更新 */
			String path = "";
			if ("/".equals(remotePath)) {
				path = "/";
			} else {
				path = remotePath + "/";
			}
			uploadFile(path, fileName, localFile);

		} catch (Exception e) {
			logger.error("上传指定文件到配置好的目录中出错" + e.getMessage(), e);
		}
	}
	
	
    public static boolean uploadFile(String path, String fileName, MultipartFile localFile) throws IOException {
    	String[] splitFile = fileName.split("/");
    	if (splitFile.length > 1) {
    		path += "/" + splitFile[0];
    		fileName = splitFile[1];
    	}
    	File dir = new File(path);
    	if (!dir.exists()) {
			dir.mkdirs();
    	}
		try {
			copyFile(localFile, new File(dir + "/" + fileName));
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
    	return false;
    }
	
    // 复制文件
    public static void copyFile(MultipartFile sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
        	if (targetFile.exists()) {
        		targetFile.deleteOnExit();
        	}
        	targetFile.createNewFile();
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(sourceFile.getInputStream());

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } catch (IOException e) {
        	e.printStackTrace();
        	throw e;
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
	
	public static String validateFtpFileIsExist(String fileName,String localPath) {
        if (!localPath.endsWith("/")) {
            localPath = localPath + "/";
        }
		File file = new File(localPath + fileName);
    	if (file.exists()) {
    		return localPath + fileName;
    	} else {
			return "";
    	}
	}
}
