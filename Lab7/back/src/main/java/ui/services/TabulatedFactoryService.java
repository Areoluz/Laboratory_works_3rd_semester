package ui.services;

import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;
import jpa.entities.User;
import jpa.repository.UserRepos;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import ui.dto.TabulatedFactoryDTO;
import ui.exeptions.BasedException;
import ui.exeptions.NoFactoryException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TabulatedFactoryService extends AbstractSecurityService{

    private UserRepos userRepos;

    private final String PREFIX = "functions.factory.";

    public TabulatedFunctionFactory construct(String className) throws NoFactoryException {
        try {
            Class<?> fclass = Class.forName(PREFIX + className);

            Class<? extends TabulatedFunctionFactory> factoryClass = fclass.asSubclass(TabulatedFunctionFactory.class);

            return factoryClass.getConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new NoFactoryException(e.getMessage());
        }
    }

    public List<TabulatedFactoryDTO> getAllFactories() {
        List<TabulatedFactoryDTO> factories = new ArrayList<>();
        factories.add(new TabulatedFactoryDTO(ArrayTabulatedFunctionFactory.class.getSimpleName()));
        factories.add(new TabulatedFactoryDTO(LinkedListTabulatedFunctionFactory.class.getSimpleName()));
        return factories;
    }

    public TabulatedFunctionFactory getActive(SecurityContext securityContext) throws BasedException {
        return construct(getUser(securityContext).getTabulatedFactory());
    }

    public void setActive(SecurityContext securityContext, TabulatedFunctionFactory factory) throws BasedException {
        User user = getUser(securityContext);
        user.setTabulatedFactory(factory.getClass().getSimpleName());
        userRepos.save(user);
    }

}
