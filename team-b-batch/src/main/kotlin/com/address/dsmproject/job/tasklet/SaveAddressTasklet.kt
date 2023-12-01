package com.address.dsmproject.job.tasklet

import com.address.dsmproject.domain.parcelNumber.ParcelNumberEntity
import com.address.dsmproject.domain.parcelNumber.ParcelNumberRepository
import com.address.dsmproject.domain.roadAddress.RoadAddressEntity
import com.address.dsmproject.domain.roadAddress.RoadAddressRepository
import com.address.dsmproject.domain.roadNumber.RoadNumberEntity
import com.address.dsmproject.domain.roadNumber.RoadNumberRepository
import com.address.dsmproject.job.dto.*
import com.address.dsmproject.util.JusoConstants.ENG_ADDRESS_FILE_PATH
import com.address.dsmproject.util.JusoConstants.KOR_ADDRESS_FILE_PATH
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
    private val result: MutableMap<AddressInfoId, AddressInfo> = HashMap()

    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
        File(KOR_ADDRESS_FILE_PATH).walk().forEach {
            if (PatternMatchUtils.simpleMatch("${KOR_ADDRESS_FILE_PATH}/rnaddrkor*.txt", it.path))
                saveAddressInfoFromFile(it.path)
        }

        File(ENG_ADDRESS_FILE_PATH).walk().forEach {
            if (PatternMatchUtils.simpleMatch("${ENG_ADDRESS_FILE_PATH}/*.txt", it.path)) {
                File(it.path).readLines(Charset.forName("euc-kr")).forEach { line ->
                    val split = line.split('|')
                    val addressInfo = result[AddressInfoId(split[0], split[8])]
                    addressInfo?.setEngInfo(
                        cityProvinceNameEng = split[1],
                        countyDistrictsEng = split[2],
                        eupMyeonDongEng = split[3],
                        beobJeongLiEng = split[4],
                        represents = split[17].toBoolean()
                    )
                }
            }
        }

        saveAddressInfoEntity()
        return RepeatStatus.FINISHED
    }

    private fun saveAddressInfoFromFile(path: String) {
        return File(path).readLines(Charset.forName("euc-kr")).forEach {
            val split = it.split("|")
            result[AddressInfoId(split[1], split[9])] = AddressInfo(
                cityProvinceName = split[2],
                countyDistricts = split[3],
                eupMyeonDong = split[4],
                beobJeongLi = split[5],
                mainAddressNumber = split[7].toInt(),
                subAddressNumber = split[8].toInt(),
                buildingName = split[22],
                mainBuildingNumber = split[12],
                subBuildingNumber = split[13],
                postalCode = split[16].toInt(),
                countyDistrictsEng = "",
                cityProvinceNameEng = "",
                beobJeongLiEng = "",
                eupMyeonDongEng = "",
                streetNumber = split[10],
                represents = false
            )
        }
    }

    private fun saveAddressInfoEntity() {
        val parcelNumbers: MutableList<ParcelNumberEntity> = ArrayList()
        val roadAddresses: MutableList<RoadAddressEntity> = ArrayList()
        val roadNumbers: MutableList<RoadNumberEntity> = ArrayList()
        result.forEach {
            val addressInfo = it.value
            val parcelNumber = addressInfo.toParcelNumberEntity()
            val roadAddress = addressInfo.toRoadAddressEntity()
            val roadNumber = addressInfo.toRoadNumberEntity(parcelNumber, roadAddress)

            parcelNumbers.add(parcelNumber)
            roadAddresses.add(roadAddress)
            roadNumbers.add(roadNumber)
        }

        parcelNumberRepository.saveAll(parcelNumbers)
        roadAddressRepository.saveAll(roadAddresses)
        roadNumberRepository.saveAll(roadNumbers)
    }
}