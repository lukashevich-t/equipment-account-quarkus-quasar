package by.gto.equipment.account.model


class BaseReference @JvmOverloads constructor(var id: Int = 0, var name: String = "") {
    override fun toString() = name
}
