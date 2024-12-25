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
    Map<Long, Map<String, TabulatedFunction>> operands = new HashMap<>();

    public void setFunction(SecurityContext context, @Nullable TabulatedFunction function) throws BasedException {
        functions.put(getId(context), function);
    }

    @Nullable
    public TabulatedFunction getFunction(SecurityContext context) throws BasedException {
        return functions.get(getId(context));
    }

    public void removeOperand(SecurityContext context, String id) throws BasedException {
        Map<String, TabulatedFunction> user_operands = operands.getOrDefault(getId(context), new HashMap<>());
        user_operands.remove(id);
        operands.put(getId(context), user_operands);
    }

    public void setOperand(SecurityContext context, String id, @Nullable TabulatedFunction function) throws BasedException {
        Map<String, TabulatedFunction> user_operands = operands.getOrDefault(getId(context), new HashMap<>());
        user_operands.put(id, function);
        operands.put(getId(context), user_operands);
    }

    @Nullable
    public TabulatedFunction getOperand(SecurityContext context, String id) throws BasedException {
        Map<String, TabulatedFunction> user_operands = operands.getOrDefault(getId(context), new HashMap<>());
        operands.put(getId(context), user_operands);
        return user_operands.get(id);
    }

    public Map<String, TabulatedFunction> getOperands(SecurityContext context) throws BasedException {
        return operands.getOrDefault(getId(context), new HashMap<>());
    }
}
