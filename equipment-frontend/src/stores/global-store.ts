import { defineStore } from 'pinia';

interface StoreState {
  selectedGuids: string[];
}

export const useGlobalStore = defineStore('globalStore', {
  state: () =>
    ({
      selectedGuids: [],
    } as StoreState),
  getters: {
    selectedGuidsCount: (state) => state.selectedGuids.length,
  },
  actions: {
    selectGuids(guids: string[]) {
      const m = this.selectedGuids;
      for (const guid of guids) {
        if (m.indexOf(guid) === -1) m.push(guid);
      }
    },
    unSelectGuids(guids: string[]) {
      const m = this.selectedGuids;
      for (const guid of guids) {
        const i = m.indexOf(guid);
        if (i !== -1) m.splice(i, 1);
      }
    },
    clearSelectedGuids() {
      this.selectedGuids = [];
    },
  },
});
