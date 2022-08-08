package by.gto.equipment.account.ws

import by.gto.equipment.account.model.JSONResponse
import by.gto.equipment.account.model.JSONResponse.Companion.CODE_COMMON_SYSTEM_ERROR
import by.gto.equipment.account.model.JSONResponse.Companion.CODE_OK
import by.gto.equipment.account.model.ReferenceAnswerJSON
import by.gto.equipment.account.service.ServiceImpl
import java.time.LocalDateTime
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@ApplicationScoped
@Path("r")
class ReferencesResource {
    @Inject
    lateinit var service: ServiceImpl

    @GET
    @Path("eqStates")
    @Produces(MediaType.APPLICATION_JSON)
    fun getEquipmentStates() = try {
        val eqs = ReferenceAnswerJSON(LocalDateTime.now(), 0, service.loadEquipmentStates().values.toList())
        JSONResponse(CODE_OK, null, eqs)
    } catch (e: Exception) {
        JSONResponse(CODE_COMMON_SYSTEM_ERROR, e.message)
    }

    @GET
    @Path("eqTypes")
    @Produces(MediaType.APPLICATION_JSON)
    fun getEquipmentTypes() = try {
        val eqs = ReferenceAnswerJSON(LocalDateTime.now(), 0, service.loadEquipmentTypes().values.toList())
        JSONResponse(CODE_OK, null, eqs)
    } catch (e: Exception) {
        JSONResponse(CODE_COMMON_SYSTEM_ERROR, e.message)
    }

    @GET
    @Path("persons")
    @Produces(MediaType.APPLICATION_JSON)
    fun getResponsiblePersons() = try {
        val eqs = ReferenceAnswerJSON(LocalDateTime.now(), 0, service.loadResponsiblePersons().values.toList())
        JSONResponse(CODE_OK, null, eqs)
    } catch (e: Exception) {
        JSONResponse(CODE_COMMON_SYSTEM_ERROR, e.message)
    }
}
