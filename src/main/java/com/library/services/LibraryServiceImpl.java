package com.library.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.model.Book;
import com.library.model.BookDTO;
import com.library.repository.LibraryRepository;

@Service
public class LibraryServiceImpl implements LibraryService {

	private static Logger LOGGER = LoggerFactory.getLogger(LibraryServiceImpl.class);

	@Autowired
	private LibraryRepository libraryRepository;

	@Override
	public List<BookDTO> getAllBooks() {
		LOGGER.info("Executing getAllBooksMethod");
		List<Book> books = libraryRepository.findAll();
		LOGGER.info("fetched total books from db: " + books.size());
		List<BookDTO> booksDTO = new ArrayList<>();
		if (!books.isEmpty()) {
			for (Book b : books) {
				BookDTO tempDTO = new BookDTO();
				BeanUtils.copyProperties(b, tempDTO);
				booksDTO.add(tempDTO);
			}
		}
		LOGGER.info("getAllBooksMethod executed successfully");
		return booksDTO;
	}

	@Override
	public BookDTO getBook(Long id) {
		LOGGER.info("Executing getBookMethod with bookid: {}", id);
		BookDTO bookDTO = new BookDTO();
		Optional<Book> optBook = libraryRepository.findById(id);
		if (optBook.isEmpty()) {
			LOGGER.info("Book not found with id: {}, exiting getBookMethod", id);
			return bookDTO;
		}
		BeanUtils.copyProperties(optBook.get(), bookDTO);
		LOGGER.info("getBookMethod executed successfully for book: {}", id);
		return bookDTO;
	}

	@Override
	public BookDTO addBook(BookDTO bookDTO) {
		LOGGER.info("Executing addBookMethod");
		BookDTO savedBook = saveOrUpdateBook(bookDTO, 0L);
		LOGGER.info("addBookMethod executed successfully");
		return savedBook;
	}

	@Override
	public BookDTO updateBook(BookDTO bookDTO, Long id) {
		LOGGER.info("Executing updateBookMethod");
		BookDTO updatedBook = saveOrUpdateBook(bookDTO, id);
		LOGGER.info("updateBookMethod executed successfully");
		return updatedBook;
	}

	@Override
	public boolean deleteBook(Long id) {
		LOGGER.info("Executing deleteBookMethod with bookid: {}", id);
		boolean flag = false;
		Optional<Book> book = libraryRepository.findById(id);
		LOGGER.info("Is Book exist with id: {}? - {}", id, book.isPresent());
		if (book.isPresent()) {
			LOGGER.info("Deleting Book");
			libraryRepository.deleteById(id);
			LOGGER.info("Book deleted successfully with id: {}", id);
			flag = true;
		}
		LOGGER.info("deleteBookMethod executed successfully");
		return flag;
	}

	private BookDTO saveOrUpdateBook(BookDTO dto, Long id) {
		LOGGER.info("Executing saveOrUpdateBookMethod");
		if (id == 0) {
			LOGGER.info("Adding new book");
			Book book = new Book();
			BeanUtils.copyProperties(dto, book);
			Book addedBook = libraryRepository.save(book);
			LOGGER.info("Book added successfully with id: {}", addedBook.getId());
			dto.setId(addedBook.getId());
		} else {
			LOGGER.info("Updating book with id: {}", id);
			Optional<Book> book = libraryRepository.findById(id);
			LOGGER.info("Is Book exist with id: {}? - {}", id, book.isPresent());
			if (book.isPresent()) {
				LOGGER.info("Updating book");
				Book updateBook = book.get();
				updateBook.setTitle(dto.getTitle());
				updateBook.setAuthor(dto.getAuthor());
				updateBook.setPublicationYear(dto.getPublicationYear());
				libraryRepository.save(updateBook);
				LOGGER.info("Book updated successfully with id: {}", id);
				dto.setId(id);
			}
		}
		LOGGER.info("saveOrUpdateBookMethod executed successfully");
		return dto;
	}

}
