package by.gto.equipment.account.mappers

import by.gto.equipment.account.model.Equipment
import by.gto.equipment.account.model.EquipmentDescr
import by.gto.equipment.account.model.EquipmentSearchTemplate
import java.lang.StringBuilder
import java.util.UUID

class SQLProviders {
    fun searchEquipment(template: EquipmentSearchTemplate): String {
        val header = "SELECT e.id, e.type_id, e.person_id, e.state_id, e.comment, e.inv_number, e.purchase_date, " +
                "e.serial, es.name AS state, et.name AS eq_type, rp.name AS person FROM equipment e\n" +
                "  LEFT JOIN responsible_person rp ON e.person_id = rp.id\n" +
                "  LEFT JOIN equipment_state es ON e.state_id = es.id\n" +
                "  LEFT JOIN equipment_type et ON e.type_id = et.id \n" +
                "where "
        val cond = StringBuilder()
        if (template.personId > 0) {
            cond.append(" (e.person_id = #{arg0.personId}) and")
        }

        if (template.typeId > 0) {
            cond.append(" (e.type_id = #{arg0.typeId}) and")
        }

        if (template.stateId > 0) {
            cond.append(" (e.state_id = #{arg0.stateId}) and")
        }

        if (!template.comment.isNullOrEmpty()) {
            cond.append(" (e.comment like #{arg0.comment}) and")
            template.comment = "%${template.comment}%"
        }

        val purchaseDate = template.purchaseDate
        if (purchaseDate != null) {
            cond.append(" (e.purchase_date = #{arg0.purchaseDate}) and")
        }

        if (!template.serial.isNullOrEmpty()) {
            cond.append(" (e.serial like #{arg0.serial}) and")
            template.serial = "%${template.serial}%"
        }

        if (template.invNumber.isNotEmpty()) {
            cond.append(" (e.inv_number like #{arg0.invNumber}) and")
            template.invNumber = "%${template.invNumber}%"
        }
        if (cond.isEmpty()) {
            return header + "(1 = 0)"
        }
        // отрезать последний оператор AND:
        cond.setLength(cond.length - 4)
        return header + cond.toString()
    }
}