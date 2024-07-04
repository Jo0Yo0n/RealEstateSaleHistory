package com.kosa.realestate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.kosa.realestate.realestates.repository.RealEstateSaleRepository;
import com.kosa.realestate.realestates.service.IRealEstateSaleService;
import com.kosa.realestate.realestates.service.RealEstateSaleService;


@SpringBootTest
public class RealEstateServiceTest {



    @Autowired
    private IRealEstateSaleService realEstateSaleService;

    @Test
    public void testSelectRealEstateCount() {

        // Then
        int result = realEstateSaleService.selectRealEstateCount(8, "수유동", 0, 180, 0, 321);
        assertEquals(55, result, "The expected result should be 55");
    }
}