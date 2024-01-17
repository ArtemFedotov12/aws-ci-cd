package com.start.springlearningdemo.repository;

import com.start.springlearningdemo.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * By default, tests annotated with @DataJpaTest are transactional and roll back at the end of each test.
 */
@DataJpaTest(showSql = false)
/**
 * replace = Replace.ANY or replace = Replace.AUTO_CONFIGURED,
 * Spring Boot would replace the DataSource with an embedded in-memory database.
 * This is helpful when you want a lightweight, fast-starting database for testing,
 * and your tests don't rely on database-specific features or behavior.
 * Embedded databases like H2, HSQLDB, or Derby are typically used in this scenario,
 * as they don't require separate installation and can run entirely within the JVM memory.
 */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public abstract class BaseRepositoryIT {

    @Autowired
    protected ItemRepository itemRepository;

}
