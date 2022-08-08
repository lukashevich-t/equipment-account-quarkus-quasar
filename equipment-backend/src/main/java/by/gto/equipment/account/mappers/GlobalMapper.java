package by.gto.equipment.account.mappers;

import by.gto.equipment.account.model.BaseReference;
import by.gto.equipment.account.model.Equipment;
import by.gto.equipment.account.model.EquipmentDescr;
import by.gto.equipment.account.model.EquipmentSearchTemplate;
import by.gto.equipment.account.model.LogEntryPresentation;
import by.gto.equipment.account.model.UserInfo;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mapper
public interface GlobalMapper {
    @Select("SELECT es.id, es.name FROM equipment_state es order by es.name")
    List<BaseReference> loadEquipmentStates();

    @Select("SELECT et.id, et.name FROM equipment_type et order by et.name")
    List<BaseReference> loadEquipmentTypes();

    @Select("SELECT rp.id, rp.name FROM responsible_person rp order by rp.name")
    List<BaseReference> loadResponsiblePersons();

    List<Equipment> loadEquipmentsByGuidList(@Param("guids") List<byte[]> guids);

    @Nullable
    EquipmentDescr loadEquipmentDescr(@Param("guid") @NotNull byte[] guid);

    /**
     * Записать лог
     *
     * @param guid     id оборудования
     * @param actionId id действия
     * @param userId   id пользователя
     * @param comment  комментарий об изменениях
     * @return количество вставленных записей
     */
    int logEquipmentChange(
            @NotNull @Param("guid") byte[] guid,
            @Param("actionId") int actionId,
            @Param("userId") int userId,
            @Param("comment") String comment,
            @Param("date") LocalDateTime date);

    /**
     * получить id записи стандартного справочника по её имени
     *
     * @param refName    имя таблицы справочника
     * @param entityName искомое имя записи
     * @return id записи или null, если такая запись не найдена
     */
    @Nullable
    Integer getRefIdByName(@NotNull @Param("refName") String refName, @Param("entityName") String entityName);

    /**
     * Создать запись простого справочника. После создания в поле id записи справочника будет её id.
     *
     * @param refName имя таблицы справочника
     * @param ref     запись справочника
     * @return количество созданных записей
     */
    int createReference(@Param("refName") String refName, @Param("ref") BaseReference ref);

    /**
     * Сохранить в БД новую запись об оборудованиии. Если запись с таким id уже есть, она будет обновлена.
     *
     * @param eq описание оборудования
     * @return количество созданных записей
     */
    int saveEquipment(@NotNull @Param("eq") Equipment eq);

    /**
     * Поиск оборудования по шаблону.
     */
    @SelectProvider(type = SQLProviders.class, method = "searchEquipment")
    List<EquipmentDescr> searchEquipment(@NotNull @Param("eqTemplate") EquipmentSearchTemplate template);

    /**
     * Загрузить информацию о пользователе по его логину
     *
     * @param login логин пользователя ("Vasya").
     * @return информация о пользователе или null.
     */
    @Nullable
    UserInfo loadUserInfoByLogin(@NotNull @Param("login") String login);

    /**
     * Загрузить информацию о пользователе по его Distinguished Name (из сертификата X500).
     *
     * @param dn Distinguished Name (из сертификата X500) (cn=client, ou=iao, o=bto, l=minsk, c=by)
     * @return информация о пользователе или null.
     */
    @Nullable
    UserInfo loadUserInfoByDn(@NotNull @Param("dn") String dn);

    List<LogEntryPresentation> getLog(@NotNull @Param("guid") byte[] guid);
}
