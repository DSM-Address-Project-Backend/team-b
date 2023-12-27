package com.address.dsmproject.global.config

import org.hibernate.boot.model.FunctionContributions
import org.hibernate.boot.model.FunctionContributor
import org.hibernate.dialect.function.StandardSQLFunction
import org.hibernate.type.StandardBasicTypes
import org.springframework.context.annotation.Configuration

@Configuration
class CustomFunctionContributor : FunctionContributor {
    override fun contributeFunctions(functionContributions: FunctionContributions) {
        functionContributions.functionRegistry.register(
            "match",
            StandardSQLFunction("MATCH(?1) AGAINST(?2 IN BOOLEAN MODE)", StandardBasicTypes.DOUBLE)
        )
    }
}