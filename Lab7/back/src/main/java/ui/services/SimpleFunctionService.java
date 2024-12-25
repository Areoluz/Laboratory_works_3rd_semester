package ui.services;

import functions.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ui.dto.SimpleFuncDTO;
import ui.exeptions.NoFuncException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SimpleFunctionService {

    private final String PREFIX = "functions.";

    public List<SimpleFuncDTO> getSimpleFunctions() {
        List<SimpleFuncDTO> simpleFunctions = new ArrayList<>();
        simpleFunctions.add(new SimpleFuncDTO(IdentityFunction.class.getSimpleName(), "Идентичная функция"));
        simpleFunctions.add(new SimpleFuncDTO(ZeroFunction.class.getSimpleName(), "Нулевая функция"));
        simpleFunctions.add(new SimpleFuncDTO(UnitFunction.class.getSimpleName(), "Единичная функция"));
        simpleFunctions.add(new SimpleFuncDTO(SqrFunction.class.getSimpleName(), "Квадратичная функция"));
        return simpleFunctions;
    }

    public MathFunction construct(String className) throws NoFuncException {
        try {
            Class<?> fclass = Class.forName(PREFIX + className);

            Class<? extends MathFunction> funcClass = fclass.asSubclass(MathFunction.class);

            return funcClass.getConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new NoFuncException("Такой функции не существует");
        }
    }

}
