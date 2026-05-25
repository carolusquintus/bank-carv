package dev.carv.bank.account.annotation;

import dev.carv.bank.account.util.UUIDv7;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

public class UUIDv7Generator  extends SequenceStyleGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor session, Object owner) {
        return UUIDv7.generate();
    }

}
