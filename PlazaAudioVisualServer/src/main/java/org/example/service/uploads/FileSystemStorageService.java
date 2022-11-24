package org.example.service.uploads;



import org.example.controller.FilesRestController;
import org.example.exceptions.storage.StorageException;
import org.example.exceptions.storage.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {
    private final Path rootLocation;

    public FileSystemStorageService(@Value("${upload.root-location}") String path) {
        this.rootLocation = Paths.get(path);
    }

    /**
     * Guardar un archivo
     * @param file Contenido multiparte
     * @return String (Ruta del fichero)
     * @throws StorageException No se puede almacenar un fichero
     */
    @Override
    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String extension = StringUtils.getFilenameExtension(filename);
        String justFilename = filename.replace("." + extension, "");
        String storedFilename = System.currentTimeMillis() + "_" + justFilename + "." + extension;
        try {
            if (file.isEmpty()) {
                throw new StorageException("Fallo al almacenar un fichero vacío " + filename);
            }
            if (filename.contains("..")) {
                throw new StorageException("No se puede almacenar un fichero fuera del path permitido " + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(storedFilename),
                        StandardCopyOption.REPLACE_EXISTING);
                return storedFilename;
            }
        } catch (IOException e) {
            throw new StorageException("Fallo al almacenar el fichero " + filename, e);
        }
    }

    /**
     * Cargar todos los ficheros
     * @return Path
     * @throws StorageException fallo al leer todos los ficheros
     */
    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Fallo al leer los ficheros almacenados", e);
        }
    }

    /**
     * Cargar fichero por su nombre
     * @param filename nombre del fichero
     * @return Path
     */
    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    /**
     * Cargar un fichero
     * @param filename nombre del fichero
     * @return Resource
     * @throws StorageFileNotFoundException no se puede leer el fichero
     */
    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("No se puede leer el fichero: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("No se puede leer el fichero: " + filename, e);
        }
    }

    /**
     * Eliminar todos los ficheros
     */
    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    /**
     * Iniciar sistema de almacenamiento
     * @throws StorageException error al iniciar
     */
    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("No se puede inicializar el sistema de almacenamiento", e);
        }
    }

    /**
     * Eliminar un fichero
     * @param filename nombre del fichero
     * @throws StorageException error al eliminar el fichero
     */
    @Override
    public void delete(String filename) {
        String justFilename = StringUtils.getFilename(filename);
        try {
            Path file = load(justFilename);
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new StorageException("Error al eliminar un fichero", e);
        }
    }

    /**
     * Obtener la url de un fichero
     * @param filename nombre del archivo
     * @return String url
     */
    @Override
    public String getUrl(String filename) {
        return MvcUriComponentsBuilder
                .fromMethodName(FilesRestController.class, "serveFile", filename, null)
                .build().toUriString();
    }
}
