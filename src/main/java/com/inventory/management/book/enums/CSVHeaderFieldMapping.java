package com.inventory.management.book.enums;

public enum CSVHeaderFieldMapping {
  ENTRY_ID("Entry ID","entryID"),
  TITLE("Title","title"),
  AUTHOR("Author","author"),
  GENRE("Genre","genre"),

  PUBLICATION_DATE("Publication Date","publicationDate"),

  ISBN("ISBN","isbn")
  ;

  private final String headerName; //csv header name

  private final String fieldName; //field name in entity class


  CSVHeaderFieldMapping(String headerName, String fieldName) {
    this.headerName = headerName;
    this.fieldName = fieldName;
  }

  public String getHeaderName() {
    return headerName;
  }

  public String getFieldName() {
    return fieldName;
  }
}
