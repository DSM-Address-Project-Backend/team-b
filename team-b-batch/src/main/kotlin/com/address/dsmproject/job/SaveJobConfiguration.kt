package com.address.dsmproject.job

import com.address.dsmproject.domain.parcelNumber.ParcelNumberRepository
import com.address.dsmproject.domain.roadAddress.RoadAddressRepository
import com.address.dsmproject.domain.roadNumber.RoadNumberRepository
import com.address.dsmproject.job.dto.*
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
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class SaveJobConfiguration(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val parcelNumberRepository: ParcelNumberRepository,
    private val roadAddressRepository: RoadAddressRepository,
    private val roadNumberRepository: RoadNumberRepository,
    @Value("classpath:/unzipKor/rnaddrkor*.txt")
    private val resources: Array<Resource>,
) {

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
            .chunk<AddressInfo, AddressInfoVo>(20, transactionManager)
            .reader(multiResourceItemReader())
            .processor(itemProcessor())
            .writer(entityItemWriter())
            .build()
    }

    @Bean
    @StepScope
    fun multiResourceItemReader() = MultiResourceItemReader<AddressInfo>().apply {
        setResources(resources)
        setDelegate(multiFileItemReader())
    }

    @Bean
    @StepScope
    fun multiFileItemReader() = FlatFileItemReader<AddressInfo>().apply {
        setEncoding("euc-kr")
        setLineMapper { line: String, _: Int ->
            val split = line.split('|')
            AddressInfo(
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
                countyDistrictsEng = "test",
                cityProvinceNameEng = "test",
                beobJeongLiEng = "test",
                eupMyeonDongEng = "test",
                streetNumber = split[10],
            )
        }
    }

    @Bean
    @StepScope
    fun itemProcessor(): ItemProcessor<AddressInfo, AddressInfoVo> {
        return ItemProcessor<AddressInfo, AddressInfoVo>() {
            val parcelNumber = it.toParcelNumberEntity()
            val roadAddress = it.toRoadAddressEntity()
            val roadNumber = it.toRoadNumberEntity(parcelNumber, roadAddress)
            return@ItemProcessor AddressInfoVo(parcelNumber, roadAddress, roadNumber)
        }
    }

    @Bean
    @StepScope
    fun entityItemWriter(): ItemWriter<AddressInfoVo> {
        return ItemWriter<AddressInfoVo> { processor ->
            processor.items.map {
                parcelNumberRepository.save(it.parcelNumberEntity)
                roadAddressRepository.save(it.roadAddressEntity)
                roadNumberRepository.save(it.roadNumberEntity)
            }
        }
    }
}
