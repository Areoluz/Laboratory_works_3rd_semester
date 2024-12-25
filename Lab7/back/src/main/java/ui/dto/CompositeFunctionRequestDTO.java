package ui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompositeFunctionRequestDTO {
    @NonNull
    private String name;
    @NonNull
    private String inner;
    @NonNull
    private String outer;
}
