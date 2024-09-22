package com.inventory.management.book.service;

import com.inventory.management.book.entity.Book;
import com.inventory.management.book.exception.BookAlreadyExistException;
import com.inventory.management.book.exception.CreateBookException;
import com.inventory.management.book.mapper.BookMapper;
import com.inventory.management.book.repository.BookRepository;
import com.inventory.management.book.request.BookSearchCriteria;
import com.inventory.management.book.request.CreateBookRequest;
import com.inventory.management.book.util.DateUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class BookServiceImpl implements BookService{

    @Autowired
    BookRepository bookRepository;

    @Override
    public Book createBook(CreateBookRequest createBookRequest) throws BookAlreadyExistException {
        try {
            bookRepository.findByIsbn(createBookRequest.getIsbn())
                    .ifPresent((book) -> {
                        throw new BookAlreadyExistException(createBookRequest.getIsbn());
                    });

            Book book = BookMapper.INSTANCE.toEntity(createBookRequest);
            return bookRepository.save(book);
        } catch(BookAlreadyExistException bae){
            throw bae;
        }
        catch (Exception e){
            log.error(e.getMessage(), e);
            throw new CreateBookException(createBookRequest.getIsbn());
        }
    }

    @Override
    public Page<Book> getBooksByCriteria(BookSearchCriteria bookSearchCriteria, Pageable pageable) {
        return bookRepository.findAll(buildSpecification(bookSearchCriteria),pageable);
    }

    @Override
    public List<Book> getBooksByCriteria(BookSearchCriteria bookSearchCriteria) {
        return bookRepository.findAll(buildSpecification(bookSearchCriteria));
    }
    private Specification<Book> buildSpecification(BookSearchCriteria bookSearchCriteria){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            if(StringUtils.isNotBlank(bookSearchCriteria.getAuthor())){
                predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("author")),
                        "%" + bookSearchCriteria.getAuthor().toLowerCase() + "%"));
            }

            if(StringUtils.isNotBlank(bookSearchCriteria.getTitle())){
                predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),
                        "%" + bookSearchCriteria.getTitle().toLowerCase() + "%"));
            }

            if(StringUtils.isNotBlank(bookSearchCriteria.getGenre())){
                predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("genre")),
                        "%" + bookSearchCriteria.getGenre() + "%"));
            }

            if(StringUtils.isNotBlank(bookSearchCriteria.getIsbn())){
                predicateList.add(criteriaBuilder.equal(root.get("isbn"),
                        bookSearchCriteria.getIsbn()));
            }

            if(StringUtils.isNotBlank(bookSearchCriteria.getFromPublicationDate())){
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("publicationDate"),
                        DateUtil.convertStringToLocalDate(bookSearchCriteria.getFromPublicationDate(),"yyyy-MM-dd")));
            }

            if(StringUtils.isNotBlank(bookSearchCriteria.getToPublicationDate())){
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("publicationDate"),
                        DateUtil.convertStringToLocalDate(bookSearchCriteria.getFromPublicationDate(),"yyyy-MM-dd")));

            }

            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };

    }
}
