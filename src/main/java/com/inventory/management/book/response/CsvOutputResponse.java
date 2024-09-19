package com.inventory.management.book.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CsvOutputResponse {
    private String fileName;
    private byte[] csvByteArray;

}
