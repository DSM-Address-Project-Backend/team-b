package com.address.dsmproject.domain.roadNumber.repository

import com.address.dsmproject.domain.roadNumber.QRoadNumberEntity
import com.address.dsmproject.domain.roadNumber.QRoadNumberEntity.roadNumberEntity
import com.address.dsmproject.domain.roadNumber.repository.vo.AutoCompletionAddressVO
import com.address.dsmproject.domain.roadNumber.repository.vo.QAutoCompletionAddressVO
import com.address.dsmproject.domain.roadNumber.repository.vo.QSearchAddressVo
import com.address.dsmproject.domain.roadNumber.repository.vo.SearchAddressVo
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions.numberTemplate
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class AddressRepository(
    private val jpaQueryFactory: JPAQueryFactory,
) {
    companion object {
        const val LIMIT = 10L
        const val KOREAN = "KOREAN"
        const val ENGLISH = "ENGLISH"
    }

    fun autoCompletionWithKor(keyword: String): List<AutoCompletionAddressVO> {
        return jpaQueryFactory
            .select(
                QAutoCompletionAddressVO(
                    roadNumberEntity.cityProvinceName,
                    roadNumberEntity.countyDistricts,
                    roadNumberEntity.eupMyeonDong
                )
            )
            .from(roadNumberEntity)
            .where(searchFulltext(keyword, KOREAN, roadNumberEntity))
            .limit(LIMIT)
            .fetch()
    }

    fun autoCompletionWithEng(keyword: String): List<AutoCompletionAddressVO> {
        return jpaQueryFactory
            .select(
                QAutoCompletionAddressVO(
                    roadNumberEntity.cityProvinceNameEng,
                    roadNumberEntity.countyDistrictsEng,
                    roadNumberEntity.eupMyeonDongEng
                )
            )
            .from(roadNumberEntity)
            .where(searchFulltext(keyword, ENGLISH, roadNumberEntity))
            .limit(LIMIT)
            .fetch()
    }

    fun searchAddressWithKor(
        page: Int,
        keyword: String,
    ): List<SearchAddressVo> {
        val rn = QRoadNumberEntity("rn")
        val rn1 = QRoadNumberEntity("rn1")
        val offset = (page - 1) * LIMIT

        return jpaQueryFactory
            .select(
                QSearchAddressVo(
                    rn.postalCode,
                    rn.korFullText,
                    rn.engFullText,
                    rn1.korFullText,
                    rn1.engFullText,
                    rn1.isRepresent,
                    rn.managementNumber,
                    rn1.type
                )
            )
            .from(rn)
            .join(rn1)
            .on(rn.managementNumber.eq(rn1.managementNumber))
            .where(searchFulltext(keyword, KOREAN, rn))
            .offset(offset)
            .limit(LIMIT)
            .fetch()
    }

    fun searchAddressWithEng(
        page: Int,
        keyword: String,
    ): List<SearchAddressVo> {
        val rn = QRoadNumberEntity("rn")
        val rn1 = QRoadNumberEntity("rn1")
        val offset = (page - 1) * LIMIT

        return jpaQueryFactory
            .select(
                QSearchAddressVo(
                    rn.postalCode,
                    rn.korFullText,
                    rn.engFullText,
                    rn1.korFullText,
                    rn1.engFullText,
                    rn1.isRepresent,
                    rn.managementNumber,
                    rn1.type
                )
            )
            .from(rn)
            .join(rn1)
            .on(rn.managementNumber.eq(rn1.managementNumber))
            .where(searchFulltext(keyword, ENGLISH, rn))
            .offset(offset)
            .limit(LIMIT)
            .fetch()
    }

    private fun searchFulltext(keyword: String, type: String, targetEntity: QRoadNumberEntity): BooleanExpression? {
        keyword.ifBlank { return null }

        return when (type) {
            KOREAN -> numberTemplate(
                Double::class.javaObjectType,
                "function('match', {0}, {1})",
                targetEntity.korFullText,
                keyword
            ).gt(0)

            else -> numberTemplate(
                Double::class.javaObjectType,
                "function('match', {0}, {1})",
                targetEntity.engFullText,
                keyword
            ).gt(0)
        }
    }
}
