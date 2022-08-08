package by.gto.equipment.account.ws

import by.gto.equipment.account.helpers.toGuidBytes
import by.gto.equipment.account.mappers.GlobalMapper
import by.gto.equipment.account.model.Action
import by.gto.equipment.account.model.BaseReference
import by.gto.equipment.account.model.Equipment
import java.time.LocalDateTime
import java.util.UUID
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

private val TEST_GUID = "ea0fc72c-070f-4820-9d59-643f70fe0572".toGuidBytes()

@ApplicationScoped
@Path("t")
class TestResource {
    @Inject
    lateinit var mapper: GlobalMapper

    @POST
    @Path("logEquipmentChange")
    @Produces(MediaType.APPLICATION_JSON)
    fun logEquipmentChange(): Any {
        return mapper.logEquipmentChange(
                TEST_GUID, Action.EQUIPMENT_CREATE.id,
                1, "Create", LocalDateTime.now()
        )
    }

    @POST
    @Path("createReferences")
    @Produces(MediaType.APPLICATION_JSON)
    fun createReferences(): Any {
        val state = BaseReference(name = "state " + Math.random().toString())
        mapper.createReference("equipment_state", state)
        val type = BaseReference(name = "type " + Math.random().toString())
        mapper.createReference("equipment_type", type)
        val person = BaseReference(name = "person " + Math.random().toString())
        mapper.createReference("responsible_person", person)
        return state.toString() + "\n" + type + "\n" + person
    }

    @POST
    @Path("saveEquipment")
    @Produces(MediaType.APPLICATION_JSON)
    fun saveEquipment(): Any {
        val create = Math.random() >= 0.5
        val result = StringBuilder()
        val guid = if (create) {
            result.append("new\n")
            UUID.randomUUID().toString().toGuidBytes()
        } else {
            result.append("modified\n")
            TEST_GUID
        }
        val eq = Equipment(guid, 1, 1, 1, "Comment " + Math.random(),
                Math.random().toString(), null, "serial" + Math.random())
        result.append(mapper.saveEquipment(eq)).append("\n").append(eq)
        return result.toString()
    }

    @GET
    @Path("loadEquipmentsByGuidList")
    @Produces(MediaType.APPLICATION_JSON)
    fun loadEquipmentsByGuidList(): Any {
        return mapper.loadEquipmentsByGuidList(listOf(
                TEST_GUID,
                "1555973d-edb9-444c-a732-8a4944906c1a".toGuidBytes(),
                "53d91886-df03-4b2f-8f13-e4c5b3a5c865".toGuidBytes()
        ))

    }

    @GET
    @Path("loadEquipmentDescr")
    @Produces(MediaType.APPLICATION_JSON)
    fun loadEquipmentDescr() = mapper.loadEquipmentDescr(TEST_GUID)

    @GET
    @Path("getRefIdByName/{ref}/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getRefIdByName(@PathParam("ref") ref: String, @PathParam("name") name: String) = mapper.getRefIdByName(ref, name)

    @GET
    @Path("loadUserInfoByLogin/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getRefIdByName(@PathParam("login") login: String) = mapper.loadUserInfoByLogin(login)

    @GET
    @Path("loadUserInfoByDn/{dn}")
    @Produces(MediaType.APPLICATION_JSON)
    fun loadUserInfoByDn(@PathParam("dn") dn: String): Any? {
        val a = mapper.loadUserInfoByDn(dn)
        return a
    }

    @GET
    @Path("getLog/{guid}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getLog(@PathParam("guid") guid: String): Any? {
        val a = mapper.getLog(guid.toGuidBytes())
        return a
    }

}
