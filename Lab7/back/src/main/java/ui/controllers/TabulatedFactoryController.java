package ui.controllers;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.web.bind.annotation.*;
import ui.dto.TabulatedFactoryDTO;
import ui.exeptions.BasedException;
import ui.services.TabulatedFactoryService;

import java.util.List;

@RestController()
@RequestMapping("/factory")
public class TabulatedFactoryController {

    SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    @Autowired
    TabulatedFactoryService tabulatedFactoryService;

    @GetMapping
    public TabulatedFactoryDTO getFactory() throws BasedException {
        return TabulatedFactoryDTO.from(tabulatedFactoryService.getActive(securityContextHolderStrategy.getContext()));
    }

    @PostMapping
    public void setFactory(@RequestParam @NonNull String className) throws BasedException {
        tabulatedFactoryService.setActive(securityContextHolderStrategy.getContext(), tabulatedFactoryService.construct(className));
    }

    @GetMapping("/all")
    public List<TabulatedFactoryDTO> getFactories() {
        return tabulatedFactoryService.getAllFactories();
    }


}
