package ar.com.jsuper.services;

import ar.com.jsuper.dto.FileMetaDTO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

public interface FileService {
    void createFromImageFile(BufferedImage inputFile, String outputPath, int w, int h);

    LinkedList<FileMetaDTO> generateFileImage(MultipartHttpServletRequest request, String directory, Boolean thumbnail);

}
