package com.library.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.model.BookDTO;
import com.library.services.LibraryService;

@WebMvcTest(LibraryController.class)
class LibraryTestController {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private LibraryService libraryService;

	@Test
	@WithMockUser(username = "anuj", password = "anuj123")
	void testGetAllBooks() throws Exception {
		List<BookDTO> books = new ArrayList<>();
		books.add(new BookDTO(1L, "The Heaven", "John", "2020"));
		books.add(new BookDTO(2L, "The Nights", "Anuj", "2019"));
		when(libraryService.getAllBooks()).thenReturn(books);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/books")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("The Heaven"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("The Nights"));
	}

	@Test
	@WithMockUser(username = "anuj", password = "anuj123")
	void testGetBook() throws Exception {
		Long id = 1L;
		BookDTO bookDTO = new BookDTO(id, "The Heaven", "John", "2020");
		when(libraryService.getBook(id)).thenReturn(bookDTO);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", id))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("The Heaven"));
	}

	@Test
	@WithMockUser(username = "anuj", password = "anuj123")
	void testAddBook() throws Exception {
		BookDTO bookDTO = new BookDTO(null, "Ramayan", "Valmiki", "1200");
		when(libraryService.addBook(any(BookDTO.class))).thenReturn(bookDTO);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/books").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(bookDTO))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Ramayan"));
	}

	@Test
	@WithMockUser(username = "anuj", password = "anuj123")
	void testUpdateBook() throws Exception {
		Long id = 1L;
		BookDTO bookDTO = new BookDTO(id, "Two States", "Chetan", "2018");
		when(libraryService.updateBook(any(BookDTO.class), eq(id))).thenReturn(bookDTO);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/books/{id}", id).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(bookDTO))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Two States"));
	}

	@Test
	@WithMockUser(username = "anuj", password = "anuj123")
	void testDeleteBook() throws Exception {
		Long id = 1L;
		when(libraryService.deleteBook(id)).thenReturn(true);

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/books/{id}", id))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Book deleted successfully"));
	}
}
