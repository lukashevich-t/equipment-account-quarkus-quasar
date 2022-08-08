package by.gto.equipment.account.model

class JSONResponseExt<T>(ec: JSONErrorCodesEnum, message: String? = null, content: T? = null, refChanged: Boolean = false) : JSONResponse<T>(ec, message, content) {
    val referencesChanged = refChanged
}
