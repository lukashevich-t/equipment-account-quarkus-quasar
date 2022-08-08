package by.gto.equipment.account.ws

import by.gto.equipment.account.helpers.toGuidBytes
import by.gto.equipment.account.model.Action
import by.gto.equipment.account.model.EquipmentDescr
import by.gto.equipment.account.model.EquipmentSearchTemplate
import by.gto.equipment.account.model.JSONErrorCodesEnum
import by.gto.equipment.account.model.JSONResponse
import by.gto.equipment.account.model.JSONResponseExt
import by.gto.equipment.account.model.LogEntry
import by.gto.equipment.account.service.ServiceImpl
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.Consumes
import javax.ws.rs.FormParam
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.SecurityContext

@ApplicationScoped
@Path("eq")
class EquipmentResource {
    @Inject
    lateinit var service: ServiceImpl

    @GET
    @Path("getEqDescr")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    fun loadEquipmentDescr(@QueryParam("guid") sGuid: String): JSONResponse {
        return try {
            val eq = service.loadEquipmentDescription(sGuid.toGuidBytes())
                ?: return JSONResponse(JSONErrorCodesEnum.COMMON_USER_ERROR, "не найдено оборудование с таким guid")
            JSONResponse(JSONErrorCodesEnum.OK, null, eq)
        } catch (ex: Exception) {
            log.error(ex.message, ex)
            JSONResponse(JSONErrorCodesEnum.COMMON_USER_ERROR, "неверный формат guid")
        }
    }

    @GET
    @Path("getEqLog/{guid}")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    fun getEquipmentLog(@PathParam("guid") sGuid: String): JSONResponse =
        try {
            UUID.fromString(sGuid)
            JSONResponse(JSONErrorCodesEnum.OK, null, service.getLog(sGuid.toGuidBytes()))
        } catch (ex: Exception) {
            log.error(ex.message, ex)
            JSONResponse(JSONErrorCodesEnum.COMMON_USER_ERROR, "неверный формат guid")
        }

    @POST
    @Path("putEqDescr")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    fun putEquipmentDescr(
        @Context rq: HttpServletRequest,
        @Context sc: SecurityContext,
        eqdescr: EquipmentDescr?
    ): JSONResponse {
        if (eqdescr == null) {
            return JSONResponse(JSONErrorCodesEnum.COMMON_USER_ERROR, "Отсутствует входной параметр")
        }
        val userId = service.getCachedUserInfo(rq.getSession(), sc.getUserPrincipal().getName()).id
        try {
            val (refsModified, newObj) = service.putEquipmentDescription(eqdescr, userId, true)
            // если были изменены справочники, то строка ответа начинается с пробела:
            return JSONResponse(
                JSONErrorCodesEnum.OK,
                (if (refsModified as Boolean) " " else "") + "modified OK (guid ${eqdescr.guid})",
                newObj
            )
        } catch (ex: Exception) {
            log.error(ex.message, ex)
            return JSONResponse(JSONErrorCodesEnum.COMMON_SYSTEM_ERROR, ex.message)
        }
    }

    @POST
    @Path("updateEqDescr")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    //    @Consumes(MediaType.APPLICATION_FORM_URLENCODED + ";charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    fun updateEquipmentDescr(
        @Context rq: HttpServletRequest,
        @Context sc: SecurityContext,
        eqdescr: EquipmentDescr?
    ): JSONResponse {
        if (eqdescr == null) {
            return JSONResponse(JSONErrorCodesEnum.COMMON_USER_ERROR, "Отсутствует входной параметр")
        }

        return try {
            // TODO: in production userId is mandatory
            val userId =
                if (sc.userPrincipal == null) -1 else service.getCachedUserInfo(rq.session, sc.userPrincipal.name).id
            val (refsModified, newObj) = service.putEquipmentDescription(eqdescr, userId, false)
            // если были изменены справочники, то строка ответа начинается с пробела:
            JSONResponseExt(
                JSONErrorCodesEnum.OK,
                (if (refsModified as Boolean) " " else "") + "modified OK (guid ${eqdescr.guid})",
                newObj, refsModified
            )
        } catch (ex: Exception) {
            log.error(ex.message, ex)
            JSONResponse(JSONErrorCodesEnum.COMMON_SYSTEM_ERROR, ex.message)
        }
    }

    @GET
    @Path("testdate")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    fun getTestDate() =
        JSONResponse(JSONErrorCodesEnum.OK, null, object {
            val d = Date()
            val ld = LocalDate.now()
            val ldt = LocalDateTime.now()
            val ms = System.currentTimeMillis()
        })

    @GET
    @Path("testlog")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    fun getTestLog() = JSONResponse(JSONErrorCodesEnum.OK, null, service.getLog(existingTestEqipment))

    @GET
    @Path("testsearch")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    fun getTestSearch() = JSONResponse(
        JSONErrorCodesEnum.OK,
        "search",
        service.searchEquipment(EquipmentSearchTemplate().apply { invNumber = "480699" })
    )

    @GET
    @Path("testeq")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    fun getTestEquipment(): JSONResponse {

        val eq = service.loadEquipmentDescription(existingTestEqipment)
            ?: return JSONResponse(JSONErrorCodesEnum.COMMON_USER_ERROR, "не найдено оборудование с таким guid")
        return JSONResponse(JSONErrorCodesEnum.OK, null, eq)
    }

    companion object {
        private val log = LoggerFactory.getLogger(EquipmentResource::class.java)
        private val existingTestEqipment = "f6a20400-393b-4ff0-98b4-67690884310d".toGuidBytes()
    }

    /**
     * генерация UUID
     */
    @GET
    @Path("guid")
    fun genGuid() = JSONResponse(JSONErrorCodesEnum.OK, content = UUID.randomUUID().toString())

    @POST
    @Path("print_invs2")
    @Produces("application/pdf")
    fun printInventoryNumbers2(@FormParam("ids_for_printing") ids_for_printing: String): Response {
        val sGuids: List<String> = ids_for_printing.split(',').dropLastWhile { it.isEmpty() }

        val baos = ByteArrayOutputStream()
        return try {
            val numbers = service.loadInvNumbersFromGUIDs(sGuids)
            service.writeNumbersToPdfStream(numbers, baos, "a4_6x6.jasper")
            baos.flush()
            baos.close()
            Response.ok(baos.toByteArray())
                .header("Content-type", "application/pdf")
//                    .header("Content-Disposition", "attachment; filename=inv-numbers.pdf")
                .header("Content-Transfer-Encoding", "binary")
                .build()
        } catch (ex: Exception) {
            ex.printStackTrace()
//            StretchTypeEnum
            Response.serverError().entity(ex.message).status(Response.Status.INTERNAL_SERVER_ERROR).build()
        } finally {
            baos.close()
        }
    }

    @GET
    @Path("gen_inv")
    @Produces(MediaType.WILDCARD + ";charset=UTF-8")
    fun getInventoryNumbers2(
        @QueryParam("date") sDate: String?,
        @QueryParam("range") sRange: String,
        @QueryParam("templateFilename") templateFilename: String?
    ): Response {
        val baos = ByteArrayOutputStream()

        return try {
            val date = if (sDate.isNullOrBlank()) null else LocalDate.parse(sDate)
            val numbers = service.generateInvNumbers(date, sRange)
            service.writeNumbersToPdfStream(numbers, baos, templateFilename ?: "a4_6x6.jasper")
            baos.flush()
            baos.close()
            Response.ok(baos.toByteArray())
                .header("Content-type", "application/pdf")
                .build()
        } catch (ex: Exception) {
            ex.printStackTrace()
            Response.serverError().entity(ex.message).status(Response.Status.INTERNAL_SERVER_ERROR).build()
        } finally {
            baos.close()
        }
    }

    @POST
    @Path("searchEq")
    @Produces(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    @Consumes(MediaType.APPLICATION_JSON + ";charset=UTF-8")
    fun searchEquipment(eq: EquipmentSearchTemplate): JSONResponse {
        try {
            return JSONResponse(JSONErrorCodesEnum.OK, "search", service.searchEquipment(eq))
        } catch (e: Exception) {
            return JSONResponse(JSONErrorCodesEnum.COMMON_USER_ERROR, e.message, null)
        }

    }

    @POST
    @Path("addLog")
    fun addLog(@Context rq: HttpServletRequest, @Context sc: SecurityContext, logEntry: LogEntry) = try {
        // TODO: in production userId is mandatory
        val userId =
            if (sc.userPrincipal == null) -1 else service.getCachedUserInfo(rq.session, sc.userPrincipal.name).id
        val result = service.logEquipmentChange(
            logEntry.guid,
            Action.fromId(logEntry.actionId),
            userId,
            logEntry.comment ?: "",
            LocalDateTime.now()
        )
        JSONResponse(JSONErrorCodesEnum.OK, result.toString(), result)
    } catch (e: Exception) {
        JSONResponse(JSONErrorCodesEnum.COMMON_USER_ERROR, e.message, null)
    }

    @PostConstruct
    fun postConstruct() {
        println("postConstruct (print)")
        log.error("postConstruct (logger)")
    }
}
