package com.inventory.management.book.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PagingRequest {

    private int pageNo = 0;

    private int pageSize = 10;

}
