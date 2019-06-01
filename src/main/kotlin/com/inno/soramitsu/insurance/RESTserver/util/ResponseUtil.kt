package com.inno.soramitsu.insurance.RESTserver.util

import com.inno.soramitsu.insurance.RESTserver.controller.AgentController
import com.inno.soramitsu.insurance.RESTserver.controller.ClientController
import com.inno.soramitsu.insurance.RESTserver.model.RequestTO
import com.inno.soramitsu.insurance.RESTserver.model.envelope.EnvelopedResponse
import com.inno.soramitsu.insurance.RESTserver.model.envelope.MetaData
import org.springframework.data.domain.Page
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn

/**
 * Created by nethmih on 14.04.19.
 */

object ResponseUtil {


    fun <T> generateResponse(data: T): EnvelopedResponse<Any> {

        val response: EnvelopedResponse<Any> = EnvelopedResponse()

        if (data is Page<*>) {
            response.data = (data as Page<*>).content
        } else {
            response.data = data
        }

        response.meta = generateMetaData(data)

        return response
    }

    private fun <T> generateMetaData(data: T): MetaData {
        val metaData = MetaData()

        if (data is Page<*>) {
            metaData.currentPage = (data as Page<*>).number
            metaData.itemsPerPage = (data as Page<*>).size
            metaData.totalPages = (data as Page<*>).totalPages
            metaData.totalItems = (data as Page<*>).totalElements
        }
        return metaData
    }

    fun generateGetAllInsuranceRequestsLinks(requestTO: RequestTO, response: EnvelopedResponse<*>) {
        val companyId = requestTO.companyId
        val status = requestTO.status
        val currPage = response.meta?.currentPage
        val totalPages = response.meta?.totalPages
        val size = response.meta?.itemsPerPage


        if (currPage != null && currPage > 0) {
            response.add(linkTo(methodOn(AgentController::class.java).getInsuranceRequests(companyId, status, currPage -1,
                    size!!)).withRel("previous"))
        }
        response.add(linkTo(methodOn(AgentController::class.java).getInsuranceRequests(companyId, status, currPage!!,
                size!!)).withSelfRel())
        if (currPage != null && currPage < totalPages!! - 1) {
            response.add(linkTo(methodOn(AgentController::class.java).getInsuranceRequests(companyId, status, currPage + 1,
                    size!!)).withRel("next"))
        }

    }

    fun generateGetAllInsuranceRequestsLinksForClient(requestTO: RequestTO, response: EnvelopedResponse<*>) {
        val email = requestTO.email
        val currPage = response.meta?.currentPage
        val totalPages = response.meta?.totalPages
        val size = response.meta?.itemsPerPage


        if (currPage != null && currPage > 0) {
            response.add(linkTo(methodOn(ClientController::class.java).getInsuranceRequestForClient(email, currPage -1,
                    size!!)).withRel("previous"))
        }
        response.add(linkTo(methodOn(ClientController::class.java).getInsuranceRequestForClient(email, currPage!!,
                size!!)).withSelfRel())
        if (currPage < totalPages!! - 1) {
            response.add(linkTo(methodOn(ClientController::class.java).getInsuranceRequestForClient(email, currPage + 1,
                    size)).withRel("next"))
        }

    }



}