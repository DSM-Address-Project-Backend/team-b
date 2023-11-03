package com.address.dsmproject.config

import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class BatchConfig(
    private val dataSource: DataSource,
) : DefaultBatchConfiguration() {

    override fun getDataSource(): DataSource = dataSource
}
