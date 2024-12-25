package ui.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TabulatedSimpleRequestDTO {
    @NotNull
    private Double xStart;
    @NotNull
    private Double xEnd;
    @Min(2)
    @NotNull
    private Integer count;
    @NotNull
    private String className;
}
