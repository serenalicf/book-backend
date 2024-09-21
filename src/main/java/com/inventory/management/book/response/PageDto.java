package com.inventory.management.book.response;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T> {

  private List<T> content;

  private int totalPages;

  private long totalElements;

}
