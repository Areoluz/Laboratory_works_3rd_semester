package ui.services;

import functions.*;
import lombok.AllArgsConstructor;
import org.reflections.Reflections;
import org.springframework.stereotype.Service;
import ui.annotations.UIFunction;
import ui.dto.SimpleFuncDTO;
import ui.exeptions.NoFuncException;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SimpleFunctionService {

    private final String PREFIX = "functions.";

    public List<SimpleFuncDTO> getSimpleFunctions() {
        Reflections reflections = new Reflections(PREFIX);

        return reflections.getTypesAnnotatedWith(UIFunction.class).stream().sorted(
                (a, b) -> {
                    UIFunction annotation1 = a.getAnnotation(UIFunction.class);
                    UIFunction annotation2 = b.getAnnotation(UIFunction.class);
                    Comparator<String> stringComparator = Comparator.naturalOrder();
                    Comparator<Integer> integerComparator = Comparator.reverseOrder();
                    return integerComparator.compare(annotation1.priority(), annotation2.priority()) * 100000 + stringComparator.compare(annotation1.localizedName(), annotation2.localizedName());
                }
        ).map((func) -> {
            UIFunction annotation = func.getAnnotation(UIFunction.class);
            return new SimpleFuncDTO(func.getSimpleName(), annotation.localizedName());
        }).collect(Collectors.toList());
    }

    public MathFunction construct(String className) throws NoFuncException {
        try {
            Class<?> fclass = Class.forName(PREFIX + className);

            Class<? extends MathFunction> funcClass = fclass.asSubclass(MathFunction.class);

            return funcClass.getConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new NoFuncException("Такой простой функции не существует");
        }
    }

}
