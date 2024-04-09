package com.example.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDTO {

    @Size(min = 2, max = 50)
    @NotNull
    private String title;

    @Size(min = 2, max = 50)
    @NotNull
    private String author;

    @NotNull
    private Year publicationYear;

    @Size(min = 10, max = 10)
    @NotNull
    private String isbn;
}
