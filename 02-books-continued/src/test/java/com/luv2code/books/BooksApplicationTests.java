package com.luv2code.books;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BooksApplicationTests {

	@Test
	void contextLoads(ApplicationContext appCtx) {
        assertThat(appCtx).isNotNull();
	}

}
