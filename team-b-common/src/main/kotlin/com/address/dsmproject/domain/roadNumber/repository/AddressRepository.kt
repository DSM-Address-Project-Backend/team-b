package com.address.dsmproject.domain.roadNumber.repository

import com.address.dsmproject.domain.roadNumber.QRoadNumberEntity.roadNumberEntity
import com.address.dsmproject.domain.roadNumber.RoadNumberEntity
import com.address.dsmproject.domain.roadNumber.repository.vo.AutoCompletionAddressVO
import com.address.dsmproject.domain.roadNumber.repository.vo.QAutoCompletionAddressVO
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions.numberTemplate
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class AddressRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {

    fun autoCompletion(keyword: String, type: String): List<AutoCompletionAddressVO> {
        return jpaQueryFactory
            .select(
                QAutoCompletionAddressVO(
                    roadNumberEntity.cityProvinceName,
                    roadNumberEntity.countyDistricts,
                    roadNumberEntity.eupMyeonDong
                )
            )
            .from(roadNumberEntity)
            .where(searchFulltext(keyword, type))
            .limit(LIMIT)
            .fetch()
    }

    fun queryAddress(
        keyword: String,
        type: String,
        page: Long,
    ): List<RoadNumberEntity> {
        val numberTemplate = numberTemplate(
            Double::class.javaObjectType,
            "function('match', {0}, {1})",
            roadNumberEntity.korFullText,
            keyword
        )

        return jpaQueryFactory
            .selectFrom(roadNumberEntity)
            .where(numberTemplate.gt(0))
            .offset(page * LIMIT)
            .limit(LIMIT)
            .fetch()
    }

    private fun searchFulltext(keyword: String, type: String): BooleanExpression? {
        keyword.ifBlank {
            return null
        }
        return when (type) {
            "KOREAN" -> numberTemplate(
                Double::class.javaObjectType,
                "function('match', {0}, {1})",
                roadNumberEntity.korFullText,
                keyword
            ).gt(0)

            else -> numberTemplate(
                Double::class.javaObjectType,
                "function('match', {0}, {1})",
                roadNumberEntity.engFullText,
                keyword
            ).gt(0)
        }
    }

    companion object {
        const val LIMIT = 10L
    }
}