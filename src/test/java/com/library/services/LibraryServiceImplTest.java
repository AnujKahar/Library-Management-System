package com.library.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.library.model.Book;
import com.library.model.BookDTO;
import com.library.repository.LibraryRepository;

@SpringBootTest
class LibraryServiceImplTest {

	@Autowired
	private LibraryService libraryService;

	@MockBean
	private LibraryRepository libraryRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllBooks() {
		List<Book> books = new ArrayList<>();
		books.add(new Book(1L, "The Heaven", "John", "2020"));
		books.add(new Book(2L, "The Nights", "Anuj", "2019"));

		when(libraryRepository.findAll()).thenReturn(books);

		List<BookDTO> result = libraryService.getAllBooks();

		assertEquals(2, result.size());
		assertEquals("The Heaven", result.get(0).getTitle());
		assertEquals("Anuj", result.get(1).getAuthor());
	}

	@Test
	void testGetBook() {
		Book book = new Book(1L, "The Heaven", "John", "2020");
		Optional<Book> optionalBook = Optional.of(book);

		when(libraryRepository.findById(1L)).thenReturn(optionalBook);

		BookDTO result = libraryService.getBook(1L);

		assertEquals("The Heaven", result.getTitle());
		assertEquals("John", result.getAuthor());
		assertEquals("2020", result.getPublicationYear());
	}

	@Test
	void testAddBook() {
		BookDTO bookDTO = new BookDTO(null, "The Nights", "Anuj", "2019");
		Book savedBook = new Book(1L, "The Nights", "Anuj", "2019");

		when(libraryRepository.save(any(Book.class))).thenReturn(savedBook);

		BookDTO result = libraryService.addBook(bookDTO);

		assertEquals(1L, result.getId());
		assertEquals("The Nights", result.getTitle());
		assertEquals("Anuj", result.getAuthor());
		assertEquals("2019", result.getPublicationYear());
	}

	@Test
	void testUpdateBook() {
		BookDTO bookDTO = new BookDTO(null, "The Heaven", "John", "2022");
		Book existingBook = new Book(1L, "Book1", "Author1", "2020");
		Optional<Book> optionalBook = Optional.of(existingBook);

		when(libraryRepository.findById(1L)).thenReturn(optionalBook);
		when(libraryRepository.save(any(Book.class))).thenReturn(existingBook);
		System.out.println(existingBook.toString());
		BookDTO result = libraryService.updateBook(bookDTO, 1L);
		System.out.println(existingBook.toString());

		assertEquals(1L, result.getId());
		assertEquals("The Heaven", result.getTitle());
		assertEquals("John", result.getAuthor());
		assertEquals("2022", result.getPublicationYear());
	}

	@Test
	void testDeleteBook() {
		Book existingBook = new Book(1L, "The Nights", "Anuj", "2020");
		Optional<Book> optionalBook = Optional.of(existingBook);
		when(libraryRepository.findById(1L)).thenReturn(optionalBook);
		boolean result = libraryService.deleteBook(1L);
		assertTrue(result);
		verify(libraryRepository, times(1)).deleteById(1L);
	}

	@Test
	void testDeleteBook_NotFound() {
		Optional<Book> optionalBook = Optional.empty();
		when(libraryRepository.findById(1L)).thenReturn(optionalBook);
		boolean result = libraryService.deleteBook(1L);
		assertFalse(result);
		verify(libraryRepository, never()).deleteById(1L);
	}
}
