package ui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasedExceptionDTO {
    @NonNull
    private String message;
    @NonNull
    private String details;
}
