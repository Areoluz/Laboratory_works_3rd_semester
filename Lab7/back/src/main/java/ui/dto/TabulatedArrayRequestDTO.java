package ui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TabulatedArrayRequestDTO {
    @NonNull
    List<Double> x;
    @NonNull
    List<Double> y;
}
