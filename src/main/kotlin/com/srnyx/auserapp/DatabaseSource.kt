package com.srnyx.auserapp

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

import io.github.freya022.botcommands.api.core.db.HikariSourceSupplier
import io.github.freya022.botcommands.api.core.service.annotations.BService

import org.flywaydb.core.Flyway


@BService
class DatabaseSource(config: Config): HikariSourceSupplier {
    override val source = HikariDataSource(HikariConfig().apply { jdbcUrl = config.database })

    init {
        Flyway.configure()
            .dataSource(source)
            .schemas("bc")
            .locations("bc_database_scripts")
            .validateMigrationNaming(true)
            .loggers("slf4j")
            .load().migrate()
    }
}
