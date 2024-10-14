package indi.yuluo.web.util;

import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public final class FilesUtils {

    private FilesUtils() {
    }

    /**
     * Get file name.
     * @param file {@link MultipartFile}
     * @return file name
     */
    public static String getFileName(MultipartFile file) {

        var fileName = file.getOriginalFilename();
        if (!StringUtils.hasText(fileName)) {
            return "";
        }
        return fileName;
    }

    public static String getFileType(String fileName) {

        if (!StringUtils.hasText(fileName)) {
            return "";
        }

        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static String upload(MultipartFile file, String path, String filename) {

        createDirIfNotExists(path);

        String resultPath = path + "/" + randomString() + "-" + filename;

        File uploadFile = new File(resultPath);
        try (InputStream inputStream = new ByteArrayInputStream(file.getBytes())) {
            FileUtils.copyInputStreamToFile(inputStream, uploadFile);
        } catch (IOException e) {
            return null;
        }

        return resultPath;
    }

    private static void createDirIfNotExists(String path) {

        File upload = new File(path);
        if(!upload.exists()) {
            upload.mkdirs();
        }
    }

    private static String randomString() {

        return SnowFlakeIdGenerator.generateId() + "";
    }

}
