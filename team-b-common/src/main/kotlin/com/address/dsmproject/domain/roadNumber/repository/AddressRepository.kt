package com.address.dsmproject.domain.roadNumber.repository

import com.address.dsmproject.domain.roadNumber.QRoadNumberEntity.roadNumberEntity
import com.address.dsmproject.domain.roadNumber.RoadNumberEntity
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.NumberTemplate
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository


@Repository
class AddressRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {

    fun queryAddress(
        keyword: String,
        type: String,
        page: Long,
    ): List<RoadNumberEntity> {
        val booleanTemplate: NumberTemplate<*> = Expressions.numberTemplate(
            Double::class.javaObjectType, "function('match',{0},{1})", roadNumberEntity.korFullText, keyword
        )

        return jpaQueryFactory
            .selectFrom(roadNumberEntity)
            .where(booleanTemplate.gt(0))
            .offset(page * 10)
            .limit(10)
            .fetch()
    }

    private fun searchFulltext(keyword: String, type: String): BooleanExpression? {
        keyword.ifBlank {
            return null
        }
        return when (type) {
            "KOREAN" -> Expressions.numberTemplate(
                Integer::class.java,
                "function('match',{0},{1})", roadNumberEntity.korFullText, keyword
            ).gt(0)

            else -> Expressions.numberTemplate(
                Integer::class.java,
                "function('match',{0},{1})", roadNumberEntity.korFullText, keyword
            ).gt(0)
        }
    }
}