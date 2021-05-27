package cn.ithy.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    /**
     * 文件上传
     * @param file
     * @return
     */
    public String uploadImage(MultipartFile file);
}
