package com.app.backend.services.users;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.backend.models.tickets.Document;
import com.app.backend.models.tickets.DocumentType;
import com.app.backend.models.users.User;
import com.app.backend.repositories.tickets.DocumentRepo;
import com.app.backend.repositories.tickets.DocumentTypeRepo;
import com.app.backend.repositories.users.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class UserFileService {
    final public String USER_FILES_PATH = "userData" + File.separator + "user";
    final public String PROFILE_PICTURE_DIR = "profile_pic";
    final public String DOC_DIR = "docs";

    @Autowired
    UserRepo userRepo;
    @Autowired
    DocumentTypeRepo documentTypeRepo;
    @Autowired
    DocumentRepo documentRepo;

    public File getUserProfilePicDir(Integer userId) {
        File userDir = new File(USER_FILES_PATH + File.separator + userId + File.separator + PROFILE_PICTURE_DIR);
        return userDir;
    }

  

    public Date getNextPossibleChangeDate(Integer userId) {
        if (getProfilePictureFile(userId).exists())
            return new Date(getProfilePictureFile(userId).lastModified() + (1000l * 60l * 60l * 24l * 365));
        return null;
    }

    public boolean isProfilePictureChangePossible(Integer userId) {
        if (getProfilePictureFile(userId).exists())
            return getNextPossibleChangeDate(userId).after(new Date(System.currentTimeMillis()));
        return true;
    }

    public boolean checkSuppliedImageProperties(MultipartFile file) {
        BufferedImage image;
        try {
            image = ImageIO.read(file.getInputStream());

            if (image.getHeight() != image.getWidth())
                return false;
        } catch (IOException e) {

            e.printStackTrace();
            return false;
        }
        return true;
    }

    public File getProfilePictureFile(Integer userId) {
        return new File(getUserProfilePicDir(userId), userId + ".jpg");
    }

    public boolean saveUserProfilePicture(Integer userId, MultipartFile file) {
        File userDir = getUserProfilePicDir(userId);
        if (!userDir.isDirectory())
            userDir.mkdirs();

        try {
            File destFile = new File(userDir, userId + ".jpg");

            if (!destFile.exists())
                destFile.createNewFile();
            Optional<User> userOptional = userRepo.findById(userId);
            if (!userOptional.isPresent())
                return false;
            User user = userOptional.get();
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

            messageDigest.update(file.getInputStream().readAllBytes());
            user.setPictureHash(messageDigest.digest());
            Files.copy(file.getInputStream(), Paths.get(destFile.getAbsolutePath()),
                    StandardCopyOption.REPLACE_EXISTING);
            userRepo.save(user);
        } catch (IOException e) {

            e.printStackTrace();
            return false;
        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
            return false;
        }
        return true;
    }

      public File getUserDocsDir(Integer userId) {
        File userDir = new File(USER_FILES_PATH + File.separator + userId + File.separator + DOC_DIR);
        return userDir;
    }
    
    @Transactional
    public boolean saveUserDocument(Integer userId, Integer documentTypeId, MultipartFile file) {
        Document doc = new Document();
        doc.setApproved(false);
        doc.setUserId(userId);
        DocumentType docType = documentTypeRepo.findById(documentTypeId).get();
        if(docType.getValidUntilDate().after(new Date()))
        return false;
        
        doc.setDocumentType(docType);
        Document dbResponse = documentRepo.save(doc);
        if(dbResponse!=null) 
        {
            File  destFile  = new   File(getUserDocsDir(userId), dbResponse.getId() + ".pdf");
            try {
                destFile.createNewFile();
                Files.copy(file.getInputStream(), Paths.get(destFile.getAbsolutePath()),
                    StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                return false;
            }
            
        }
        return false;
    }

    public File getDocument(Integer userId, Integer documentId) {
        
        return new   File(getUserDocsDir(userId), documentId + ".pdf");

    }


}
