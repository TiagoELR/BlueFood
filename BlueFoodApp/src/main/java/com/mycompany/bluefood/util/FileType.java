
package com.mycompany.bluefood.util;

public enum FileType {
   PNG("image/png", "png"),
   JPG("image/jpeg", "jpg");
   
   String mimeType;
   String extencion;

    FileType(String mimeType, String extencion) {
        this.mimeType = mimeType;
        this.extencion = extencion;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getExtencion() {
        return extencion;
    }

   public boolean sameOf(String mimeType){
       return this.mimeType.equalsIgnoreCase(mimeType); 
   }
   
   public static FileType of(String mimeType){
       for(FileType fileType : values()){
           if(fileType.sameOf(mimeType)){
               return fileType;
           }
       }
       return null;
   }
}
