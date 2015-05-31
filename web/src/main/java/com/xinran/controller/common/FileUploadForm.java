package com.xinran.controller.common;
import lombok.Data;

import org.springframework.web.multipart.MultipartFile;
 
@Data
public class FileUploadForm {
 
    private MultipartFile file;
     
}