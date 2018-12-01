package robert.annotations.cache.payments;

import org.springframework.cache.annotation.CacheEvict;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@CacheEvict(cacheNames = {"debts", "debtors", "moneyBalance", "debtBalance"}, allEntries = true)
public @interface PaymentsCacheEvict {
}
