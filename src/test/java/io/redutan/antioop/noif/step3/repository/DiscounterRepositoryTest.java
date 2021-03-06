package io.redutan.antioop.noif.step3.repository;

import io.redutan.antioop.AntiOopApplication;
import io.redutan.antioop.noif.step3.AbstractDiscounter;
import io.redutan.antioop.noif.step3.AmtDiscounter;
import io.redutan.antioop.noif.step3.RateDiscounter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by redutan on 2016. 4. 1..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AntiOopApplication.class)
@Transactional
@Slf4j
public class DiscounterRepositoryTest {
	
	@Autowired
    DiscounterRepository<AbstractDiscounter> repository;

    @Before
    public void setUp() throws Exception {
        RateDiscounter naverDiscounter = new RateDiscounter();
        naverDiscounter.setCode("NAVER");
        naverDiscounter.setName("네이버할인");
        naverDiscounter.setRate(10);

        RateDiscounter danawaDiscounter = new RateDiscounter();
        danawaDiscounter.setCode("DANAWA");
        danawaDiscounter.setName("다나와할인");
        danawaDiscounter.setRate(15);

        AmtDiscounter fancafeDiscounter = new AmtDiscounter();
        fancafeDiscounter.setCode("FANCAFE");
        fancafeDiscounter.setName("팬카페할인");
        fancafeDiscounter.setAmt(1000);

        List<AbstractDiscounter> list = Arrays.asList(naverDiscounter, danawaDiscounter, fancafeDiscounter);

        repository.save(list);
    }

    @Test
    public void findByCode_Naver() throws Exception {
        // Given
        final String code = "NAVER";
        // When
        AbstractDiscounter discounter = repository.findByCode(code);
        log.info("{} discounter = {}", code, discounter);
        // Then
        assertDiscounter(discounter, code, 20000, 2000);
    }

    @Test
    public void findByCode_Danawa() throws Exception {
        // Given
        final String code = "DANAWA";
        // When
        AbstractDiscounter discounter = repository.findByCode(code);
        log.info("{} discounter = {}", code, discounter);
        // Then
        assertDiscounter(discounter, code, 20000, 3000);
    }

    @Test
    public void findByCode_Fancafe() throws Exception {
        // Given
        final String code = "FANCAFE";
        // When
        AbstractDiscounter discounter = repository.findByCode(code);
        log.info("{} discounter = {}", code, discounter);
        // Then
        assertDiscounter(discounter, code, 20000, 1000);
    }

    private void assertDiscounter(AbstractDiscounter discounter, String code, long amt, long discountAmt) {
        assertThat(discounter.getCode(), is(code));
        assertThat(discounter.getDiscountAmt(amt), is(discountAmt));
    }
}