package com.address.dsmproject.job.tasklet

import com.address.dsmproject.domain.roadNumber.repository.RoadNumberRepository
import com.address.dsmproject.job.dto.AddressEngInfo
import com.address.dsmproject.job.dto.AddressInfo
import com.address.dsmproject.job.dto.AddressJibunInfo
import com.address.dsmproject.job.dto.toRoadNumberEntity
import com.address.dsmproject.util.JusoConstants.RoadAddress.ENG_FILE_PATH
import com.address.dsmproject.util.JusoConstants.RoadAddress.KOR_FILE_PATH
import jakarta.persistence.EntityManager
import org.slf4j.LoggerFactory
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
        const val EUC_KR = "euc_kr"
        val REGION_LIST = listOf(
            "busan.txt",
            "chungbuk.txt",
            "chungnam.txt",
            "daegu.txt",
            "daejeon.txt",
            "gangwon.txt",
            "gwangju.txt",
            "gyeongbuk.txt",
            "gyeongnam.txt",
            "gyunggi.txt",
            "incheon.txt",
            "jeju.txt",
            "jeonbuk.txt",
            "jeonnam.txt",
            "sejong.txt",
            "seoul.txt",
            "ulsan.txt",
        )

        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    private val result: MutableMap<String, AddressInfo> = HashMap()

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        val korAddressFile = File(KOR_FILE_PATH)
        val engAddressFile = File(ENG_FILE_PATH)

        roadNumberRepository.truncateTable()
        for (region in REGION_LIST) {
            korAddressFile.walk().forEach {
                if (PatternMatchUtils.simpleMatch("$ROAD_ADDRESS_KOR_PATH$region", it.path)) {
                    saveKorAddressInfoFromFile(it.path)
                }
            }

            korAddressFile.walk().forEach {
                if (PatternMatchUtils.simpleMatch("$JIBUN_KOR_PATH$region", it.path)) {
                    saveKorJibunInfoFromFile(it.path)
                }
            }

            engAddressFile.walk().forEach {
                if (PatternMatchUtils.simpleMatch("$ROAD_ADDRESS_ENG_PATH$region", it.path)) {
                    saveEngAddressInfoFromFile(it.path)
                }
            }

            roadNumberRepository.saveAllAndFlush(
                result.flatMap { (management, addressInfo) -> addressInfo.toRoadNumberEntity(management) }
            )
            result.clear()
            entityManager.clear()

            logger.info("$region Compleated!")
        }

        korAddressFile.deleteRecursively()
        engAddressFile.deleteRecursively()

        return RepeatStatus.FINISHED
    }

    private fun saveKorAddressInfoFromFile(path: String) {
        path.readFile().forEach { line ->
            val split = line.split("|")
            result[split[0]] = AddressInfo.of(split)
        }
    }

    private fun saveKorJibunInfoFromFile(path: String) {
        path.readFile().forEach { line ->
            val split = line.split("|")
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
        path.readFile().forEach { line ->
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

    private fun String.readFile(): List<String> = File(this).readLines(Charset.forName(EUC_KR))
}
