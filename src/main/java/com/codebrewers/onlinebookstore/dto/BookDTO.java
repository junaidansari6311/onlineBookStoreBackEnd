package com.codebrewers.onlinebookstore.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


public class BookDTO {

    @Pattern(regexp = "^[a-zA-Z0-9 ]+[a-zA-Z0-9]{2,}$", message = "Please Provide Book Name")
    public String bookName;

    @Pattern(regexp = "^[A-Za-z. ]+[ ]*[A-Za-z.]*$", message = "Please provide proper author name")
    public String authorName;


    @Length(min = 1, max = 300, message = "Description should between 1-300 characters")
    public String description;

    @NotEmpty(message = "Please Provide Image Name")
    public String imageUrl;

    @Pattern(regexp = "^[A-Za-z0-9]{10}$", message = "ISBN should be of 10 digits and characters")
    public String isbn;

    @Min(value = 1, message = "Book Price cant be 0")
    public double bookPrice;

    @Min(value = 1, message = "Quantity cant be 0")
    public int quantity;

    @Range(min = 999, max = 2020, message = "Year should be between 999 and 2020")
    public int publishingYear;

    public BookDTO(String bookName, String authorName, String description, String isbn, String imageUrl, double bookPrice, int quantity, int publishingYear) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.bookPrice = bookPrice;
        this.isbn = isbn;
        this.quantity = quantity;
        this.description = description;
        this.imageUrl = imageUrl;
        this.publishingYear = publishingYear;
    }
}
