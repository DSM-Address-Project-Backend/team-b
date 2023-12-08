package com.address.dsmproject.job.tasklet

import com.address.dsmproject.domain.roadNumber.RoadNumberRepository
import com.address.dsmproject.job.dto.AddressEngInfo
import com.address.dsmproject.job.dto.AddressInfo
import com.address.dsmproject.job.dto.AddressJibunInfo
import com.address.dsmproject.job.dto.toRoadNumberEntity
import com.address.dsmproject.util.JusoConstants.RoadAddress.ENG_FILE_PATH
import com.address.dsmproject.util.JusoConstants.RoadAddress.KOR_FILE_PATH
import jakarta.persistence.EntityManager
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
    private val entityManager: EntityManager,
) : Tasklet, StepExecutionListener {
    companion object {
        const val ROAD_ADDRESS_KOR_PATH = "$KOR_FILE_PATH/rnaddrkor_"
        const val JIBUN_KOR_PATH = "$KOR_FILE_PATH/jibun_rnaddrkor_"
        const val ROAD_ADDRESS_ENG_PATH = "$ENG_FILE_PATH/rneng_"
        const val TXT = ".txt"
        const val EUC_KR = "euc_kr"
        val REGION_LIST = listOf(
            "busan",
            "chungbuk",
            "chungnam",
            "daegu",
            "daejeon",
            "gangwon",
            "gwangju",
            "gyeongbuk",
            "gyeongnam",
            "gyunggi",
            "incheon",
            "jeju",
            "jeonbuk",
            "jeonnam",
            "sejong",
            "seoul",
            "ulsan",
        )
    }

    private val result: MutableMap<String, AddressInfo> = HashMap()

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        for (region in REGION_LIST) {
            val korAddressFile = File(KOR_FILE_PATH)
            korAddressFile.walk().forEach {
                if (PatternMatchUtils.simpleMatch("$ROAD_ADDRESS_KOR_PATH$region$TXT", it.path)) {
                    saveKorAddressInfoFromFile(it.path)
                }
            }

            korAddressFile.walk().forEach {
                if (PatternMatchUtils.simpleMatch("$JIBUN_KOR_PATH$region$TXT", it.path)) {
                    saveKorJibunInfoFromFile(it.path)
                }
            }

            val engAddressFile = File(ENG_FILE_PATH)
            engAddressFile.walk().forEach {
                if (PatternMatchUtils.simpleMatch("$ROAD_ADDRESS_ENG_PATH$region$TXT", it.path)) {
                    saveEngAddressInfoFromFile(it.path)
                }
            }

            roadNumberRepository.saveAllAndFlush(
                result.flatMap { (management, addressInfo) -> addressInfo.toRoadNumberEntity(management) }
            )
            entityManager.clear()
        }

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
