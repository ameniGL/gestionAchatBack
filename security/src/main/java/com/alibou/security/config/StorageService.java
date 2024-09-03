package com.alibou.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class StorageService {
    // l enregistrement des logs (ou on stock les erreurs )
    Logger log = LoggerFactory.getLogger(this.getClass().getName());

    // ken ana des images ouin bch nstockiwhom
    private final Path rootLocation = Paths.get("upload-dir");

    // Méthode pour stocker un fichier
    public void store(MultipartFile file) {
        try {
            // Extraire l'extension et le nom du fichier d'origine
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'), file.getOriginalFilename().length());
            String name = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf('.'));
            String original = name + ext;

            // Copier le contenu du fichier vers deux emplacements différents
            Files.copy(file.getInputStream(), this.rootLocation.resolve(original));
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            // En cas d'erreur, ytaych exeption
            throw new RuntimeException("FAIL Image !");
        }
    }

    // Méthode bch yloadi un fichier
    public Resource loadFile(String filename) {
        try {
            //yji lfichier
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            // ylawej alih ken exist wale
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                // ken fama erreur ytalaa exeption
                throw new RuntimeException("FAIL Resource Inexistant!");
            }
        } catch (MalformedURLException e) {
            //erreur mtaa path
            throw new RuntimeException("FAIL Malformed!");
        }
    }

    // methode tfasakh li fi west fichier
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    // Méthode pour bch taaml initialisation lstockage
    public void init() {
        try {
            // tverifi ken   répertoire existe, sinon tasn3ou
            if (!Files.exists(rootLocation)) {
                Files.createDirectory(rootLocation);
                log.info("Storage initialized successfully at location: {}", rootLocation);
            } else {
                log.info("Storage directory already exists at location: {}", rootLocation);
            }
        } catch (IOException e) {
            // exception
            log.error("Error initializing storage: {}", e.getMessage());
            throw new RuntimeException("Could not initialize storage!", e);
        }
    }
}
