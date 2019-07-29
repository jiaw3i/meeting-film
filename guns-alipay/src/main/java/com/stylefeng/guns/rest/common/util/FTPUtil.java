package com.stylefeng.guns.rest.common.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.*;

/**
 * @author Jerry
 **/
@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "ftp")
public class FTPUtil {
    // 地址 端口  用户名 密码
    private String hostName = "192.168.1.128";
    private Integer port = 2100;
    private String userName = "ftpClient";
    private String password = "ftpclient";
    private FTPClient ftpClient = null;
    private String uploadPath = "/qrcode";

    private void initFTPClient() {
        try {
            ftpClient = new FTPClient();
            ftpClient.setControlEncoding("utf-8");
            ftpClient.connect(hostName, port);
            ftpClient.login(userName, password);

        } catch (Exception e) {
            log.error("初始化FTPClient出错", e);
        }
    }

    // 输入一个路径 将路径里的文件转成字符串返回
    public String getFileStrByAddress(String filePath) {
        BufferedReader bufferedReader = null;
        try {
            initFTPClient();
            bufferedReader = new BufferedReader(new InputStreamReader(ftpClient.retrieveFileStream(filePath)));
            StringBuffer stringBuffer = new StringBuffer();
            while (true) {
                String lineStr = bufferedReader.readLine();
                if (lineStr == null) {
                    break;
                }
                stringBuffer.append(lineStr);
            }
            ftpClient.logout();
            return stringBuffer.toString();
        } catch (Exception e) {
            log.error("获取文件信息失败", e);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }


    // 上传FTP文件
    public boolean uploadFile(String fileName, File file) {

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            // 初始化ftpclient
            initFTPClient();
            // 设置ftpClient相关参数
            ftpClient.setControlEncoding("utf-8");
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            // 保证通道的高可用
            ftpClient.enterLocalPassiveMode();

            // 将ftpClient的工作空间修改，---- 上传到哪个文件夹
            ftpClient.changeWorkingDirectory(this.getUploadPath());

            // 上传
            ftpClient.storeFile(fileName, fileInputStream);
            return true;
        } catch (Exception e) {
            log.error("上传失败", e);
            return false;
        } finally {
            try {
                fileInputStream.close();
                ftpClient.logout();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
