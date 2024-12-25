package ui.controllers;

import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;
import functions.ArrayTabulatedFunction;
import functions.MathFunction;
import functions.TabulatedFunction;
import functions.factory.TabulatedFunctionFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.web.bind.annotation.*;
import ui.dto.SimpleFuncDTO;
import ui.dto.TabulatedArrayRequestDTO;
import ui.dto.TabulatedResponseDTO;
import ui.dto.TabulatedSimpleRequestDTO;
import ui.exeptions.ArrayExeptions;
import ui.exeptions.BasedException;
import ui.services.CompositeFunctionService;
import ui.services.SimpleFunctionService;
import ui.services.TabulatedFactoryService;
import ui.services.TempStorage;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/functions")
@Tag(name = "Current function controller", description = "думаю разберешься")
public class TabulatedFunctionController {
    @Autowired
    TempStorage tempStorage;
    @Autowired
    TabulatedFactoryService tabulatedFactoryService;
    @Autowired
    SimpleFunctionService simpleFunctionService;
    @Autowired
    CompositeFunctionService compositeFunctionService;

    SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    @GetMapping("/tabulated")
    public TabulatedResponseDTO getTabulatedFunction()throws BasedException {
        return TabulatedResponseDTO.from(tempStorage.getFunction(securityContextHolderStrategy.getContext()));
    }

    @PostMapping("/array")
    public TabulatedResponseDTO setTabulatedFunction(@RequestBody @NonNull TabulatedArrayRequestDTO request) throws BasedException {
        try {
            SecurityContext context = securityContextHolderStrategy.getContext();

            TabulatedFunctionFactory factory = tabulatedFactoryService.getActive(context);

            TabulatedFunction tabulatedFunction = factory.create(request.getX().stream().mapToDouble(d -> d).toArray(), request.getY().stream().mapToDouble(d -> d).toArray());
            tempStorage.setFunction(context, tabulatedFunction);

            return TabulatedResponseDTO.from(tabulatedFunction);
        } catch (IllegalArgumentException | DifferentLengthOfArraysException | ArrayIsNotSortedException e) {
            throw new ArrayExeptions(e.getMessage());
        }
    }

    @PostMapping("/simple")
    public TabulatedResponseDTO setTabulatedFunctionFromSimple(@RequestBody @NonNull TabulatedSimpleRequestDTO request) throws BasedException {
        try {
            SecurityContext context = securityContextHolderStrategy.getContext();

            TabulatedFunctionFactory factory = tabulatedFactoryService.getActive(context);
            MathFunction function = compositeFunctionService.getComposite(context, request.getClassName());

            TabulatedFunction tabulatedFunction = factory.create(function, request.getXStart(), request.getXEnd(), request.getCount());
            tempStorage.setFunction(securityContextHolderStrategy.getContext(), tabulatedFunction);

            return TabulatedResponseDTO.from(tabulatedFunction);
        } catch (IllegalArgumentException | DifferentLengthOfArraysException | ArrayIsNotSortedException e) {
            throw new ArrayExeptions(e.getMessage());
        }
    }

    @GetMapping("simple/all")
    @Operation(summary = "Get all functions", description = "ВКЛЮЧАЯ составные")
    List<SimpleFuncDTO> getSimpleFunctions() throws BasedException {
        return Stream.concat(simpleFunctionService.getSimpleFunctions().stream(),
                compositeFunctionService.getAllComposite(securityContextHolderStrategy.getContext())
                        .stream().map((e) -> new SimpleFuncDTO(e.getName(), e.getName()))).toList();
    }
}
