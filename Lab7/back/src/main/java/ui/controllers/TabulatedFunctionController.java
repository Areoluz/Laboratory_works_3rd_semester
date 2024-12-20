package ui.controllers;

import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;
import functions.ArrayTabulatedFunction;
import functions.TabulatedFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ui.dto.TabulatedArrayRequestDTO;
import ui.exeptions.ArrayExeptions;
import ui.exeptions.BasedException;
import ui.services.TempStorage;

@RestController
@RequestMapping("/functions")
public class TabulatedFunctionController {
    @Autowired
    TempStorage tempStorage;
    @Autowired
    SecurityContextHolderStrategy securityContextHolderStrategy;

    @PostMapping("/array")
    public void TabulatedArray(@RequestBody TabulatedArrayRequestDTO request) throws BasedException {
        try {
            TabulatedFunction tabulatedFunction = new ArrayTabulatedFunction(request.getX().stream().mapToDouble(d -> d).toArray(), request.getY().stream().mapToDouble(d -> d).toArray());
            tempStorage.setFunction(securityContextHolderStrategy.getContext(), tabulatedFunction);
        } catch (IllegalArgumentException | DifferentLengthOfArraysException | ArrayIsNotSortedException e) {
            throw new ArrayExeptions(e.getMessage());
        }


    }

}
