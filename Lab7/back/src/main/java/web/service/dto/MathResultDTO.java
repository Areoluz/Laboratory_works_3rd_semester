package web.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MathResultDTO {
    private int id;
    private double x;
    private double y;
    private long hash;
}
