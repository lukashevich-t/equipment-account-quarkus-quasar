package by.gto.equipment.account.dao

//import by.gto.equipment.account.e.RecordNotFoundException
//import by.gto.equipment.account.helpers.toGuidBytes
//import by.gto.equipment.account.helpers.toBytes
//import by.gto.equipment.account.helpers.toGuid
//import by.gto.equipment.account.helpers.toDate
//import by.gto.equipment.account.helpers.toLocalDate
//import by.gto.equipment.account.model.Action
////import by.gto.equipment.account.model.DS_JNDI_NAME
//import by.gto.equipment.account.model.Equipment
//import by.gto.equipment.account.model.EquipmentDescr
//import by.gto.equipment.account.model.LogEntryPresentation
//import by.gto.equipment.account.model.UserInfo
////import org.apache.commons.dbutils.QueryRunner
//import java.sql.ResultSet
//import java.sql.SQLException
//import java.time.LocalDateTime
//import java.util.Date
//import java.util.UUID
//import javax.annotation.Resource
import javax.enterprise.context.ApplicationScoped
//import javax.sql.DataSource

@ApplicationScoped
open class DaoJdbcJdbcUtils {
//    @Resource(mappedName = DS_JNDI_NAME)
//    private val ds: DataSource? = null

//    @Throws(SQLException::class)
//    open fun getEquipmentStates(): Map<Int, BaseReference> {
//        val run = QueryRunner(ds)
//        val r =
//            run.query<Map<Int, BaseReference>>("SELECT es.id, es.name FROM equipment_state es order by es.name") { rs ->
//                val res = LinkedHashMap<Int, BaseReference>()
//                while (rs.next()) {
//                    val id = rs.getInt(1)
//                    res[id] = BaseReference(id, rs.getString(2))
//                }
//                res
//            }
//        return r
//    }
//
//    @Throws(SQLException::class)
//    open fun getEquipmentTypes(): Map<Int, BaseReference> {
//        val run = QueryRunner(ds)
//        val r =
//            run.query<Map<Int, BaseReference>>("SELECT et.id, et.name FROM equipment_type et order by et.name") { rs ->
//                val res = LinkedHashMap<Int, BaseReference>()
//                while (rs.next()) {
//                    val id = rs.getInt(1)
//                    res[id] = BaseReference(id, rs.getString(2))
//                }
//                res
//            }
//        return r
//
//    }
//
//    @Throws(SQLException::class)
//    open fun getResponsiblePersons(): Map<Int, BaseReference> {
//        val run = QueryRunner(ds)
//        val r =
//            run.query<Map<Int, BaseReference>>("SELECT rp.id, rp.name FROM responsible_person rp order by rp.name") { rs ->
//                val res = LinkedHashMap<Int, BaseReference>()
//                while (rs.next()) {
//                    val id = rs.getInt(1)
//                    res[id] = BaseReference(id, rs.getString(2))
//                }
//                res
//            }
//        return r
//    }
//
////    val models: ReferenceAnswerJSON
////        @Throws(SQLException::class)
////        get() {
////            val run = QueryRunner(ds)
////            val r = run.query<List<BaseReference>>("SELECT t.id, concat(t.name_rus, ' / ', t.name_eng) name FROM gai_model t") { rs ->
////                val res = ArrayList<BaseReference>()
////                while (rs.next()) {
////                    res.add(BaseReference(rs.getInt(1), rs.getString(2)))
////                }
////                res
////            }
////            return ReferenceAnswerJSON(Date(), 0, r)
////        }
//
////    fun getEquipment(guid: UUID): Equipment? {
////        val run = QueryRunner(ds)
////        try {
////            return run.query("SELECT e.type_id, e.person_id, e.state_id, e.comment, e.inv_number, e.purchase_date, e.serial FROM equipment e WHERE  e.id = ? LIMIT 1",
////                    ResultSetHandler<Equipment> { rs ->
////                        if (rs.next()) {
////                            Equipment(guid.toString(), rs.getShort("type_id"),
////                                    rs.getInt("person_id"), rs.getInt("state_id"),
////                                    rs.getString("comment"), rs.getString("inv_number"),
////                                    rs.getDate("purchase_date"),
////                                    rs.getString("serial"))
////                        } else {
////                            null
////                        }
////                    }, GuidHelpers.guidAsBytes(guid))
////        } catch (e: SQLException) {
////            return null
////        }
////    }
//
//    open fun getEquipmentDescr(guid: UUID): EquipmentDescr? {
//        val query = """SELECT e.id, e.type_id, e.person_id, e.state_id, e.comment, e.inv_number, e.purchase_date,
//                e.serial, es.name AS state, et.name AS eq_type, rp.name AS person FROM equipment e
//                  LEFT JOIN responsible_person rp ON e.person_id = rp.id
//                  LEFT JOIN equipment_state es ON e.state_id = es.id
//                  LEFT JOIN equipment_type et ON e.type_id = et.id
//                WHERE (e.id=?)"""
//        val run = QueryRunner(ds)
//        try {
//            return run.query(
//                query,
//                { rs ->
//                    if (rs.next()) {
//                        equipmentDescrFromResultSet(rs)
//                    } else {
//                        null
//                    }
//                }, guid.toBytes()
//            )
//        } catch (e: SQLException) {
//            return null
//        }
//    }
//
//    @Throws(SQLException::class)
//    open fun searchEquipment(template: Equipment): MutableList<EquipmentDescr> {
//        val run = QueryRunner(ds)
//        val query = "SELECT e.id, e.type_id, e.person_id, e.state_id, e.comment, e.inv_number, e.purchase_date, " +
//                "e.serial, es.name AS state, et.name AS eq_type, rp.name AS person FROM equipment e\n" +
//                "  LEFT JOIN responsible_person rp ON e.person_id = rp.id\n" +
//                "  LEFT JOIN equipment_state es ON e.state_id = es.id\n" +
//                "  LEFT JOIN equipment_type et ON e.type_id = et.id \n" +
//                "where "
//        var cond = ""
//        val params = ArrayList<Any>()
//        try {
//            val guid = UUID.fromString(template.guid)
//            cond = "$cond (e.id=?) and"
//            params.add(guid.toBytes())
//        } catch (ignored: Exception) {
//        }
//
//        if (template.personId > 0) {
//            cond = "$cond (e.person_id=?) and"
//            params.add(template.personId)
//        }
//
//        if (template.typeId > 0) {
//            cond = "$cond (e.type_id=?) and"
//            params.add(template.typeId)
//        }
//
//        if (template.stateId > 0) {
//            cond = "$cond (e.state_id=?) and"
//            params.add(template.stateId)
//        }
//
//        if (!template.comment.isNullOrEmpty()) {
//            cond = "$cond (e.comment like ?) and"
//            params.add("%" + template.comment?.trim() + "%")
//        }
//
//        val purchaseDate = template.purchaseDate
//        if (purchaseDate != null) {
//            cond = "$cond (e.purchase_date = ?) and"
//            params.add(purchaseDate.toDate()!!)
//        }
//
//        if (!template.serial.isNullOrEmpty()) {
//            cond = "$cond (e.serial like ?) and"
//            params.add("%" + template.serial?.trim() + "%")
//        }
//
//        if (template.invNumber.isNotEmpty()) {
//            cond = "$cond (e.inv_number like ?) and"
//            params.add("%" + template.invNumber.trim() + "%")
//        }
//        if ("" == cond) {
//            return ArrayList()
//        }
//        cond = cond.substring(0, cond.length - 4)
//        return run.query<MutableList<EquipmentDescr>>("$query $cond", { rs ->
//            val res = ArrayList<EquipmentDescr>()
//            while (rs.next()) {
//                res.add(equipmentDescrFromResultSet(rs))
//            }
//            res
//        }, *params.toTypedArray())
//    }
//
//    @Throws(SQLException::class)
//    private fun equipmentDescrFromResultSet(rs: ResultSet): EquipmentDescr {
//        val r = EquipmentDescr()
//        r.guid = rs.getBytes("id").toGuid().toString()
//        r.comment = rs.getString("comment")
//        r.invNumber = rs.getString("inv_number")
//
//        r.purchaseDate = (rs.getDate("purchase_date") as Date?).toLocalDate()
//        r.state = rs.getString("state")
//        r.type = rs.getString("eq_type")
//        r.person = rs.getString("person")
//        r.typeId = rs.getInt("type_id")
//        r.stateId = rs.getInt("state_id")
//        r.personId = rs.getInt("person_id")
//        r.serial = rs.getString("serial")
//        return r
//    }
//
//    @Throws(SQLException::class)
//    open fun getIdByName(tableName: String, name: String): Int? {
//        val run = QueryRunner(ds)
//        return run.query(String.format(qGetIdOfName, tableName), { rs ->
//            if (rs.next()) rs.getInt(1) else null
//        }, name)
//    }

//    @Throws(Exception::class)
//    open fun putEquipment(eq: Equipment): EquipmentDescr {
//        val query = """
//INSERT INTO equipment(  id,   type_id,  person_id,  state_id,  comment,  inv_number,  purchase_date, serial)
//  VALUES (?,   ?,  ?,  ?,  ?,  ?, ?, ?)
//ON DUPLICATE KEY UPDATE
//type_id = VALUES(equipment.type_id),
//person_id = VALUES(equipment.person_id),
//state_id = VALUES(equipment.state_id),
//comment = VALUES(equipment.comment),
//serial = VALUES(equipment.serial)"""
//        val guid = UUID.fromString(eq.guid)
//        val params = arrayOfNulls<Any>(8)
//        params[0] = guid.toBytes()
//        params[1] = eq.typeId
//        params[2] = eq.personId
//        params[3] = eq.stateId
//        params[4] = eq.comment
//        params[5] = eq.invNumber
//        params[6] = eq.purchaseDate.toDate()
//        params[7] = eq.serial
//
//        val run = QueryRunner(ds)
//        val cnt = run.update(query, *params)
//        if (cnt == 0) {
//            throw Exception("Ошибка вставки/обновления")
//        }
//
//        val t = EquipmentDescr(eq)
//        t.guid = eq.guid
//        return searchEquipment(t)[0]
//    }

//
//    @Throws(SQLException::class)
//    open fun getEquipmentsByGuidList(sGuids: List<String?>?): List<Equipment> {
//        if (sGuids == null) {
//            return ArrayList()
//        }
//        val run = QueryRunner(ds)
//
//        val query =
//            "SELECT e.id, e.type_id, e.person_id, e.state_id, e.comment, e.inv_number, e.purchase_date, e.serial FROM equipment e WHERE "
//        val cond = StringBuilder()
//        val bGuids = ArrayList<ByteArray>()
//        for (sGuid in sGuids) {
//            try {
//                bGuids.add(UUID.fromString(sGuid).toBytes())
//                cond.append(" e.id = ? or")
//            } catch (ignored: Exception) {
//            }
//
//        }
//        if (bGuids.size == 0) {
//            return ArrayList()
//        }
//
//        cond.setLength(cond.length - 3)
//        val result = run.query(query + cond.toString(), { rs ->
//            val res = ArrayList<Equipment>()
//            while (rs.next()) {
//
//                res.add(
//                    Equipment(
//                        rs.getBytes("id").guidFromBytes().toString(),
//                        rs.getInt("type_id"),
//                        rs.getInt("person_id"),
//                        rs.getInt("state_id"),
//                        rs.getString("comment"),
//                        rs.getString("inv_number"),
//                        (rs.getDate("purchase_date") as Date?).toLocalDate(),
//                        rs.getString("serial")
//                    )
//                )
//            }
//            res
//        }, *bGuids.toTypedArray())
//        return result
//    }
//
//    @Throws(SQLException::class)
//    open fun createEquipmentType(type: String): Int {
//        return createReference("equipment_type", type)
//    }
//
//    @Throws(SQLException::class)
//    open fun createEquipmentState(state: String): Int {
//        return createReference("equipment_state", state)
//    }
//
//    @Throws(SQLException::class)
//    open fun createPerson(personName: String): Int {
//        return createReference("responsible_person", personName)
//    }
//
//    @Throws(SQLException::class)
//    private fun createReference(tableName: String, value: String): Int {
//        val run = QueryRunner(ds)
//        val id = run.insert(
//            String.format(qCreateReference, tableName),
//            { rs -> if (rs.next()) rs.getInt(1) else 0 },
//            value
//        )
//        return id!!
//    }
//
//    open fun getUserInfoByLogin(userLogin: String, certLogin: Boolean): UserInfo {
//        val query = """SELECT
//            u.id,
//            u.name,
//            u.login,
//            u.login_cert,
//            u.valid,
//            r.name role_name
//        FROM user u
//            LEFT JOIN user_role ur ON ur.user_id = u.id
//            LEFT JOIN role r ON ur.role_id = r.id
//        WHERE u.${if (certLogin) "login_cert" else "login"} = ?"""
//        val r = UserInfo()
//        val run = QueryRunner(ds)
//
//        run.query(
//            query,
//            { rs ->
//                while (rs.next()) {
//                    r.id = rs.getInt("id")
//                    r.name = rs.getString("name")
//                    r.login = rs.getString("login")
//                    r.loginCert = rs.getString("login_cert")
//                    r.isValid = rs.getBoolean("valid")
//                    r.roles.add(rs.getString("role_name"))
//                }
//                r
//            }, userLogin
//        )
//        if (r.id == 0) {
//            throw RecordNotFoundException("Не найден пользователь $userLogin")
//        }
//        return r
//    }

//    /**
//     * Создание записи в таблице логов
//     * @param guid к какому оборудованию относится запись
//     * @param action действие
//     * @param userId совершившего пользователя
//     * @param comment комментарий
//     * @param date дата и время совершения действия. Если null, то берется текущее время сервера
//     *
//     * @return количество вставленных записей
//     */
//    open fun logEquipmentChange(
//        guid: String,
//        action: Action,
//        userId: Int,
//        comment: String?,
//        date: LocalDateTime? = null
//    ): Int {
//        val d = if (date == null) Date() else date.toDate()
//        return QueryRunner(ds)
//            .update(
//                "INSERT INTO equipment.log (equipment_id, action_id, user_id, comment, `time`) VALUES (?, ?, ?, ?, ?)",
//                guid.toGuidBytes(), action.id, userId, comment, d
//            )
//    }

//    open fun getLog(guid: String): List<LogEntryPresentation> {
//        val query = """
//SELECT l.equipment_id eqipment_id, a.`name` action_name, u.`name` user_name, l.`time`, l.`comment`
//  FROM log l
//  left join action a ON (l.action_id = a.id)
//  LEFT JOIN user u ON (l.user_id = u.id)
//  WHERE l.equipment_id = ?
//  order by l.`time` desc"""
//        return QueryRunner(ds).query(query, { rs ->
//            val r = ArrayList<LogEntryPresentation>()
//            while (rs.next()) {
//                r.add(
//                    LogEntryPresentation(
//                        rs.getBytes("eqipment_id").guidFromBytes().toString(),
//                        rs.getString("action_name") ?: "",
//                        rs.getString("user_name") ?: "",
//                        rs.getDate("time").toLocalDateTime() ?: LocalDateTime.of(1, 1, 1, 0, 0),
//                        rs.getString("comment")
//                    )
//                )
//            }
//            r
//        }, guid.toGuidBytes())
//    }


//    @Throws(SQLException::class)
//    open fun testTransactions() {
//        val run = QueryRunner(ds)
//
//        val d = Date()
//        val sql = "INSERT INTO testtable(value, t) VALUES(?, ?)"
//        val update1 = run.update(sql, Math.random().toString(), d)
//        val update2 = run.update(sql, Math.random().toString(), d)
//        throw SQLException("KKK")
//    }

//    companion object {
//        private const val qGetIdOfName = "SELECT id FROM %s et WHERE LOWER(et.name) = LOWER(?) LIMIT 1"
//        private const val qCreateReference = "INSERT INTO %s (`name`) VALUES (?)"
//    }
}
