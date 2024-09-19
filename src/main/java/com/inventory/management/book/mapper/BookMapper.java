package com.inventory.management.book.mapper;

import com.inventory.management.book.entity.Book;
import com.inventory.management.book.request.CreateBookRequest;
import com.inventory.management.book.response.BookDto;
import com.inventory.management.book.util.DateUtil;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    default BookDto toDto(Book book){
        return BookDto.builder()
                .entryId(book.getEntryId())
                .author(book.getAuthor())
                .publicationDate(DateUtil.convertLocalDateToString(book.getPublicationDate()))
                .title(book.getTitle())
                .genre(book.getGenre())
                .isbn(book.getIsbn())
                .build();
    }

    default List<BookDto> toDtoList(List<Book> bookList){
        return bookList.stream()
                .map(book -> toDto(book))
                .collect(Collectors.toList());
    }

    default Book toEntity(CreateBookRequest createBookRequest){

        return Book.builder()
                .author(createBookRequest.getAuthor())
                .title(createBookRequest.getTitle())
                .genre(createBookRequest.getGenre())
                .isbn(createBookRequest.getIsbn())
                .publicationDate(DateUtil.convertStringToLocalDate(createBookRequest.getPublicationDate()))
                .build();
    }
}
