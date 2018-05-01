package com.spark.ims.common.util;

import com.spark.ims.common.constants.DataDict;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import org.apache.commons.lang3.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

public class ZipUtils {

    /**
     * 压缩文件
     *
     * @param path 压缩路径（可为文件或者文件夹）
     */
    public static void zip(String path) {
        zip(path, false);
    }

    /**
     * 压缩文件
     *
     * @param path     压缩路径（可为文件或者文件夹）
     * @param isDelete 是否删除被压缩的文件或者文件夹
     */
    public static void zip(String path, Boolean isDelete) {
        ZipFile zipFile;
        try {
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(8);
            parameters.setCompressionLevel(5);
            File file = new File(path);
            if (file.isDirectory()) {
                zipFile = new ZipFile(new File(path + DataDict.Separator.POINT + DataDict.Suffix.ZIP));
                zipFile.setFileNameCharset("GBK");
                zipFile.addFolder(path, parameters);
            } else {
                String zipFileName = path.substring(0, path.lastIndexOf("."));
//                zipFile = new ZipFile(new File(path.split("\\.")[0] + DataDict.Separator.POINT + DataDict.Suffix.ZIP));
                zipFile = new ZipFile(new File(zipFileName + DataDict.Separator.POINT + DataDict.Suffix.ZIP));
                zipFile.setFileNameCharset("GBK");
                zipFile.addFile(file, parameters);
            }

            if (isDelete)
                FileUtils.deleteDir(file);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    public static void zipSetPass(String path, Boolean isDelete, String password) {
        ZipFile zipFile;
        try {
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(8);
            parameters.setCompressionLevel(5);

            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(0);
            parameters.setPassword(password);
            File file = new File(path);
            if (file.isDirectory()) {
                zipFile = new ZipFile(new File(path + DataDict.Separator.POINT + DataDict.Suffix.ZIP));
                zipFile.setFileNameCharset("utf-8");
                zipFile.addFolder(path, parameters);
            } else {
                zipFile = new ZipFile(new File(path.split(".")[0] + DataDict.Separator.POINT + DataDict.Suffix.ZIP));
                zipFile.setFileNameCharset("utf-8");
                zipFile.addFile(file, parameters);
            }
            if (isDelete)
                FileUtils.deleteDir(new File(path));
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    public static void unZip(String filePath, String toPath, String password) {
        try {
            unZipFile(new ZipFile(filePath), toPath, password);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压流程定义扩展文件的压缩包
     *
     * @param originalFilename 压缩文件名称
     * @param bytes            文件字节数组
     * @param toPath           流程定义扩展文件的解压目标位置
     * @param tempPath         解压缩的临时路径
     * @return 解压出来的流程定义扩展文件路径
     * @throws Exception
     */
    public static String unZipBpmFile(String originalFilename, byte[] bytes, String toPath, String tempPath) throws Exception {
        if (originalFilename.indexOf(DataDict.Suffix.ZIP.toString()) == -1) {
            throw new ZipException("该文件不是压缩文件");
        }
        String destPath = createFilePath(tempPath, originalFilename);
        File file = new File(destPath);
        if (file.exists())
            file.delete();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();
//        multipartFile.transferTo(file);
        ZipFile zipFile = new ZipFile(file);
        zipFile.setFileNameCharset("GBK");
        if (zipFile.isEncrypted())
            zipFile.setPassword("");
        if (!zipFile.isValidZipFile())
            throw new ZipException("压缩文件不合法,可能被损坏.");
        zipFile.extractAll(tempPath);
        // 解压出来的扩展流程定义文件路径
        String fileDir = null;
        List<FileHeader> fileHeaderList = zipFile.getFileHeaders();
        for (FileHeader fileHeader : fileHeaderList) {
            String dirName = fileHeader.getFileName();
            if (!fileHeader.isDirectory() && dirName.indexOf(DataDict.Suffix.IMS_BPMN_XML.toString()) != -1) {
                fileDir = tempPath + dirName;
                break;
            }
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(fileDir)) {
            throw new ZipException("该压缩文件不包含流程定义文件");
        }

        String newFilePath = toPath + UUIDUtils.generate() + DataDict.Separator.POINT + DataDict.Suffix.IMS_BPMN_XML;
        FileUtils.copyFile(fileDir, newFilePath);
        // 删除解压出来的文件
//        String tempUnzipPath = tempPath + originalFilename.replace(DataDict.Separator.POINT.toString() + DataDict.Suffix.ZIP, "");
//        FileUtils.deleteDir(new File(tempUnzipPath));
        FileUtils.deleteDir(new File(fileDir));
        // 删除zip压缩文件
        FileUtils.deleteDir(file);
        return newFilePath;
    }

    public static String unZipFile(String originalFilename, byte[] bytes, String toPath) throws Exception {
        if (originalFilename.indexOf(DataDict.Suffix.ZIP.toString()) == -1) {
            throw new ZipException("该文件不是压缩文件");
        }
        String destPath = toPath + originalFilename;

        createFilePath(toPath, originalFilename);
        File file = new File(destPath);
        if (file.exists())
            file.delete();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();
        ZipFile zipFile = new ZipFile(file);
        zipFile.setFileNameCharset("GBK");
        if (zipFile.isEncrypted())
            zipFile.setPassword("");
        if (!zipFile.isValidZipFile())
            throw new ZipException("压缩文件不合法,可能被损坏.");
        String fileDir = "";
        zipFile.extractAll(toPath);

        List<FileHeader> fileHeaderList = zipFile.getFileHeaders();
        for (FileHeader fileHeader : fileHeaderList) {
            String dirName = fileHeader.getFileName();
            if (fileHeader.isDirectory()) {
                if (dirName.endsWith("\\"))
                    fileDir = dirName.substring(0, dirName.lastIndexOf("\\"));
                else if (dirName.endsWith("/"))
                    fileDir = dirName.substring(0, dirName.lastIndexOf("/"));
                else {
                    fileDir = dirName.substring(0, dirName.lastIndexOf(File.separator));
                }
            }
        }
        FileUtils.deleteDir(file);

        return fileDir;
    }

    public static void unZipFile(String originalFilename, byte[] bytes, String toPath, String password) {
        String destPath = toPath + originalFilename;
        try {
            createFilePath(destPath, originalFilename);
            File file = new File(destPath);
            if (file.exists())
                file.delete();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();
            unZipFile(new ZipFile(file), toPath, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压压缩文件
     *
     * @param zipFile  压缩文件
     * @param toPath   解压目标地址
     * @param password 解压密码
     */
    public static void unZipFile(ZipFile zipFile, String toPath, String password) {
        try {
            if (zipFile.isEncrypted())
                zipFile.setPassword(password);
            List fileHeaderList = zipFile.getFileHeaders();
            for (Iterator localIterator = fileHeaderList.iterator(); localIterator.hasNext(); ) {
                Object o = localIterator.next();
                FileHeader fileHeader = (FileHeader) o;
                zipFile.extractFile(fileHeader, toPath);
            }
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建文件路径
     *
     * @param tempPath 文件所在的文件夹路径
     * @param fileName 文件名
     * @return 文件路径
     */
    public static String createFilePath(String tempPath, String fileName) {
        File file = new File(tempPath);

        if (!file.exists())
            file.mkdirs();
        return file.getPath() + File.separator + fileName;
    }
}