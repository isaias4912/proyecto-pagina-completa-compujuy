package ar.com.jsuper.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

//ignore "bytes" when return json format
@JsonIgnoreProperties({"bytes"})
public class FileMetaDTO {

    private String fileName;
    private String fileSize;
    private String fileType;
    private String fileAlt;

    private byte[] bytes;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @JsonIgnore
    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    // setters & getters
    public String getFileAlt() {
        return fileAlt;
    }

    public void setFileAlt(String fileAlt) {
        this.fileAlt = fileAlt;
    }
}
