package com.address.dsmproject.global.config

import org.hibernate.boot.model.FunctionContributions
import org.hibernate.dialect.DatabaseVersion
import org.hibernate.dialect.MySQLDialect
import org.hibernate.type.StandardBasicTypes
import org.springframework.stereotype.Component

@Component
class MysqlDialectConfig : MySQLDialect(DatabaseVersion.make(8)) {
    override fun initializeFunctionRegistry(functionContributions: FunctionContributions) {
        super.initializeFunctionRegistry(functionContributions)

        functionContributions.functionRegistry.registerPattern(
            "MATCH",
            "match(?1) against('?2' in boolean mode)",
            functionContributions.typeConfiguration.basicTypeRegistry.resolve(StandardBasicTypes.DOUBLE)
        )
    }
}
