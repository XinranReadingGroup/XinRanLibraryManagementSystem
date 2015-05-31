package com.xinran.controller.common;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
 
public class  UploadValidator implements Validator {

    private Logger                logger                 = LoggerFactory.getLogger(UploadValidator.class);
    // Content types the user can upload
    private static final String[] ACCEPTED_CONTENT_TYPES = new String[] {
 "image/jpeg", "image/jpg", "image/png",
            "image/tiff",
                                                           // "application/pdf",
                                                           // "application/doc",
                                                           // "application/msword",
                                                           // "application/rtf",
                                                           // "text/richtext" ,
                                                           // "text/rtf" ,
                                                           // "text/plain" ,
                                                           // "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                                                           // ,
                                                           // "application/vnd.sun.xml.writer" ,
                                                           // "application/x-soffice" ,
            };
     
    private static final String[] ACCEPTED_EXTENSIONS = new String[] {
                                                           // "doc",
                                                           // "pdf",
                                                           // "docx",
            "jpeg", "jpg", "png", "tiff"
                                                           // "txt",
    };
     
    @Override
    public boolean supports(Class<?> clazz) {
        return FileUploadForm.class.equals(clazz);
    }
 
    /**
     * Validate our uploaded bean
     */
    @Override
    public void validate(Object obj, Errors errors) {
        FileUploadForm uploadItem = (FileUploadForm) obj;
        MultipartFile file = uploadItem.getFile();
        try {
            if(file == null || file.getBytes().length == 0){
                errors.reject("error.upload.null", "File name can't be empty");
                return;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());           
        }       
         
        // Check content type
        boolean acceptableContentType = false;
        String incomingContentType = file.getContentType();
        logger.info("FileName = "+file.getName());      
        logger.info("incomingContentType = "+incomingContentType);
        // This related to bug  when on Vista and using Firefox content type is 'application/octet-stream'        
        // Instead  of one of the allowed ones
        if("application/octet-stream".equalsIgnoreCase(incomingContentType)){
            int index = file.getOriginalFilename().lastIndexOf('.');
            String incomingExtension = file.getOriginalFilename().substring(index + 1);
            for(String extendsion : ACCEPTED_EXTENSIONS){
                if(extendsion.equalsIgnoreCase(incomingExtension)){
                    acceptableContentType = true;
                    break;
                }           
            }
        }else{
            for(String contentType : ACCEPTED_CONTENT_TYPES){
                logger.debug("Comparing " + incomingContentType +" to "+ contentType);
                if(contentType.equalsIgnoreCase(incomingContentType)){
                    acceptableContentType = true;
                    break;
                }           
            }
        }
        if(!acceptableContentType){
            errors.reject("error.upload.contenttype", "Please upload a file with one of the following file types; .doc, .docx, .txt, .pdf, .rtf .");
        }
    }
}