package ui.services;

import functions.TabulatedFunction;
import jakarta.annotation.Nullable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;
import ui.exeptions.BasedException;

import java.util.HashMap;
import java.util.Map;

@Service
public class TempStorage extends AbstractSecurityService {
    Map<Long, TabulatedFunction> functions = new HashMap<>();

    public void setFunction(SecurityContext context, @Nullable TabulatedFunction function) throws BasedException {
        functions.put(getId(context), function);
    }

    @Nullable
    public TabulatedFunction getFunction(SecurityContext context) throws BasedException {
        return functions.get(getId(context));
    }

}
