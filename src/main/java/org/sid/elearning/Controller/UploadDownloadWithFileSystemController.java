package org.sid.elearning.Controller;


import org.sid.elearning.Models.Classe;
import org.sid.elearning.Models.Cour;
import org.sid.elearning.Models.Folder;
import org.sid.elearning.Models.Module;
import org.sid.elearning.Repositories.CourRepository;
import org.sid.elearning.Repositories.FolderRepository;
import org.sid.elearning.Repositories.ModuleRepository;
import org.sid.elearning.fileuploaddownload.payload.Response.FileUploadResponse;
import org.sid.elearning.fileuploaddownload.payload.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RequestMapping("/api")
@CrossOrigin("*")
@RestController
public class UploadDownloadWithFileSystemController {
    private FileStorageService fileStorageService;
    @Autowired
    private FolderRepository folderRepository;
    @Autowired
    private CourRepository courRepository;

    public UploadDownloadWithFileSystemController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/uploadfile/{id}")
    ResponseEntity<?>  singleFileUplaod(@PathVariable Long id , @RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/download/")
                .path(fileName)
                .toUriString();

        String contentType = file.getContentType();
        FileUploadResponse response = new FileUploadResponse(fileName, contentType, url);
        Folder fol= new Folder(fileName,contentType,url);
        //folderRepository.save(fol);
        // return response;
        Cour cour = courRepository.findById(id).get();
        //Folder existedFolder=folderRepository.findBy
        //if(existedFolder!=null){
        //    cour.getFolder().add(existedFolder);
         //   return new ResponseEntity<> (folderRepository.save(existedFolder), HttpStatus.OK);
        //}
        cour.getFolder().add(fol);
        return new ResponseEntity<> (folderRepository.save(fol), HttpStatus.OK);

    }

    @GetMapping("/download/{fileName}")
    ResponseEntity<Resource> downLoadSingleFile(@PathVariable String fileName, HttpServletRequest request) {

        Resource resource = fileStorageService.downloadFile(fileName);

//        MediaType contentType = MediaType.APPLICATION_PDF;

        String mimeType;

        try {
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        mimeType = mimeType == null ? MediaType.APPLICATION_OCTET_STREAM_VALUE : mimeType;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName="+resource.getFilename())
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename())
                .body(resource);
    }

    @PostMapping("/multiple/upload")
    List<FileUploadResponse> multipleUpload(@RequestParam("files") MultipartFile[] files) {

        if (files.length > 7) {
            throw new RuntimeException("too many files");
        }
        List<FileUploadResponse> uploadResponseList = new ArrayList<>();
        Arrays.asList(files)
                .stream()
                .forEach(file -> {
                    String fileName = fileStorageService.storeFile(file);


                    String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/download/")
                            .path(fileName)
                            .toUriString();

                    String contentType = file.getContentType();

                    FileUploadResponse response = new FileUploadResponse(fileName, contentType, url);
                    uploadResponseList.add(response);
                });

        return uploadResponseList;
    }

    @GetMapping("/zipDownload")
    void zipDownload(@RequestParam("fileName") String[] files, HttpServletResponse response) throws IOException {
//zipoutstream - zipentry+zipentry

        try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
            Arrays.asList(files)
                    .stream()
                    .forEach(file -> {
                        Resource resource = fileStorageService.downloadFile(file);

                        ZipEntry zipEntry = new ZipEntry(resource.getFilename());

                        try {
                            zipEntry.setSize(resource.contentLength());
                            zos.putNextEntry(zipEntry);

                            StreamUtils.copy(resource.getInputStream(), zos);

                            zos.closeEntry();
                        } catch (IOException e) {
                            System.out.println("some exception while ziping");
                        }
                    });
            zos.finish();

        }

        response.setStatus(200);
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=zipfile");
    }
}
