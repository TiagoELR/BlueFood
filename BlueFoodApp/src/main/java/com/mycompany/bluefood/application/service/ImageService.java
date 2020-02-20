
package com.mycompany.bluefood.application.service;

import com.mycompany.bluefood.util.IOUtil;
import java.io.IOException;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {
    @Value("${bluefood.files.ligotipo}")
    private String logotiposDir;
    
    @Value("${bluefood.files.comida}")
    private String comidasDir;
    
    @Value("${bluefood.files.categoria}")
    private String categoriaDir;
    
    public void uploadLogotipo(MultipartFile multipartFile, String fileName){
        try {
            IOUtil.copy(multipartFile.getInputStream(), fileName, logotiposDir);
        } catch (IOException ex) {
           throw new ApplicationServiceExceptions(ex);
        }
    }
    
     public void uploadComida(MultipartFile multipartFile, String fileName){
        try {
            IOUtil.copy(multipartFile.getInputStream(), fileName, comidasDir);
        } catch (IOException ex) {
           throw new ApplicationServiceExceptions(ex);
        }
    }
     
     public byte[] getBytes(String type, String imgName) {
         try{
         String dir;
         if("comida".equals(type)){
             dir = comidasDir;
         }else if("logotipo".equals(type)){
             dir = logotiposDir;
         }else if("categoria".equals(type)){
             dir = categoriaDir;
         }else{
             throw new Exception(type + "Não é um tipo de imagem valida");
         }
         return IOUtil.getBytes(Paths.get(dir, imgName));
         }catch(Exception e){
             throw new ApplicationServiceExceptions(e);
         }
     }
}
