package ar.com.jsuper.services.impl;

import ar.com.jsuper.dto.FileMetaDTO;
import ar.com.jsuper.security.SimpleEncryption;
import ar.com.jsuper.services.FileService;
import ar.com.jsuper.utils.FileUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    private Logger logger = Logger.getLogger(FileServiceImpl.class);
    LinkedList<FileMetaDTO> files = null;
    FileMetaDTO fileMeta = null;
    @Value("${image.maxFileSize}")
    private long maxFileSize;
    @Autowired
    private FileUtils fileUtil;

    public static final String DIR_PRODUCT_IMAGE="DIR_PRODUCT_IMAGE";

    List<Integer> sizesImages = Arrays.asList(24, 48, 128);

    @Override
    public LinkedList<FileMetaDTO> generateFileImage(MultipartHttpServletRequest request, String path, Boolean thumbnail) {
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf = null;
        files = new LinkedList<>();
        // 2. get each file
        while (itr.hasNext()) {
            // 2.1 get next MultipartFile
            mpf = request.getFile(itr.next());
            // 2.2 if files > 10 remove the first from the list
            if (files.size() >= 10) {
                files.pop();
            }

            // 2.3 create new fileMeta
            fileMeta = new FileMetaDTO();
            fileMeta.setFileName(mpf.getOriginalFilename());
            long fileSize = mpf.getSize() / 1024;
            if (fileSize > maxFileSize) {
                throw new DataIntegrityViolationException("Para esta versión no se permiten archivos de mas de 2MB de tamaño.");
            }
            fileMeta.setFileSize(fileSize + " Kb");
            fileMeta.setFileType(mpf.getContentType());

            try {
                fileMeta.setBytes(mpf.getBytes());
                String nameFile = FileUtils.getNameFile(path, mpf.getOriginalFilename());
                String ext = FilenameUtils.getExtension(nameFile); // returns "txt"
                String name = FilenameUtils.getBaseName(nameFile); // returns "txt"
                String nameOriginal = name;
                name = SimpleEncryption.md5(name);
                nameFile = findFileName(path, name, ext);
                fileMeta.setFileName(nameFile);
                fileMeta.setFileAlt(nameOriginal);
                FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(path + nameFile));
                // creamos un thumbnail de la imagen del  logo que se suba
                if (thumbnail) {
                    InputStream in = new ByteArrayInputStream(mpf.getBytes());
                    BufferedImage bImageFromConvert = ImageIO.read(in);
                    name = FilenameUtils.getBaseName(nameFile);
                    ext = FilenameUtils.getExtension(nameFile);
                    for (Integer sizesImage : this.sizesImages) {
                        nameFile = String.format("%s.%s", name + "_" + sizesImage, ext);
                        this.createFromImageFile(bImageFromConvert, path + "thumbnails/" + nameFile, sizesImage, sizesImage);
                    }
                }
            } catch (IOException e) {
                logger.error("Error con la subida de la imagen " + path, e);
            }
            // 2.4 add to files
            files.add(fileMeta);
        }
        return files;

    }

    @Override
    public void createFromImageFile(BufferedImage inputFile, String outputPath, int w, int h) {
        try {
            Thumbnails.of(inputFile)
                    .size(w, h)
                    .toFile(outputPath);
        } catch (IOException e) {
            this.logger.error("Error al subir la imagen", e);
        }
    }

    private String findFileName(final String dir, final String baseName,
                                final String extension) {
        String name = "";
        name = String.format("%s.%s", baseName, extension);
        Path ret = Paths.get(dir, name);
        if (!Files.exists(ret)) {
            return name;
        }

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            name = String.format("%s%d.%s", baseName, i, extension);
            ret = Paths.get(dir, name);
            if (!Files.exists(ret)) {
                return name;
            }
        }
        name = (Math.random() * 4) + "";
        logger.error("Problemas con el nombre del archivo");
        return name;
    }
}
