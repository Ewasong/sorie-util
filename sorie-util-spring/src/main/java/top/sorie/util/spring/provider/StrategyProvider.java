package top.sorie.util.spring.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import top.sorie.util.core.exception.UtilException;
import top.sorie.util.core.utils.StringUtils;
import top.sorie.util.spring.annotation.Strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 策略提供者
 * @author soriee
 * @date 2022/1/9 22:50
 */
@Slf4j
@Component
public class StrategyProvider implements ApplicationContextAware {

    private final Map<Class<?>, Map<String, Object>> strategyMap = new ConcurrentHashMap<>(32);
    private volatile ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 注册策略
     * @param strategyClazz
     */
    public <T> void regiter(Class<T> strategyClazz) {
        Map<String, T> beanMap = applicationContext.getBeansOfType(strategyClazz);
        String name = null;
        Map<String, Object> strategyNameToBean = new HashMap<String, Object>(16);
        for (T per : beanMap.values()) {
            if (!per.getClass().isAnnotationPresent(Strategy.class)) {
                log.warn(strategyClazz.getName() + " 必须携带Strategy注解");
                continue;
            }
            Strategy strategy = per.getClass().getAnnotation(Strategy.class);
            if (strategyNameToBean.containsKey(strategy.value())) {
                throw new UtilException("SPI注解重复 + " + strategy.value());
            }
            // 注册
            strategyNameToBean.put(strategy.value(), per);
        }
        strategyMap.put(strategyClazz, strategyNameToBean);
    }

    /**
     * 获取策略
     * @param strategyClazz
     * @public type 策略类型
     */
    public <T> Optional<T> obtain(Class<T> strategyClazz, String type) {
        if (strategyClazz == null || StringUtils.isEmpty(type)) {
            return Optional.empty();
        }
        if (!strategyMap.containsKey(strategyClazz)) {
            return Optional.empty();
        }
        return Optional.ofNullable((T)strategyMap.get(strategyClazz).get(type));
    }
}
