package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.domain.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ImageUploadService {

    private final UserRepository userRepository;

    private final BookRepository bookRepository;

    private static final String AVATAR_UPLOAD_DIR = "uploads/avatar";

    private static final String AVATAR_PUBLIC_URL_PREFIX = "/uploads/avatar/";

    private static final String BOOK_UPLOAD_DIR = "uploads/books";

    private static final String BOOK_PUBLIC_URL_PREFIX = "/uploads/books/";


    public void uploadAvatarImage(MultipartFile file, String username) throws IOException {

        User user = userRepository.findByUsername(username).orElseThrow();

        String filename = user.getId() + '-' + file.getOriginalFilename();
        File uploadPath = new File(AVATAR_UPLOAD_DIR).getAbsoluteFile();
        File destinationFile = new File(uploadPath, filename);

        file.transferTo(destinationFile);
        String imageUrl = AVATAR_PUBLIC_URL_PREFIX + filename;

        user.setAvatarUrl(imageUrl);
        log.info("Зображення користувача успішно збережено: {}", imageUrl);
    }

    public void uploadBookImage(MultipartFile file, Long id) throws IOException {

        Book book = bookRepository.findById(id).orElseThrow();

        String filename = UUID.randomUUID() + file.getOriginalFilename();
        File uploadPath = new File(BOOK_UPLOAD_DIR).getAbsoluteFile();
        File destinationFile = new File(uploadPath, filename);

        file.transferTo(destinationFile);
        String imageUrl = BOOK_PUBLIC_URL_PREFIX + filename;

        book.setPhotoBookUrl(imageUrl);
        log.info("Зображення книги успішно збережено: {}", imageUrl);
    }

}
