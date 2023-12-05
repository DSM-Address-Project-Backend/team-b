package com.address.dsmproject.job.tasklet

import com.address.dsmproject.domain.roadNumber.RoadNumberEntity
import com.address.dsmproject.domain.roadNumber.RoadNumberRepository
import com.address.dsmproject.domain.roadNumber.type.RoadNumberType
import com.address.dsmproject.job.dto.AddressEngInfo
import com.address.dsmproject.job.dto.AddressInfo
import com.address.dsmproject.job.dto.AddressJibunInfo
import com.address.dsmproject.job.dto.toRoadNumberEntity
import com.address.dsmproject.util.JusoConstants.RoadAddress.ENG_FILE_PATH
import com.address.dsmproject.util.JusoConstants.RoadAddress.KOR_FILE_PATH
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.util.PatternMatchUtils
import java.io.File
import java.nio.charset.Charset

class SaveAddressTasklet(
    private val roadNumberRepository: RoadNumberRepository,
) : Tasklet, StepExecutionListener {
    companion object {
        const val ROAD_ADDRESS_KOR_PATH = "$KOR_FILE_PATH/rnaddrkor*.txt"
        const val JIBUN_KOR_PATH = "$KOR_FILE_PATH}/jibun_rnaddrkor*.txt"
        const val ROAD_ADDRESS_ENG_PATH = "$ENG_FILE_PATH/*.txt"
        const val EUC_KR = "euc_kr"
    }

    private val result: MutableMap<String, AddressInfo> = HashMap()

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        val korAddressFile = File(KOR_FILE_PATH)
        korAddressFile.walk().forEach { file ->
            when {
                PatternMatchUtils.simpleMatch(ROAD_ADDRESS_KOR_PATH, file.path) -> {
                    saveKorAddressInfoFromFile(file.path)
                }
                PatternMatchUtils.simpleMatch(JIBUN_KOR_PATH, file.path) -> {
                    saveKorJibunInfoFromFile(file.path)
                }
            }
        }
        val engAddressFile = File(ENG_FILE_PATH)
        engAddressFile.walk().forEach { file ->
            when {
                PatternMatchUtils.simpleMatch(ROAD_ADDRESS_ENG_PATH, file.path) -> {
                    saveEngAddressInfoFromFile(file.path)
                }
            }
        }

        val roadNumberEntityList = listOf<RoadNumberEntity>()

        result.map { (managementNumber, addressInfo) ->
            val commonKorFullText = addressInfo.common.cityProvinceName + addressInfo.common.countyDistricts + addressInfo.common.eupMyeonDong + addressInfo.common.beobJeongLi
            val commonEngFullText = addressInfo.common.addressEngInfo?.cityProvinceNameEng + addressInfo.common.addressEngInfo?.countyDistrictsEng + addressInfo.common.addressEngInfo?.eupMyeonDongEng + addressInfo.common.addressEngInfo?.beobJeongLiEng
            val addressKorFullText = commonKorFullText + addressInfo.road.mainBuildingNumber + addressInfo.road.subBuildingNumber + addressInfo.road.buildingName
            val addressEngFullText = commonEngFullText + addressInfo.road.mainBuildingNumber + addressInfo.road.subBuildingNumber + addressInfo.road.buildingName
            roadNumberEntityList.plus(
                addressInfo.jibuns.map { jibun ->
                    val jibunKorFullText = commonKorFullText + jibun.mainJibunNumber + jibun.subJibunNumber
                    val jibunEngFullText = commonEngFullText + jibun.mainJibunNumber + jibun.subJibunNumber
                    addressInfo.toRoadNumberEntity(
                        jibun.represents,
                        jibun.mainJibunNumber,
                        jibun.subJibunNumber,
                        RoadNumberType.JIBUN,
                        jibunKorFullText,
                        jibunEngFullText,
                        managementNumber,
                    )
                }.plus(
                    addressInfo.toRoadNumberEntity(
                        false,
                        0,
                        0,
                        RoadNumberType.ROAD_NAME,
                        addressKorFullText,
                        addressEngFullText,
                        managementNumber
                    )
                )
            )
        }

        roadNumberRepository.saveAll(roadNumberEntityList)

        return RepeatStatus.FINISHED
    }

    private fun saveKorAddressInfoFromFile(path: String) {
        File(path).readLines(Charset.forName(EUC_KR)).forEach {
            val split = it.split("|")
            result[split[0]] = AddressInfo.of(split)
        }
    }

    private fun saveKorJibunInfoFromFile(path: String) {
        File(path).readLines(Charset.forName(EUC_KR)).forEach {
            val split = it.split("|")
            result[split[0]]?.jibuns?.add(
                AddressJibunInfo(
                    mainJibunNumber = split[7].toInt(),
                    subJibunNumber = split[8].toInt(),
                    represents = false
                )
            )
        }
    }

    private fun saveEngAddressInfoFromFile(path: String) {
        File(path).readLines(Charset.forName(EUC_KR)).forEach { line ->
            val split = line.split('|')
            result[split[0]]?.common?.addressEngInfo = AddressEngInfo(
                cityProvinceNameEng = split[2],
                countyDistrictsEng = split[3],
                eupMyeonDongEng = split[4],
                beobJeongLiEng = split[5],
                roadNameEng = split[7]
            )
        }
    }
}
