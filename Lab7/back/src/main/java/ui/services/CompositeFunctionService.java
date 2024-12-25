package ui.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import functions.CompositeFunction;
import functions.MathFunction;
import jpa.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import ui.dto.CompositeFunctionDTO;
import ui.exeptions.BasedException;
import ui.exeptions.CompositeFunctionExists;
import ui.exeptions.NoFuncException;
import jpa.entities.CompositeFunctionEntity;
import jpa.repository.CompositeFunctionRepos;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompositeFunctionService extends AbstractSecurityService {

    @Autowired
    SimpleFunctionService simpleFunctionService;
    @Autowired
    CompositeFunctionRepos functionRepos;

    ObjectMapper mapper = new ObjectMapper();

    private boolean isSimple(String name){
        try {
            simpleFunctionService.construct(name);
            return true;
        }catch (NoFuncException e) {
            return false;
        }
    }

    public MathFunction getMathFunction(User user, String name) throws BasedException {
        Optional<CompositeFunctionEntity> optionalCompositeFunction = functionRepos.findByUserIdAndName(user, name);

        if(optionalCompositeFunction.isPresent()) {
            CompositeFunctionEntity compositeFunctionEntity = optionalCompositeFunction.get();
            return new CompositeFunction(getMathFunction(user, compositeFunctionEntity.getInner()),
                    getMathFunction(user, compositeFunctionEntity.getOuter()));
        }else{
            return simpleFunctionService.construct(name);
        }
    }

    public void CreateComposite(SecurityContext context, String name, String outer, String inner) throws BasedException {
        User user = getUser(context);
        if(isSimple(name) || functionRepos.findByUserIdAndName(user, name).isPresent())
            throw new CompositeFunctionExists("Функция с таким именем уже существует.");

        String error = "Первой";
        try {
            getMathFunction(user, outer);
            error = "Второй";
            getMathFunction(user, inner);
        }catch (NoFuncException e) {
            throw new NoFuncException(error + " функции не существует");
        }

        CompositeFunctionEntity compositeFunctionEntity = new CompositeFunctionEntity();
        compositeFunctionEntity.setName(name);
        compositeFunctionEntity.setOuter(outer);
        compositeFunctionEntity.setInner(inner);
        compositeFunctionEntity.setUserId(user);
        functionRepos.save(compositeFunctionEntity);
    };

    public void RemoveComposite(SecurityContext context, String name) throws BasedException {
        User user = getUser(context);
        if(isSimple(name))
            throw new CompositeFunctionExists("Нельзя удалить простую функцию.");

        Optional<CompositeFunctionEntity> optionalCompositeFunction = functionRepos.findByUserIdAndName(user, name);
        if(optionalCompositeFunction.isPresent()) throw new NoFuncException("Такой функции не существует");
    }

    public MathFunction getComposite(SecurityContext context, String name) throws BasedException {
        User user = getUser(context);
        try {
            return getMathFunction(user, name);
        }catch (NoFuncException e) {
            throw new NoFuncException("Такой функции не существует :(");
        }
    }

    public List<CompositeFunctionDTO> getAllComposite(SecurityContext context) throws BasedException {
        User user = getUser(context);
        return functionRepos.findByUserId(user).stream().map((e) -> mapper.convertValue(e, CompositeFunctionDTO.class)).collect(Collectors.toList());
    }

}
