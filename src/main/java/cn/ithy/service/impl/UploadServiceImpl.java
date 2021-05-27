package cn.ithy.service.impl;

import cn.ithy.config.UploadProperties;
import cn.ithy.enums.ExceptionEnum;
import cn.ithy.exception.CcshopException;
import cn.ithy.service.UploadService;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
public class UploadServiceImpl implements UploadService {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private UploadProperties prop;

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            //校验文件类型
            String contentType = file.getContentType();
            if(!prop.getAllowTypes().contains(contentType)){
                throw new CcshopException(ExceptionEnum.FILE_SAVE_ERROR);
            }
            //校验文件内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image == null){
                throw new CcshopException(ExceptionEnum.FILE_SAVE_ERROR);
            }
            //上传到fastDFS
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
            //返回访问路径
            return prop.getBaseUrl()+storePath.getFullPath();
        } catch (IOException e) {
            //上传失败
            log.error("上传文件失败", e);
           throw new CcshopException(ExceptionEnum.FILE_SAVE_ERROR);
        }
    }
}
