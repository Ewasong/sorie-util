package top.sorie.util.spring.provider;

import org.springframework.stereotype.Component;
import top.sorie.util.spring.annotation.Strategy;

/**
 * A策略
 * @author soriee
 * @date 2022/1/9 23:10
 */
@Component
@Strategy("A")
public class AStrategyService implements BaseStrategyService {
    @Override
    public String hello() {
        return "Hello, A";
    }
}
