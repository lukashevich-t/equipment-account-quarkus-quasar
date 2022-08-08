<template>
  <div>
    <q-card>
      <q-card-section class="row items-center">
        <div class="text-h6">Критерии поиска</div>
        <q-space />
        <q-btn icon="close" flat round dense v-close-popup />
      </q-card-section>

      <q-card-section class="items-center">
        <q-input
          outlined
          v-model="crit.invNumber"
          label="Инвентарник"
          dense
          @keyup.enter="search"
          ref="invNumberInput"
        >
          <template v-slot:append>
            <q-icon
              name="close"
              @click="crit.invNumber = ''"
              class="cursor-pointer"
              v-if="criteria.inv_number"
            />
          </template>
        </q-input>

        <q-input
          outlined
          v-model="crit.serial"
          label="Серийник"
          dense
          @keyup.enter="search"
        >
          <template v-slot:append>
            <q-icon
              name="close"
              @click="crit.serial = ''"
              class="cursor-pointer"
              v-if="criteria.serial"
            />
          </template>
        </q-input>

        <q-input
          outlined
          v-model="crit.comment"
          label="Комментарий"
          dense
          @keyup.enter="search"
        >
          <template v-slot:append>
            <q-icon
              name="close"
              @click="crit.comment = ''"
              class="cursor-pointer"
              v-if="criteria.comment"
            />
          </template>
        </q-input>

        <q-input
          outlined
          v-model="crit.displayDate"
          label="Дата покупки"
          dense
          @keyup.enter="search"
        >
          <template v-slot:append>
            <q-icon
              name="close"
              @click="crit.displayDate = ''"
              class="cursor-pointer"
              v-if="criteria.displayDate"
            />
            <q-icon name="event" class="cursor-pointer">
              <q-popup-proxy
                ref="qDateProxy"
                transition-show="scale"
                transition-hide="scale"
              >
                <q-date
                  v-model="crit.displayDate"
                  @input="() => $refs.qDateProxy.hide()"
                  mask="DD.MM.YYYY"
                  :locale="locale"
                />
              </q-popup-proxy>
            </q-icon>
          </template>
        </q-input>

        <q-select
          outlined
          dense
          v-model="crit.typeId"
          hide-selected
          fill-input
          :options="typesFiltered"
          emit-value
          map-options
          use-input
          @filter="filterTypes"
          label="Тип"
        >
          <template v-slot:append>
            <q-icon
              name="close"
              @click="crit.typeId = 0"
              class="cursor-pointer"
              v-if="criteria.type_id"
            />
          </template>
        </q-select>

        <q-select
          outlined
          dense
          v-model="crit.stateId"
          hide-selected
          fill-input
          :options="statesFiltered"
          emit-value
          map-options
          use-input
          @filter="filterStates"
          label="Состояние"
        >
          <template v-slot:append>
            <q-icon
              name="close"
              @click="crit.stateId = 0"
              class="cursor-pointer"
              v-if="criteria.state_id"
            />
          </template>
        </q-select>

        <q-select
          outlined
          dense
          v-model="crit.personId"
          hide-selected
          fill-input
          :options="personsFiltered"
          emit-value
          map-options
          use-input
          @filter="filterPersons"
          label="Ответственный"
        >
          <template v-slot:append>
            <q-icon
              name="close"
              @click="crit.personId = 0"
              class="cursor-pointer"
              v-if="criteria.person_id"
            />
          </template>
        </q-select>
      </q-card-section>

      <q-card-actions align="right">
        <!-- <q-btn flat label="test" color="primary" @click="$log.debug(criteria)"/> -->
        <q-btn flat label="Отмена" color="primary" v-close-popup />
        <q-btn flat label="Очистить" @click="clear" />
        <q-btn
          icon="search"
          color="primary"
          @click="search"
          :disable="!containsCriteria || $v.criteria.$invalid"
        />
      </q-card-actions>
    </q-card>
  </div>
</template>

<script lang="ts">
import {
  defineComponent,
  PropType,
  // computed,
  // ref,
  toRef,
  // Ref,
} from 'vue';
import { Criteria } from 'components/models';

export default defineComponent({
  name: 'SearchDlg',
  props: {
    criteria: {
      type: Object as PropType<Criteria>,
      required: true
    },
  },
  setup (props) {
    console.log(props);
    // return { ...useClickCount(), ...useDisplayTodo(toRef(props, 'todos')) };
    return { crit: toRef(props, 'criteria') };
    return {};
  },
});
</script>
