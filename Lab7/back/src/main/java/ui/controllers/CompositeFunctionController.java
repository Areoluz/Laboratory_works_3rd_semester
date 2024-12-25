package ui.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.web.bind.annotation.*;
import ui.dto.CompositeFunctionDTO;
import ui.dto.CompositeFunctionRequestDTO;
import ui.exeptions.BasedException;
import ui.services.CompositeFunctionService;

import java.util.List;

@RestController
@RequestMapping("/composite")
public class CompositeFunctionController {

    SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    @Autowired
    CompositeFunctionService compositeFunctionService;

    @PostMapping
    public void createCompositeFunction(@RequestBody CompositeFunctionRequestDTO requestDTO) throws BasedException {
        compositeFunctionService.CreateComposite(securityContextHolderStrategy.getContext(), requestDTO.getName(), requestDTO.getOuter(), requestDTO.getInner());
    }

    @DeleteMapping
    public void deleteCompositeFunction(@RequestParam("name") String name) throws BasedException {
        compositeFunctionService.RemoveComposite(securityContextHolderStrategy.getContext(), name);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all composite functions", description = "ТОЛЬКО составные")
    public List<CompositeFunctionDTO> getAllCompositeFunctions() throws BasedException {
        return compositeFunctionService.getAllComposite(securityContextHolderStrategy.getContext());
    }

}
