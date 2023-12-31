package com.address.dsmproject.feign

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "JusoClient", url = "\${address.url}")
interface JusoClient {

    @GetMapping("/addrlink/download.do")
    fun downloadJusoFile(
        @RequestParam("reqType") reqType: String,
        @RequestParam("regYmd") yyyy: String,
        @RequestParam("ctprvnCd") ctprvnCd: String,
        @RequestParam("stdde") yyyyMM: String,
        @RequestParam("fileName") fileName: String,
        @RequestParam("intNum") intNum: String,
        @RequestParam("intFileNo") intFileNo: String,
        @RequestParam("realFileName") realFileName: String,
    ): ResponseEntity<ByteArray>
}
