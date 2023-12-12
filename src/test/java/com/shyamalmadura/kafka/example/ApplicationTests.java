package com.shyamalmadura.kafka.example;

import common.com.shyamalmadura.kafka.example.AbstractContainerLoader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"integration-test"})
class ApplicationTests extends AbstractContainerLoader {

	@Test
	void contextLoads() {
	}

}
