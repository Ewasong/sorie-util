package top.sorie.util.spring.provider;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import top.sorie.util.spring.TestApplication;
import top.sorie.util.spring.annotation.Strategy;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestApplication.class)
public class StrategyProviderTest {
    @Autowired
    public StrategyProvider strategyProvider;

    @Test
    void strategyTest() {
        strategyProvider.regiter(BaseStrategyService.class);
        Optional<BaseStrategyService> aStrategy = strategyProvider.obtain(BaseStrategyService.class, "A");
        assertTrue(aStrategy.isPresent());
        assertEquals("Hello, A", aStrategy.get().hello());

        Optional<BaseStrategyService> bStrategy = strategyProvider.obtain(BaseStrategyService.class, "B");
        assertTrue(bStrategy.isPresent());
        assertEquals("Hello, B", bStrategy.get().hello());

        Optional<BaseStrategyService> cStrategy = strategyProvider.obtain(BaseStrategyService.class, "C");
        assertFalse(cStrategy.isPresent());
    }
}