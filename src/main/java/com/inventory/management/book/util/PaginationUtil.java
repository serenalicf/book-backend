package com.inventory.management.book.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

public class PaginationUtil {

  public static Pageable getPageable(Integer pageNo,Integer pageSize,String[] sortString){
    if(sortString != null){
      return PageRequest.of(
          pageNo,
          pageSize,
          getSort(sortString)
      );
    }
    return PageRequest.of(pageNo,pageSize);
  }

  private static Sort.Direction getSortDirection(String direction) {
    if (direction.equals("asc")) {
      return Sort.Direction.ASC;
    } else if (direction.equals("desc")) {
      return Sort.Direction.DESC;
    }

    return Sort.Direction.ASC;
  }
  public static Sort getSort(String[] sortString){
    List<Order> orders = new ArrayList<>();
    if(sortString[0].contains(",")){
      for(String sortStr : sortString){
        String[] sortDetail = sortStr.split(",");
        String fieldToSort = sortDetail[0];
        String direction = sortDetail[1];
        orders.add(new Order(getSortDirection(direction),fieldToSort));
      }
    }else{
      orders.add(new Order(getSortDirection(sortString[1]),sortString[0]));
    }
    return Sort.by(orders);
  }



}
