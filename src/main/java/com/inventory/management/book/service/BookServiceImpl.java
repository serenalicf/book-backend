package com.inventory.management.book.service;

import bean.CsvData;
import com.inventory.management.book.constants.CSVHeaderConstants;
import com.inventory.management.book.entity.Book;
import com.inventory.management.book.exception.BookAlreadyExistException;
import com.inventory.management.book.exception.CreateBookException;
import com.inventory.management.book.mapper.BookMapper;
import com.inventory.management.book.repository.BookRepository;
import com.inventory.management.book.request.BookSearchCriteria;
import com.inventory.management.book.request.CreateBookRequest;
import com.inventory.management.book.request.PagingRequest;
import com.inventory.management.book.response.CsvOutputResponse;
import com.inventory.management.book.util.CSVUtil;
import com.inventory.management.book.util.DateUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public List<Book> getBooksByCriteria(BookSearchCriteria bookSearchCriteria, PagingRequest pagingRequest) {
        Pageable pageable = PageRequest.of(pagingRequest.getPageNo(), pagingRequest.getPageSize());
        return bookRepository.findAll(buildSpecification(bookSearchCriteria),pageable).getContent();
    }

    @Override
    public List<Book> getBooksByCriteria(BookSearchCriteria bookSearchCriteria) {
        return bookRepository.findAll(buildSpecification(bookSearchCriteria));
    }

    @Override
    public CsvOutputResponse getBooksInCsvByCriteria(BookSearchCriteria bookSearchCriteria) {
        List<Book> books = getBooksByCriteria(bookSearchCriteria);
        LinkedHashMap<String,Integer> headers = getCsvHeaders();

        List<CsvData> csvDataList = convertToCsvDataList(books,headers);

        byte[] dataBytes = CSVUtil.generateCSVOutputStream(new ArrayList<>(headers.keySet()),csvDataList);


        return CsvOutputResponse.builder()
                .fileName("book_"+ DateUtil.getCurrentLocalDateTimeAsString()+".csv")
                .csvByteArray(dataBytes)
                .build();
    }



    private LinkedHashMap<String,Integer> getCsvHeaders(){
        LinkedHashMap header = new LinkedHashMap<>();
        header.put(CSVHeaderConstants.ENTRY_ID,0);
        header.put(CSVHeaderConstants.TITLE,1);
        header.put(CSVHeaderConstants.AUTHOR,2);
        header.put(CSVHeaderConstants.GENRE,3);
        header.put(CSVHeaderConstants.PUBLICATION_DATE,4);
        header.put(CSVHeaderConstants.ISBN,5);
        return header;
    }

    private List<CsvData> convertToCsvDataList(List<Book> books,Map<String,Integer> headers){
        List<CsvData> csvDataList = new ArrayList<>();
        for(Book book : books){
            List<String> data = new ArrayList<>();
            data.add(headers.get(CSVHeaderConstants.ENTRY_ID),book.getEntryId().toString());
            data.add(headers.get(CSVHeaderConstants.TITLE),book.getTitle());
            data.add(headers.get(CSVHeaderConstants.AUTHOR),book.getAuthor());
            data.add(headers.get(CSVHeaderConstants.GENRE),book.getGenre());
            data.add(headers.get(CSVHeaderConstants.PUBLICATION_DATE),DateUtil.convertLocalDateToString(book.getPublicationDate()));
            data.add(headers.get(CSVHeaderConstants.ISBN),book.getIsbn());

            CsvData csvData = CsvData.builder()
                    .data(data)
                    .build();
            csvDataList.add(csvData);

        }
        return csvDataList;
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
                        DateUtil.convertStringToLocalDate(bookSearchCriteria.getFromPublicationDate())));
            }

            if(StringUtils.isNotBlank(bookSearchCriteria.getToPublicationDate())){
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("publicationDate"),
                        DateUtil.convertStringToLocalDate(bookSearchCriteria.getToPublicationDate())));

            }

            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };

    }
}
