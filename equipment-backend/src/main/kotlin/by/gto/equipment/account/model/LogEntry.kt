package by.gto.equipment.account.model

import by.gto.equipment.account.helpers.JsonGuidDeserializer
import by.gto.equipment.account.helpers.JsonGuidSerializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.time.LocalDateTime

/**
 * Класс для хранения описания изменении состояния оборудования
 */
class LogEntryPresentation {
    @JsonSerialize(using = JsonGuidSerializer::class)
    @JsonDeserialize(using = JsonGuidDeserializer::class)
    val guid: ByteArray = DEFAULT_GUID
    val actionName: String? = null
    val userName: String = ""
    val time: LocalDateTime = LocalDateTime.MIN
    val comment: String? = null

}

data class LogEntries(var entries: List<LogEntryPresentation> = emptyList())

/**
 * Класс для записи информации об изменении состояния оборудования в БД
 */
data class LogEntry(
    @JsonSerialize(using = JsonGuidSerializer::class)
    @JsonDeserialize(using = JsonGuidDeserializer::class)
    val guid: ByteArray,
    val actionId: Int, var userId: Int, var time: LocalDateTime, val comment: String?
) {
    constructor() : this(DEFAULT_GUID, 0, 0, LocalDateTime.MIN, null)
}