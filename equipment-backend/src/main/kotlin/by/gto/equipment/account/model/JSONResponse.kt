package by.gto.equipment.account.model

open class JSONResponse<T> constructor(ec: JSONErrorCodesEnum, var message: String? = null, var content: T? = null) {
    var errCode = ec.errorCode
}
