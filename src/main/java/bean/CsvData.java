package bean;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CsvData {
    List<String> data;
}
