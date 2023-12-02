package com.address.dsmproject.job.tasklet

import com.address.dsmproject.domain.parcelNumber.ParcelNumberRepository
import com.address.dsmproject.domain.roadAddress.RoadAddressRepository
import com.address.dsmproject.domain.roadNumber.RoadNumberRepository
import com.address.dsmproject.job.dto.AddressInfo
import com.address.dsmproject.job.dto.AddressJibunInfo
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
    private val parcelNumberRepository: ParcelNumberRepository,
    private val roadAddressRepository: RoadAddressRepository,
    private val roadNumberRepository: RoadNumberRepository
) : Tasklet, StepExecutionListener {
    private val result: MutableMap<String, AddressInfo> = HashMap()

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        val korAddressFile = File(KOR_FILE_PATH)
        korAddressFile.walk().forEach {
            if (PatternMatchUtils.simpleMatch(
                    "${KOR_FILE_PATH}/rnaddrkor*.txt",
                    it.path
                )
            ) saveKorAddressInfoFromFile(it.path)
        }

        korAddressFile.walk().forEach {
            if (PatternMatchUtils.simpleMatch(
                    "${KOR_FILE_PATH}/jibun_rnaddrkor*.txt",
                    it.path
                )
            ) saveKorJibunInfoFromFile(it.path)
        }

        val engAddressFile = File(ENG_FILE_PATH)
        engAddressFile.walk().forEach {
            if (PatternMatchUtils.simpleMatch("${ENG_FILE_PATH}/*.txt", it.path)) {
                saveEngAddressInfoFromFile(it.path)
            }
        }

        // TODO: save 추가

        return RepeatStatus.FINISHED
    }

    private fun saveKorAddressInfoFromFile(path: String) {
        return File(path).readLines(Charset.forName("euc-kr")).forEach {
            val split = it.split("|")
            result[split[0]] = AddressInfo.build(split)
        }
    }

    private fun saveKorJibunInfoFromFile(path: String) {
        return File(path).readLines(Charset.forName("euc-kr")).forEach {
            val split = it.split("|")
            result[split[0]]?.jibuns?.add(
                AddressJibunInfo(
                    mainJibunNumber = split[7].toInt(), subJibunNumber = split[8].toInt(), represents = false
                )
            )
        }
    }

    private fun saveEngAddressInfoFromFile(path: String) {
        File(path).readLines(Charset.forName("euc-kr")).forEach { line ->
            val split = line.split('|')
            result[split[0]]?.common?.updateEngInfo(
                cityProvinceNameEng = split[2],
                countyDistrictsEng = split[3],
                eupMyeonDongEng = split[4],
                beobJeongLiEng = split[5],
                roadNameEng = split[7]
            )
        }
    }
}