package com.enotes.service.impl;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.NotesDto;
import com.enotes.entity.Category;
import com.enotes.entity.FileDetails;
import com.enotes.entity.Notes;
import com.enotes.repository.CategoryRepository;
import com.enotes.repository.FileRepository;
import com.enotes.repository.NotesRepository;
import com.enotes.service.NotesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private NotesRepository repository;

    @Value("${file.upload.path}")
    private String uploadpath;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileRepository fileRepo;

    @Override
    public NotesDto saveNotes(String notes, MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        NotesDto notesDto = objectMapper.readValue(notes, NotesDto.class);

        FileDetails fileDtls = saveFileDetails(file);

        CategoryDto category = notesDto.getCategory();
        Integer id = category.getId();
        Category existCategory = categoryRepository.findById(id).orElseThrow();
        CategoryDto dtoCategory = mapper.map(existCategory, CategoryDto.class);
        notesDto.setCategory(dtoCategory);
        Notes note = mapToEntity(notesDto);
        Notes save = repository.save(note);
        return mapToDto(save);
    }

    @Override
    public List<NotesDto> getAllNotes() {
        List<Notes> all = repository.findAll();
        List<NotesDto> notesDto = all.stream().map((element) -> mapper.map(element, NotesDto.class)).collect(Collectors.toList());
        return notesDto;
    }

    private FileDetails saveFileDetails(MultipartFile file) throws IOException {

        if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {

            String originalFilename = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFilename);

            List<String> extensionAllow = Arrays.asList("pdf", "xlsx", "jpg", "png");
            if (!extensionAllow.contains(extension)) {
                throw new IllegalArgumentException("invalid file format ! Upload only .pdf , .xlsx,.jpg");
            }

            String rndString = UUID.randomUUID().toString();
            String uploadfileName = rndString + "." + extension; // sdfsafbhkljsf.pdf

            File saveFile = new File(uploadpath);
            if (!saveFile.exists()) {
                saveFile.mkdir();
            }
            // path : enotesapiservice/notes/java.pdf
            String storePath = uploadpath.concat(uploadfileName);

            // upload file
            long upload = Files.copy(file.getInputStream(), Paths.get(storePath));
            if (upload != 0) {
                FileDetails fileDtls = new FileDetails();
                fileDtls.setOriginalFileName(originalFilename);
                fileDtls.setDisplayFileName(getDisplayName(originalFilename));
                fileDtls.setUploadFileName(uploadfileName);
                fileDtls.setFileSize(file.getSize());
                fileDtls.setPath(storePath);
                FileDetails saveFileDtls = fileRepo.save(fileDtls);
                return saveFileDtls;
            }
        }

        return null;
    }

    private String getDisplayName(String originalFilename) {
        String extension = FilenameUtils.getExtension(originalFilename);
        String fileName = FilenameUtils.removeExtension(originalFilename);

        if (fileName.length() > 8) {
            fileName = fileName.substring(0, 7);
        }
        fileName = fileName + "." + extension;
        return fileName;
    }

    public NotesDto mapToDto(Notes notes){
       return mapper.map(notes, NotesDto.class);
    }

    public Notes mapToEntity(NotesDto dto){
        return mapper.map(dto, Notes.class);
    }
}
