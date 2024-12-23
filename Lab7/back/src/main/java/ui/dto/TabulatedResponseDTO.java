package ui.dto;

import functions.TabulatedFunction;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TabulatedResponseDTO {
    List<Double> x;
    List<Double> y;

    public static TabulatedResponseDTO from(@Nullable TabulatedFunction tabulatedFunction) {
        if (tabulatedFunction == null) return null;

        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();

        for(int i = 0; i < tabulatedFunction.getCount(); i++){
            x.add(tabulatedFunction.getX(i));
            y.add(tabulatedFunction.getY(i));
        }

        return new TabulatedResponseDTO(x,y);
    }
}
