package ui.controllers;

import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;
import functions.ArrayTabulatedFunction;
import functions.MathFunction;
import functions.TabulatedFunction;
import functions.factory.TabulatedFunctionFactory;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.web.bind.annotation.*;
import ui.dto.TabulatedArrayRequestDTO;
import ui.dto.TabulatedResponseDTO;
import ui.dto.TabulatedSimpleRequestDTO;
import ui.exeptions.ArrayExeptions;
import ui.exeptions.BasedException;
import ui.services.SimpleFunctionService;
import ui.services.TabulatedFactoryService;
import ui.services.TempStorage;

@RestController
@RequestMapping("/functions")
public class TabulatedFunctionController {
    @Autowired
    TempStorage tempStorage;
    @Autowired
    SecurityContextHolderStrategy securityContextHolderStrategy;
    @Autowired
    TabulatedFactoryService tabulatedFactoryService;
    @Autowired
    SimpleFunctionService simpleFunctionService;

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
            MathFunction function = simpleFunctionService.construct(request.getClassName());

            TabulatedFunction tabulatedFunction = factory.create(function, request.getXStart(), request.getXEnd(), request.getCount());
            tempStorage.setFunction(securityContextHolderStrategy.getContext(), tabulatedFunction);

            return TabulatedResponseDTO.from(tabulatedFunction);
        } catch (IllegalArgumentException | DifferentLengthOfArraysException | ArrayIsNotSortedException e) {
            throw new ArrayExeptions(e.getMessage());
        }
    }


}
