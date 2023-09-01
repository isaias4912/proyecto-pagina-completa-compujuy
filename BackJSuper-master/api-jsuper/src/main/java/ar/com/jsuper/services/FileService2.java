package ar.com.jsuper.services;

import ar.com.jsuper.api.utils.FileMeta;
import java.util.LinkedList;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface FileService2 {

	LinkedList<FileMeta> generateFileImage(MultipartHttpServletRequest request, String directory, Boolean thumbnail);

	FileMeta generateFile(MultipartHttpServletRequest request, String type);

}
