package com.josepedevs.pcrepair;

import com.josepedevs.pcrepair.config.AppPropertiesReader;
import com.josepedevs.pcrepair.infra.writer.factory.PersonWriterFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDataBatchAppTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private PersonWriterFactory writerFactory;

    @Autowired
    private AppPropertiesReader props;

    @Test
    void contextLoads_GivenApp_ThenAllWriterBeansExist() {
        assertNotNull(context.getBean(PersonWriterFactory.class));
        assertNotNull(context.getBean("csvPersonWriterStrategy"));
        assertNotNull(context.getBean("jsonPersonWriterStrategy"));
        assertNotNull(context.getBean("personFileWriter"));
    }

    @Test
    void factory_GivenCsvFormat_ThenStrategyBeanInjected() {
        props.setExportFormat("csv");

        final var strategy = writerFactory.getStrategy("csv");

        assertEquals("CsvPersonWriterStrategy", strategy.getClass().getSimpleName());
    }

    @Test
    void factory_GivenJsonFormat_ThenStrategyBeanInjected() {
        props.setExportFormat("json");

        final var strategy = writerFactory.getStrategy("json");
        assertEquals("JsonPersonWriterStrategy", strategy.getClass().getSimpleName());
    }

}