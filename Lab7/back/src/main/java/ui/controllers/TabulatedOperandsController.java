package ui.controllers;

import functions.TabulatedFunction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import operations.TabulatedDifferentialOperator;
import operations.TabulatedFunctionOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.web.bind.annotation.*;
import ui.dto.TabulatedResponseDTO;
import ui.exeptions.BasedException;
import ui.exeptions.FunctionExceptionException;
import ui.exeptions.NoOperandException;
import ui.exeptions.NoOperationException;
import ui.services.SereService;
import ui.services.TabulatedFactoryService;
import ui.services.TempStorage;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/operands")
@Tag(name = "Operand controller", description =
        "op1 - 1ый операнд | " +
        "op2 - 2ой операнд  | " +
        "result - результат | " +
        "op3 - операнд дифференциирования | " +
        "result2 - результат дифференциирования")
public class TabulatedOperandsController {

    @Autowired
    TempStorage tempStorage;
    @Autowired
    SereService sereService;
    @Autowired
    TabulatedFactoryService tabulatedFactoryService;

    SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    // Айдишники операндов
    // op1 - 1ый операнд
    // op2 - 2ой операнд
    // result - результат
    // op3 - операнд дифференциирования
    // result2 - результат дифференциирования

    @Operation(summary = "Set operand from current")
    @PostMapping("/set")
    public TabulatedResponseDTO setOperandFromCurrent(@RequestParam("id") String id) throws BasedException {
        SecurityContext context = securityContextHolderStrategy.getContext();
        TabulatedFunction function = tempStorage.getFunction(context);
        tempStorage.setOperand(context, id,function);
        return TabulatedResponseDTO.from(function);
    }

    @Operation(summary = "Set current from operand")
    @PostMapping("/setCurrent")
    public TabulatedResponseDTO setCurrentFromOperand(@RequestParam("id") String id) throws BasedException {
        SecurityContext context = securityContextHolderStrategy.getContext();
        TabulatedFunction function = tempStorage.getOperand(context, id);
        tempStorage.setFunction(context, function);
        return TabulatedResponseDTO.from(function);
    }

    @Operation(summary = "Get operand")
    @GetMapping("/get")
    public TabulatedResponseDTO getOperand(@RequestParam("id") String id) throws BasedException {
        SecurityContext context = securityContextHolderStrategy.getContext();
        TabulatedFunction function = tempStorage.getOperand(context, id);
        return TabulatedResponseDTO.from(function);
    }

    @GetMapping("/binary")
    public ResponseEntity<Resource> getOperandSerialized(@RequestParam("id") String id) throws BasedException {
        TabulatedFunction function = tempStorage.getOperand(securityContextHolderStrategy.getContext(), id);
        ByteArrayResource resource = new ByteArrayResource(sereService.serializeFunction(function));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION)
                .body(resource);
    }

    @PostMapping("/binary")
    public TabulatedResponseDTO setOperandSerialized(@RequestParam("id") String id, @RequestBody byte[] data) throws BasedException {
        TabulatedFunction function = sereService.deserializeFunction(data);
        tempStorage.setOperand(securityContextHolderStrategy.getContext(), id, function);
        return TabulatedResponseDTO.from(function);
    }

    @GetMapping("/getAll")
    public Map<String,TabulatedResponseDTO> getAllOperands() throws BasedException {
        SecurityContext context = securityContextHolderStrategy.getContext();
        return tempStorage.getOperands(context).entrySet().stream().collect(Collectors.toMap(
                e -> e.getKey(),
                e -> TabulatedResponseDTO.from(e.getValue())
                )
        );
    }

    @PostMapping("/setY")
    public TabulatedResponseDTO setY(@RequestParam("id") String id, @RequestParam("index") @Min(0) Integer index, @RequestParam("value") Double y) throws BasedException {
        SecurityContext context = securityContextHolderStrategy.getContext();
        TabulatedFunction function = tempStorage.getOperand(context, id);
        if(Objects.isNull(function)) throw new NoOperandException("Такого операнда не существует.");
        try {
            function.setY(index, y);
        }catch (IndexOutOfBoundsException e){
            throw new FunctionExceptionException(e.getMessage());
        }
        return TabulatedResponseDTO.from(function);
    }



    @PostMapping("calculate")
    @Operation(summary = "Calculate result using op1 and op2", description =
            "operations:" +
            "add = +\n" +
            "sub = -\n" +
            "mul = *\n" +
            "div = /\n")
    public TabulatedResponseDTO calculate(@RequestParam("operation") String operation) throws BasedException {
        SecurityContext context = securityContextHolderStrategy.getContext();
        TabulatedFunction operand1 = tempStorage.getOperand(context, "op1");
        if(Objects.isNull(operand1)) throw new NoOperandException("Нету первого операнда.");
        TabulatedFunction operand2 = tempStorage.getOperand(context, "op2");
        if(Objects.isNull(operand2)) throw new NoOperandException("Нету второго операнда.");

        TabulatedFunctionOperationService operationService = new TabulatedFunctionOperationService();

        TabulatedFunction result = switch (operation) {
            case "add" -> operationService.addition(operand1, operand2);
            case "sub" -> operationService.subtraction(operand1, operand2);
            case "mul" -> operationService.multiplication(operand1, operand2);
            case "div" -> operationService.division(operand1, operand2);
            default -> throw new NoOperationException("Такой операции не существует.");
        };

        tempStorage.setOperand(context, "result",result);
        return TabulatedResponseDTO.from(result);
    }

    // Используется только op3 и result2

    @PostMapping("derive")
    public TabulatedResponseDTO fuck() throws BasedException {
        SecurityContext context = securityContextHolderStrategy.getContext();
        TabulatedFunction operand1 = tempStorage.getOperand(context, "op3");
        if(Objects.isNull(operand1)) throw new NoOperandException("Нету операнда. :(");

        TabulatedDifferentialOperator differentialOperator = new TabulatedDifferentialOperator(
                tabulatedFactoryService.getActive(context)
        );

        TabulatedFunction result = differentialOperator.derive(operand1);

        tempStorage.setOperand(context, "result2", result);
        return TabulatedResponseDTO.from(result);
    }


}
