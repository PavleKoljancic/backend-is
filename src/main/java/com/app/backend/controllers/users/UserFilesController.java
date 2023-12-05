package com.app.backend.controllers.users;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.backend.security.SecurityUtil;
import com.app.backend.services.users.UserFileService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user/files")
public class UserFilesController {

    /*@Autowired
    private UserFileService userFileService;

    @PostMapping("upload/profilepicture&userId={UserId}")
    public ResponseEntity<Boolean> uploadProfilePicture(@RequestParam("profile_pic") MultipartFile file,
            @PathVariable("UserId") Integer userId, HttpServletRequest request) {

        if("USER".compareTo(SecurityUtil.getRoleFromAuthToken(request)) == 0 && userId == SecurityUtil.getIdFromAuthToken(request)){

            if (!userFileService.checkSuppliedImageProperties(file))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(false);

            if (!userFileService.isProfilePictureChangePossible(userId))
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(false);

            if (!userFileService.saveUserProfilePicture(userId, file))
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
            return ResponseEntity.ok().body(true);
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(false);
    }

    @GetMapping(value = "get/profilepicture&userId={UserId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable("UserId") Integer userId,
            HttpServletRequest request) {

        if("USER".compareTo(SecurityUtil.getRoleFromAuthToken(request)) == 0 && userId != SecurityUtil.getIdFromAuthToken(request))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        File userFile = userFileService.getProfilePictureFile(userId);
        if (userFile.exists())
            try {

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentLength(userFile.length())
                        .body(Files.readAllBytes(Paths.get(userFile.getAbsolutePath())));

            } catch (IOException e) {

                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping(value = "get/getNextPossibleChangeDate&userId={UserId}")
    public Date canUserChangeProfilePicture(@PathVariable("UserId") Integer userId) {

        return userFileService.getNextPossibleChangeDate(userId);
    }

    @PostMapping("upload/document&userId={UserId}&DocumentName={DocumentName}")
    public ResponseEntity<Boolean> uploadDocument(@RequestParam("document") MultipartFile file,
            @PathVariable("UserId") Integer userId, @PathVariable("DocumentName") String DocumentName,
            HttpServletRequest request) {

        if("USER".compareTo(SecurityUtil.getRoleFromAuthToken(request)) == 0 && userId == SecurityUtil.getIdFromAuthToken(request))
            return ResponseEntity.ok().body(userFileService.saveUserDocument(userId, DocumentName, file));

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(false);
    }

    @GetMapping(value = "get/document&userId={UserId}&DocumentName={DocumentName}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getDocument(@PathVariable("UserId") Integer userId,
            @PathVariable("DocumentName") String DocumentName,
            HttpServletRequest request) {

        if("USER".compareTo(SecurityUtil.getRoleFromAuthToken(request)) == 0 && userId != SecurityUtil.getIdFromAuthToken(request))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    
        File doc = userFileService.getDocument(userId, DocumentName);
        if (doc.exists() && doc.isFile())
            try {

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentLength(doc.length())
                        .body(Files.readAllBytes(Paths.get(doc.getAbsolutePath())));

            } catch (IOException e) {

                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
    @GetMapping(value = "remove/document&userId={UserId}&DocumentName={DocumentName}")
    public ResponseEntity<Boolean> removeDocument(@PathVariable("UserId") Integer userId,
            @PathVariable("DocumentName") String DocumentName,
            HttpServletRequest request) {

        if("USER".compareTo(SecurityUtil.getRoleFromAuthToken(request)) == 0 && userId == SecurityUtil.getIdFromAuthToken(request))
            return ResponseEntity.ok().body(userFileService.removeDocument(userId,DocumentName));
            
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(false); 
    }*/
}
