package dev.carv.bank.account.annotation;

import dev.carv.bank.account.util.UUIDv7;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;

import java.util.EnumSet;

import static org.hibernate.generator.EventType.INSERT;

public class UUIDv7Generator implements BeforeExecutionGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor session, Object owner, Object currentValue, EventType eventType) {
        return UUIDv7.generate();
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return EnumSet.of(INSERT);
    }

}
