package top.sorie.util.spring.provider;

import org.springframework.stereotype.Component;
import top.sorie.util.spring.annotation.Strategy;

/**
 * B策略
 * @author soriee
 * @date 2022/1/9 23:10
 */
@Component
@Strategy("B")
public class BStrategyService implements BaseStrategyService {
    @Override
    public String hello() {
        return "Hello, B";
    }
}