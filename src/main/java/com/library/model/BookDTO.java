package com.library.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
	private Long id;

	@NotBlank(message = "Please enter title.")
	@Size(max = 25, message = "Please entet title value between 1-25 characters.")
	private String title;

	@NotBlank(message = "Please enter author.")
	@Size(max = 30, message = "Please enter author value between 1-30 characters.")
	private String author;

	@Size(min = 4, max = 4, message = "Please enter valid publication year.")
	private String publicationYear;

	@Override
	public String toString() {
		return "BookDTO [id=" + id + ", title=" + title + ", author=" + author + ", publicationYear=" + publicationYear
				+ "]";
	}

}
