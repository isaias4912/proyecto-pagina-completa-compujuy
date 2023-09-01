package ar.com.jsuper.services.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ar.com.jsuper.api.utils.FileMeta;
import ar.com.jsuper.api.utils.FileUtils;
import ar.com.jsuper.api.utils.ImageUtils;
import ar.com.jsuper.security.SimpleEncryption;
import ar.com.jsuper.services.FileService2;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class FileService2Impl extends BaseService implements FileService2 {

	LinkedList<FileMeta> files = null;
	FileMeta fileMeta = null;
	private Logger logger = Logger.getLogger(FileService2Impl.class);
	@Value("${image.maxFileSize}")
	private long maxFileSize;

	@Override
	public LinkedList<FileMeta> generateFileImage(MultipartHttpServletRequest request, String directory, Boolean thumbnail) {
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
			fileMeta = new FileMeta();
			fileMeta.setFileName(mpf.getOriginalFilename());
			long fileSize = mpf.getSize() / 1024;
			if (fileSize > maxFileSize) {
				throw new DataIntegrityViolationException("Para esta versión no se permiten archivos de mas de 2MB de tamaño.");
			}
			fileMeta.setFileSize(fileSize + " Kb");
			fileMeta.setFileType(mpf.getContentType());

			try {
				fileMeta.setBytes(mpf.getBytes());
				String path = "";
				path = directory;
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
					nameFile = String.format("%s.%s", name + "_24", ext);
					ImageUtils.resize(bImageFromConvert, path + "thumbnails/" + nameFile, 24);
					nameFile = String.format("%s.%s", name + "_48", ext);
					ImageUtils.resize(bImageFromConvert, path + "thumbnails/" + nameFile, 48);
					nameFile = String.format("%s.%s", name + "_128", ext);
					ImageUtils.resize(bImageFromConvert, path + "thumbnails/" + nameFile, 128);
				}
			} catch (IOException e) {
				logger.error("Error con la subida de la imagen " + directory, e);
			}
			// 2.4 add to files
			files.add(fileMeta);
		}
		return files;

	}

	@Override
	public FileMeta generateFile(MultipartHttpServletRequest request, String type) {
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf = null;
		String msg = "";
		String directory = "";
		files = new LinkedList<>();
		// 2. get each file
		while (itr.hasNext()) {
			// 2.1 get next MultipartFile
			mpf = request.getFile(itr.next());
			// 2.3 create new fileMeta
			fileMeta = new FileMeta();
			fileMeta.setFileName(mpf.getOriginalFilename());
			String ext = FilenameUtils.getExtension(mpf.getOriginalFilename()); // returns "txt"
			if (!ext.equals("crt")) {
				throw new DataIntegrityViolationException("Extension de archivo no valida");
			}
			Long sizeFile = new Long("1024");
			if (type == "crt") {
				sizeFile = new Long("604");
				msg = "Para archivos crt/cert no se permiten mas de 0.5 mb de tamanio.";
				directory = this.getFullPathApp() + "afip/";
			}
			long fileSize = mpf.getSize() / 1024;
			if (fileSize > sizeFile) {
				throw new DataIntegrityViolationException(msg);
			}
			fileMeta.setFileSize(fileSize + " Kb");
			fileMeta.setFileType(mpf.getContentType());
			try {
				fileMeta.setBytes(mpf.getBytes());
				String nameFile = mpf.getOriginalFilename();
				fileMeta.setFileName(nameFile);
				fileMeta.setFileAlt(nameFile);
				FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream(directory + nameFile));
			} catch (IOException e) {
				logger.error("Error con la subida del archivo, " + directory, e);
			}
		}
		return fileMeta;
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
