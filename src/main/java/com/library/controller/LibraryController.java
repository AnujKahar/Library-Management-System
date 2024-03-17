package com.library.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.exception.handler.ResponseMessage;
import com.library.model.BookDTO;
import com.library.services.LibraryService;

@RestController
@RequestMapping("/api/books")
public class LibraryController {

	@Autowired
	private LibraryService libraryService;

	private static Logger LOGGER = LoggerFactory.getLogger(LibraryController.class);

	@GetMapping()
	public List<BookDTO> getAllBooks() {
		LOGGER.info("Received request to fetch all the books");
		List<BookDTO> booksDTO = libraryService.getAllBooks();
		LOGGER.info("Responding with status 200 and body: {}", booksDTO);
		return booksDTO;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getBook(@PathVariable("id") Long id) {
		LOGGER.info("Received request to fetch book with id: {}", id);
		boolean flag = false;
		ResponseMessage responseMessage = null;
		BookDTO bookDTO = libraryService.getBook(id);
		if (bookDTO.getId() == null) {
			flag = true;
			responseMessage = new ResponseMessage(200, "No book found with the given id: " + id, new Date());
		}
		LOGGER.info("Responding with status 200 and body: {}", flag ? null : bookDTO);
		return ResponseEntity.status(HttpStatus.OK).body(flag ? responseMessage : bookDTO);
	}

	@PostMapping
	public BookDTO addBook(@Valid @RequestBody BookDTO bookDTO) {
		LOGGER.info("Received request to add new book");
		return libraryService.addBook(bookDTO);
	}

	@PutMapping("/{id}")
	public BookDTO updateBook(@Valid @RequestBody BookDTO bookDTO, @PathVariable("id") Long id) {
		LOGGER.info("Received request to update book info with id: {}", id);
		return libraryService.updateBook(bookDTO, id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseMessage> deleteBook(@PathVariable("id") Long id) {
		LOGGER.info("Received request to delete book with id: {}", id);
		boolean flag = libraryService.deleteBook(id);
		ResponseMessage responseMessage = new ResponseMessage(200,
				flag ? "Book deleted successfully" : "No book found with the given id: " + id, new Date());
		return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
	}
}
