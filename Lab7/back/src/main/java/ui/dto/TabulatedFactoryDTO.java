package ui.dto;

import functions.factory.TabulatedFunctionFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TabulatedFactoryDTO {
    String className;

    static public TabulatedFactoryDTO from(TabulatedFunctionFactory factory) {
        return new TabulatedFactoryDTO(factory.getClass().getSimpleName());
    }
}
