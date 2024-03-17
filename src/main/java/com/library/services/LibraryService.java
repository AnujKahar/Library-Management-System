package com.library.services;

import java.util.List;

import com.library.model.BookDTO;

public interface LibraryService {

	List<BookDTO> getAllBooks();

	BookDTO getBook(Long id);

	BookDTO addBook(BookDTO bookDTO);

	BookDTO updateBook(BookDTO bookDTO, Long id);

	boolean deleteBook(Long id);
}
