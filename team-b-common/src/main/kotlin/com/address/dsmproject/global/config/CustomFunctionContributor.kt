package com.address.dsmproject.global.config

import org.hibernate.boot.model.FunctionContributions
import org.hibernate.boot.model.FunctionContributor
import org.hibernate.type.StandardBasicTypes

class CustomFunctionContributor : FunctionContributor {
    override fun contributeFunctions(functionContributions: FunctionContributions) {
        functionContributions.functionRegistry.registerPattern(
            "match",
            "match (?1) against (?2 in boolean mode)",
            functionContributions.typeConfiguration.basicTypeRegistry.resolve(StandardBasicTypes.DOUBLE)
        )
    }
}