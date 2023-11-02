package com.address.dsmproject.job

import com.address.dsmproject.domain.parcelNumber.ParcelNumberEntity
import com.address.dsmproject.domain.parcelNumber.ParcelNumberRepository
import com.address.dsmproject.domain.roadAddress.RoadAddressEntity
import com.address.dsmproject.domain.roadAddress.RoadAddressRepository
import com.address.dsmproject.domain.roadNumber.RoadNumberEntity
import com.address.dsmproject.domain.roadNumber.RoadNumberRepository
import com.address.dsmproject.job.dto.AddressInfo
import com.address.dsmproject.job.dto.toParcelNumberEntity
import com.address.dsmproject.job.dto.toRoadAddressEntity
import com.address.dsmproject.job.dto.toRoadNumberEntity
import com.address.dsmproject.util.JusoConstants
import com.address.dsmproject.util.JusoConstants.FILE_RESOURCES
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.MultiResourceItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader
import org.springframework.core.io.support.ResourcePatternUtils
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class SaveJobConfiguration(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val parcelNumberRepository: ParcelNumberRepository,
    private val roadAddressRepository: RoadAddressRepository,
    private val roadNumberRepository: RoadNumberRepository,
    private val resourceLoader: ResourceLoader,
) {

    companion object {

    }

    @Bean
    fun saveJob(): Job {
        return JobBuilder("saveJob", jobRepository)
            .start(saveStep())
            .build()
    }

    @Bean
    @JobScope
    fun saveStep(): Step {
        return StepBuilder("saveStep", jobRepository)
            .chunk<AddressInfo, Triple<ParcelNumberEntity, RoadAddressEntity, RoadNumberEntity>>(10, transactionManager)
            .reader(multiResourceItemReader())
            .processor(itemProcessor())
            .writer(entityItemWriter())
            .build()
    }

    @Bean
    @StepScope
    fun multiResourceItemReader(): MultiResourceItemReader<AddressInfo> {
        val resourceItemReader = MultiResourceItemReader<AddressInfo>()
        resourceItemReader.setResources(
            ResourcePatternUtils.getResourcePatternResolver(this.resourceLoader)
                .getResources(FILE_RESOURCES)
        )
        resourceItemReader.setDelegate(multiFileItemReader())
        return resourceItemReader
    }

    @Bean
    fun multiFileItemReader(): FlatFileItemReader<AddressInfo> {
        val flatFileItemReader = FlatFileItemReader<AddressInfo>()
        flatFileItemReader.setLineMapper { line: String?, _: Int ->
            val split = line?.split('|')!!
            return@setLineMapper AddressInfo(
                cityProvinceName = split[2],
                countyDistricts = split[3],
                eupMyeonDong = split[4],
                beobJeongLi = split[5],
                mainAddressNumber = split[7].toInt(),
                subAddressNumber = split[8].toInt(),
                buildingName = split[10],
                mainBuildingNumber = split[12],
                subBuildingNumber = split[13],
                postalCode = split[16].toInt(),
                countyDistrictsEng = "test",
                cityProvinceNameEng = "test",
                beobJeongLiEng = "test",
                eupMyeonDongEng = "test"
            )
        }

        return flatFileItemReader
    }

    @Bean
    fun itemProcessor(): ItemProcessor<AddressInfo, Triple<ParcelNumberEntity, RoadAddressEntity, RoadNumberEntity>> {
        return ItemProcessor<AddressInfo, Triple<ParcelNumberEntity, RoadAddressEntity, RoadNumberEntity>>() {
            val parcelNumber = it.toParcelNumberEntity()
            val roadAddress = it.toRoadAddressEntity()
            val roadNumber = it.toRoadNumberEntity(parcelNumber, roadAddress)
            return@ItemProcessor Triple(parcelNumber, roadAddress, roadNumber)
        }
    }

    @Bean
    fun entityItemWriter(): ItemWriter<Triple<ParcelNumberEntity, RoadAddressEntity, RoadNumberEntity>> {
        return ItemWriter<Triple<ParcelNumberEntity, RoadAddressEntity, RoadNumberEntity>> { it ->
            it.items.forEach {
                parcelNumberRepository.save(it.first)
                roadAddressRepository.save(it.second)
                roadNumberRepository.save(it.third)
            }
        }
    }
}
