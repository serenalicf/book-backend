package com.inventory.management.book.mapper;

import com.inventory.management.book.entity.Book;
import com.inventory.management.book.request.CreateBookRequest;
import com.inventory.management.book.response.BookDto;
import com.inventory.management.book.response.PageDto;
import com.inventory.management.book.util.DateUtil;
import java.util.ArrayList;
import java.util.Optional;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    default Book toEntity(CreateBookRequest createBookRequest){

        return Book.builder()
                .author(createBookRequest.getAuthor())
                .title(createBookRequest.getTitle())
                .genre(createBookRequest.getGenre())
                .isbn(createBookRequest.getIsbn())
                .publicationDate(DateUtil.convertStringToLocalDate("publicationDate",createBookRequest.getPublicationDate()))
                .build();
    }

    default PageDto<BookDto> toPagedDto(Page<Book> bookPage){
        List<BookDto> bookDtos = Optional.ofNullable(bookPage)
            .map(Page::getContent)
            .orElse(new ArrayList<>())
            .stream()
            .map(this::toDto)
            .collect(Collectors.toList());
        return PageDto.<BookDto>builder()
            .content(bookDtos)
            .totalPages(
                Optional.ofNullable(bookPage)
                        .map(Page::getTotalPages)
                    .orElse(0)
            )
            .totalElements(
                Optional.ofNullable(bookPage)
                    .map(Page::getTotalElements)
                    .orElse(0L)
            )
            .build();
    }
}
